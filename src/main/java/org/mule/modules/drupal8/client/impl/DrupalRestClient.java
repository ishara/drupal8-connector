package org.mule.modules.drupal8.client.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.mule.modules.drupal8.client.DrupalClient;
import org.mule.modules.drupal8.client.MediaTypes;
import org.mule.modules.drupal8.client.auth.AuthenticationStrategy;
import org.mule.modules.drupal8.model.DrupalEntity;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.TaxonomyTerm;
import org.mule.modules.drupal8.model.User;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class DrupalRestClient implements DrupalClient
{
    private static final String HTTP_HEADER_METHOD_OVERRIDE = "X-HTTP-Method-Override";
    private static final String HTTP_METHOD_PATCH = "PATCH";

    public static final String NODE_RESOURCE = "node";
    public static final String USER_RESOURCE = "user";
    public static final String TAXONOMY_RESOURCE = "taxonomy/term";
    public static final String TAXONOMY_CREATE_RESOURCE = "taxonomy_term";
    public static final String CREATE_ROOT_PATH = "entity";

    public static final String LINK_ROOT = "/rest/type/";

    protected Client client;
    protected WebResource webResource;

    private String endpoint;

    public DrupalRestClient(String endpoint, AuthenticationStrategy auth) throws DrupalException
    {
        this.endpoint = endpoint;

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(DrupalHalProvider.class);
        client = Client.create(clientConfig);
        webResource = client.resource(endpoint);
        auth.authenticateClient(client);
    }

    @Override
    public Node getNode(String nodeId) throws IOException
    {
        return webResource.path(NODE_RESOURCE).path(nodeId)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).get(Node.class);
    }

    @Override
    public void createNode(Node node) throws IOException
    {
        node.setAdditionalProperties("_links", getHALProperties(endpoint + "/rest/type/node/"
                + node.getType()));

        webResource.path(CREATE_ROOT_PATH).path(NODE_RESOURCE)
                .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON).post(node);
    }

    @Override
    public void updateNode(Node node) throws IOException
    {
        webResource.path(NODE_RESOURCE).path(node.getNid())
                .header(HTTP_HEADER_METHOD_OVERRIDE, HTTP_METHOD_PATCH)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).post(node);
    }

    @Override
    public void deleteNode(String nodeId) throws IOException
    {
        webResource.path(NODE_RESOURCE).path(nodeId).delete();
    }

    @Override
    public List<DrupalEntity> getView(String viewPath)
    {
        return webResource.path(viewPath)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<DrupalEntity>>() {
                });
    }

    @Override
    public User getUser(String userId) throws IOException
    {
        return webResource.path(USER_RESOURCE).path(userId).accept(MediaType.APPLICATION_JSON)
                .get(User.class);
    }

    @Override
    public void createUser(User user) throws IOException
    {
        user.setAdditionalProperties("_links", getHALProperties(endpoint + "/rest/type/user/user"));

        webResource.path(CREATE_ROOT_PATH).path(USER_RESOURCE)
                .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON).post(user);
    }

    @Override
    public void updateUser(User user)
    {
        webResource.path(USER_RESOURCE).path(user.getUid())
                .header(HTTP_HEADER_METHOD_OVERRIDE, HTTP_METHOD_PATCH)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).post(user);
    }

    @Override
    public void deleteUser(String uid)
    {
        webResource.path(USER_RESOURCE).path(uid).delete();
    }

    @Override
    public TaxonomyTerm getTaxonomyTerm(String termId) throws IOException
    {
        return webResource.path(TAXONOMY_RESOURCE).path(termId).accept(MediaType.APPLICATION_JSON)
                .get(TaxonomyTerm.class);
    }

    @Override
    public void createTaxonomyTerm(TaxonomyTerm term) throws IOException
    {
        term.setAdditionalProperties("_links", getHALProperties(endpoint
                + "/rest/type/taxonomy_term/tags"));

        webResource.path(CREATE_ROOT_PATH).path(TAXONOMY_CREATE_RESOURCE)
                .header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON).post(term);

    }

    @Override
    public void updateTaxonomyTerm(TaxonomyTerm term) throws IOException
    {
        webResource.path(TAXONOMY_RESOURCE).path(term.getTid())
                .header(HTTP_HEADER_METHOD_OVERRIDE, HTTP_METHOD_PATCH)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).post(term);
    }

    @Override
    public void deleteTaxonomyTerm(String termId) throws IOException
    {
        webResource.path(TAXONOMY_RESOURCE).path(termId).delete();
    }

    private Map<String, Object> getHALProperties(String link)
    {
        Map<String, Object> _links = new HashMap<String, Object>();
        Map<String, Object> type = new HashMap<String, Object>();
        type.put("href", link);
        _links.put("type", type);
        return _links;
    }

}
