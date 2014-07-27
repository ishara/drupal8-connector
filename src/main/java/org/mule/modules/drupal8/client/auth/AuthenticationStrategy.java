package org.mule.modules.drupal8.client.auth;

import com.sun.jersey.api.client.Client;

public interface AuthenticationStrategy
{
    public void authenticateClient(Client client); 
}
