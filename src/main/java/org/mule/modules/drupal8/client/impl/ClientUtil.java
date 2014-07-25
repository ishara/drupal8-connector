package org.mule.modules.drupal8.client.impl;

import java.util.Arrays;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientUtil
{

    public static ClientResponse execute(String httpMethod, WebResource resource)
    {
        return execute(httpMethod, resource.getRequestBuilder());
    }

    public static ClientResponse execute(String httpMethod, WebResource.Builder resourceBuilder)
    {
        try
        {
            ClientResponse clientResponse = resourceBuilder
                    .method(httpMethod, ClientResponse.class);
            return clientResponse;
        } catch (RuntimeException e)
        {
            throw new DrupalException("Exception trying to execute Client ", e);
        }
    }

    public static <T> T validateAndParseResponse(ClientResponse clientResponse,
            Class<T> typeToReturn, Integer... expectedStatus) throws DrupalException
    {
        if (responseValid(clientResponse, expectedStatus))
        {
            return parseResponse(clientResponse, typeToReturn);
        } else
        {
            throw new DrupalException("API returned unexpected response: "
                    + clientResponse.getStatus() + System.getProperty("line.separator") + "Body: "
                    + parseResponse(clientResponse, String.class));
        }
    }

    private static boolean responseValid(ClientResponse clientResponse, Integer... expectedStatus)
    {
        return Arrays.asList(expectedStatus).contains(clientResponse.getStatus());
    }

    private static <T> T parseResponse(ClientResponse clientResponse, Class<T> typeToReturn)
            throws DrupalException
    {
        try
        {
            return clientResponse.getEntity(typeToReturn);
        } catch (Exception e)
        {
            throw new DrupalException ("Error parsing response", e);
        }
    }
}
