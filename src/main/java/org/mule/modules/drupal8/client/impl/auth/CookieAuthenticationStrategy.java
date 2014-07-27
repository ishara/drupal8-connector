package org.mule.modules.drupal8.client.impl.auth;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.mule.modules.drupal8.client.auth.AuthenticationStrategy;
import org.mule.modules.drupal8.client.impl.DrupalException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class CookieAuthenticationStrategy implements AuthenticationStrategy
{
    private String user;
    private String password;
    private String endpoint;

    public CookieAuthenticationStrategy(String user, String password, String endpoint)
    {
        this.user = user;
        this.password = password;
        this.endpoint = endpoint;
    }

    @Override
    public void authenticateClient(Client client)
    {
        client.setFollowRedirects(false);
        WebResource webResource;
        try
        {
            webResource = client.resource(new URI(endpoint));
        } catch (URISyntaxException e)
        {
            throw new DrupalException("Drupal URI Invalid", e);
        }

        client.addFilter(new ClientFilter() {
            private ArrayList<Object> cookies;

            @Override
            public ClientResponse handle(ClientRequest request) throws ClientHandlerException
            {
                if (cookies != null)
                {
                    request.getHeaders().put(HttpHeaders.COOKIE, cookies);
                }
                ClientResponse response = getNext().handle(request);
                if (response.getCookies() != null)
                {
                    if (cookies == null)
                    {
                        cookies = new ArrayList<Object>();
                    }
                    cookies.addAll(response.getCookies());
                }
                return response;
            }
        });

        MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
        formData.add("name", user);
        formData.add("pass", password);
        formData.add("form_id", "user_login_form");

        webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class, formData);

    }
}
