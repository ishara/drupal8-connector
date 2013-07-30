package org.mule.modules.drupal8.client;

import java.io.IOException;

import org.mule.api.ConnectionException;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.User;

public interface DrupalClient {
	public void login(String username, String password) throws ConnectionException;
	public void logout() throws ConnectionException;
	public boolean isConnected();
	public String connectionId();
	
	public Node getNode(String nodeId) throws IOException;
	public Node createNode(Node node) throws IOException;
	public void deleteNode(String nodeId) throws IOException;
	
	public User getUser(String userId) throws IOException;
}
