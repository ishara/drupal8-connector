package org.mule.modules.drupal8.client;

import org.mule.modules.drupal8.client.auth.AuthenticationStrategy;
import org.mule.modules.drupal8.client.impl.DrupalRestClient;

public class DrupalClientFactory {

	/**
	 * Returns an instance of {@link DrupalRestClient}
	 * @param endpoint Endpoint to connect
	 * @return Instance configured with the values. 
	 */
	public static DrupalClient getClient(String endpoint, AuthenticationStrategy auth) {
	    return new DrupalRestClient(endpoint, auth);
	}
}
