package org.mule.modules.drupal8.client.auth.impl;

import org.mule.modules.drupal8.client.auth.AuthenticationStrategy;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class BasicAuthenticationStrategy implements AuthenticationStrategy
{
    private String username;
    private String password;
    
    public BasicAuthenticationStrategy(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
    @Override
    public void authenticateClient(Client client)
    {
        client.addFilter(new HTTPBasicAuthFilter(username, password));
    }
}
