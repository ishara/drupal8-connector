package org.mule.modules.drupal8.client;

import org.mule.modules.drupal8.client.impl.DrupalRestClient;

public class DrupalClientFactory {

	/**
	 * Returns an instance of {@link DrupalRestClient}
	 * @param server Endpoint to connect
	 * @param port	Port number
	 * @return Instance configured with the values. 
	 */
	public static DrupalClient getClient(String server, int port) {
		return new DrupalRestClient(server, port);
	}
}
