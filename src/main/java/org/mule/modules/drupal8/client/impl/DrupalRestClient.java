package org.mule.modules.drupal8.client.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.mule.modules.drupal8.client.DrupalClient;
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
    private static final String MEDIA_TYPE_HAL_JSON = "application/hal+json";

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
        return webResource.path("node").path(nodeId)
                .header(HttpHeaders.ACCEPT, MEDIA_TYPE_HAL_JSON).get(Node.class);
    }

    @Override
    public void createNode(Node node) throws IOException
    {
        node.setAdditionalProperties("_links", getHALProperties(endpoint + "/rest/type/node/"
                + node.getType()));

        webResource.path("entity").path("node").header(HttpHeaders.ACCEPT, MEDIA_TYPE_HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE_HAL_JSON).post(node);
    }

    @Override
    public void updateNode(Node node) throws IOException
    {
        webResource.path("node").path(node.getNid())
                .header(HTTP_HEADER_METHOD_OVERRIDE, HTTP_METHOD_PATCH)
                .header(HttpHeaders.ACCEPT, MEDIA_TYPE_HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE_HAL_JSON).post(node);
    }

    @Override
    public void deleteNode(String nodeId) throws IOException
    {
        webResource.path("node").path(nodeId).delete();
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
        return webResource.path("user").path(userId).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(User.class);
    }

    @Override
    public void createUser(User user) throws IOException
    {
        user.setAdditionalProperties("_links", getHALProperties(endpoint + "/rest/type/user/user"));

        webResource.path("entity").path("user").header(HttpHeaders.ACCEPT, MEDIA_TYPE_HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE_HAL_JSON).post(user);
    }

    @Override
    public void updateUser(User user)
    {
        webResource.path("user").path(user.getUid())
                .header(HTTP_HEADER_METHOD_OVERRIDE, HTTP_METHOD_PATCH)
                .header(HttpHeaders.ACCEPT, MEDIA_TYPE_HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE_HAL_JSON).post(user);
    }

    @Override
    public void deleteUser(String uid)
    {
        webResource.path("user").path(uid).delete();
    }
    
    @Override
    public TaxonomyTerm getTaxonomyTerm(String termId) throws IOException
    {
        return webResource.path("taxonomy/term").path(termId).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(TaxonomyTerm.class);
    }

    @Override
    public void createTaxonomyTerm(TaxonomyTerm term) throws IOException
    {
        term.setAdditionalProperties("_links", getHALProperties(endpoint + "/rest/type/taxonomy_term/tags"));

        webResource.path("entity").path("taxonomy_term").header(HttpHeaders.ACCEPT, MEDIA_TYPE_HAL_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE_HAL_JSON).post(term);
        
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
