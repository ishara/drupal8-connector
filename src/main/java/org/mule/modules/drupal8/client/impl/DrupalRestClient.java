package org.mule.modules.drupal8.client.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.modules.drupal8.client.DrupalClient;
import org.mule.modules.drupal8.model.DrupalEntity;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.User;
import org.mule.modules.drupal8.model.mapper.ItemDeserializer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class DrupalRestClient implements DrupalClient {

	private NewCookie sessionId;

	private String csrfToken;

	protected Client client;
	protected ObjectMapper mapper;
	protected WebResource webResource;

	private String host;
	private int port;

	public DrupalRestClient(String host, int port) {
		this.host = host;
		this.port = port;
		this.mapper = setupObjectMapper();

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		clientConfig.getClasses().add(JacksonJsonProvider.class);

		this.client = Client.create(clientConfig);
		this.client.setFollowRedirects(false);
	}

	@Override
	public void login(String username, String password)
			throws ConnectionException {
		try {
			this.webResource = client.resource(new URI("http", null, host,
					port, null, null, null));
		} catch (URISyntaxException e) {
			throw new ConnectionException(ConnectionExceptionCode.UNKNOWN,
					null, "Drupal URI Invalid");
		}

		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("name", username);
		formData.add("pass", password);
		formData.add("form_id", "user_login_form");

		ClientResponse response = webResource.path("user")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, formData);

		Status status = response.getClientResponseStatus();
		if (status == Status.OK || status == Status.FOUND) {
			String entity = response.getEntity(String.class);
			if (!response.getCookies().isEmpty()) {
				// Set Session ID
				this.sessionId = response.getCookies().get(0);
				// Get CSRF token
				this.csrfToken = webResource.path("rest/session/token")
						.cookie(sessionId).get(String.class);
			} else {
				throw new ConnectionException(ConnectionExceptionCode.UNKNOWN,
						entity, "Drupal Rejected Login");
			}
		} else if (status == Status.UNAUTHORIZED) {
			throw new ConnectionException(
					ConnectionExceptionCode.INCORRECT_CREDENTIALS, null, null);
		} else {
			throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH,
					null, null);
		}
	}

	@Override
	public void logout() throws ConnectionException {
		webResource.path("user").path("logout")
				.type(MediaType.APPLICATION_FORM_URLENCODED).cookie(sessionId)
				.post(ClientResponse.class, null);

		this.client = null;
		this.sessionId = null;
	}

	@Override
	public boolean isConnected() {
		return client != null && sessionId != null
				&& sessionId.getValue() != null;
	}

	@Override
	public String connectionId() {
		return this.sessionId.toString();
	}

	@Override
	public Node getNode(String nodeId) throws IOException {
		String response = webResource.path("entity").path("node").path(nodeId)
				.cookie(sessionId).header("Accept", "application/hal+json")
				.get(String.class);

		return mapper.readValue(response, Node.class);
	}

	@Override
	public Node createNode(Node node) throws IOException {
		// Hack to add HAL links - until Drupal 8 allow posting with straight
		// json.
		Map<String, Object> _links = new HashMap<String, Object>();
		Map<String, Object> type = new HashMap<String, Object>();
		type.put("href", "http://" + host + "/rest/type/node/" + node.getType());
		_links.put("type", type);
		node.setAdditionalProperties("_links", _links);

		// Manually render JSON because of HAL.
		StringWriter w = new StringWriter();
		mapper.writeValue(new JsonFactory().createJsonGenerator(w), node);
		w.close();

		String s = w.toString();

		ClientResponse clientResponse = webResource.path("entity/node/")
				.header("Accept", "application/hal+json")
				.header("X-CSRF-Token", csrfToken).cookie(sessionId)
				.header("Content-type", "application/hal+json")
				.post(ClientResponse.class, s);

		Status status = clientResponse.getClientResponseStatus();
		URI location = clientResponse.getLocation();
		if (status == Status.CREATED && location != null) {
			String response = webResource.path(location.getPath())
					.cookie(sessionId).header("Accept", "application/hal+json")
					.get(String.class);
			return mapper.readValue(response, Node.class);

		} else {
			throw new IOException("Could not create Node. Got status: "
					+ clientResponse.getStatus());
		}

	}

	@Override
	public Node updateNode(Node node) throws IOException {
		// Hack to add HAL links - until Drupal 8 allow posting with straight
		// json.
		Map<String, Object> _links = new HashMap<String, Object>();
		Map<String, Object> type = new HashMap<String, Object>();
		type.put("href", "http://" + host + "/rest/type/node/" + node.getType());
		_links.put("type", type);
		node.setAdditionalProperties("_links", _links);

		StringWriter w = new StringWriter();
		mapper.writeValue(new JsonFactory().createJsonGenerator(w), node);
		w.close();

		String s = w.toString();

		System.out.println("request " + s);
		ClientResponse response = webResource.path("entity").path("node")
				.path(node.getNid()).queryParam("HttpMethod", "PATCH")
				.header("Accept", "application/hal+json")
				.header("X-CSRF-Token", csrfToken).cookie(sessionId)
				.header("Content-type", "application/hal+json")
				.method("POST", ClientResponse.class, s);
		
		System.out.println("response " + response.getStatus() + response.getEntity(String.class));

		return null;
	}

	@Override
	public void deleteNode(String nodeId) throws IOException {
		webResource.path("entity").path("node").path(nodeId).cookie(sessionId)
				.delete(ClientResponse.class);
	}

	@Override
	public User getUser(String userId) throws IOException {
		String response = webResource.path("entity").path("user").path(userId)
				.accept(MediaType.APPLICATION_JSON_TYPE).cookie(sessionId)
				.get(String.class);

		return mapper.readValue(response, User.class);
	}

	public ObjectMapper setupObjectMapper() {
		ItemDeserializer deserializer = new ItemDeserializer();
		deserializer.registerItem("nid", Node.class);
		SimpleModule module = new SimpleModule(
				"PolymorphicItemDeserializerModule", new Version(1, 0, 0, null));
		module.addDeserializer(DrupalEntity.class, deserializer);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper;
	}

}
