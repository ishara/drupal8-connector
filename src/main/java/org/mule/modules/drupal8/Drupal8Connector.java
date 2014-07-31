/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mule.modules.drupal8;

import java.io.IOException;
import java.util.List;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.modules.drupal8.client.DrupalClient;
import org.mule.modules.drupal8.client.DrupalClientFactory;
import org.mule.modules.drupal8.client.impl.auth.CookieAuthenticationStrategy;
import org.mule.modules.drupal8.model.DrupalEntity;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.User;

/**
 * Drupal Cloud Connector
 * 
 * @author MuleSoft, Inc.
 */
@Connector(name = "drupal8", friendlyName = "drupal8", schemaVersion = "1.0-SNAPSHOT")
public class Drupal8Connector
{

    /**
     * Drupal endpoint
     */
    @Configurable
    private String endpoint;

    private DrupalClient client;

    @Connect
    public void connect(@ConnectionKey String username, @Password String password)
            throws ConnectionException
    {
        this.client = DrupalClientFactory.getClient(endpoint, new CookieAuthenticationStrategy(
                username, password, endpoint + "/user"));
    }

    /**
     * Disconnect
     * 
     * @throws ConnectionException
     */
    @Disconnect
    public void disconnect() throws ConnectionException
    {

    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected()
    {
        return this.client != null;
    }

    /**
     * Are we connected
     */
    @ConnectionIdentifier
    public String connectionId()
    {
        return this.client.toString();
    }

    /**
     * Get a Node by a id
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:get-node}
     * 
     * @param nodeId
     *            Id of drupal node
     * @return Node
     * @throws IOException
     *             exception
     */
    @Processor
    public Node getNode(String nodeId) throws IOException
    {
        return this.client.getNode(nodeId);
    }

    /**
     * Create a new Node
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:create-node}
     * 
     * @param node
     *            Node to create
     * @return Node
     * @throws IOException
     *             exception
     */
    @Processor
    public void createNode(Node node) throws IOException
    {
        this.client.createNode(node);
    }

    /**
     * Update an existing Node
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:update-node}
     * 
     * @param node
     *            Node to update
     * @return Node
     * @throws IOException
     *             exception
     */
    @Processor
    public void updateNode(Node node) throws IOException
    {
        this.client.updateNode(node);
    }

    /**
     * Delete a Node by a id
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:delete-node}
     * 
     * @param nodeId
     *            Id of drupal node
     * @return Node
     * @throws IOException
     *             exception
     */
    @Processor
    public void deleteNode(String nodeId) throws IOException
    {
        this.client.deleteNode(nodeId);
    }

    /**
     * Get the result of a custom view
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:get-view}
     * 
     * @param viewPath
     *            the path of the custom view
     * @return Node
     * @throws IOException
     *             exception
     */
    @Processor
    public List<DrupalEntity> getView(String viewPath) throws IOException
    {
        return this.client.getView(viewPath);
    }

    /**
     * Get a User by a id
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:get-user}
     * 
     * @param userId
     *            Id of a drupal user
     * @return User
     * @throws IOException
     *             connection
     */
    @Processor
    public User getUser(String userId) throws IOException
    {
        return this.client.getUser(userId);
    }

    /**
     * Create a new User
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:create-user}
     * 
     * @param user
     *            The user to create
     * @return void
     * @throws IOException
     *             connection
     */
    @Processor
    public void createUser(User user) throws IOException
    {
        this.client.createUser(user);
    }

    /**
     * Update an existing User
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:update-user}
     * 
     * @param user
     *            The user to to update
     * @return void
     * @throws IOException
     *             connection
     */
    @Processor
    public void updateUser(User user) throws IOException
    {
        this.client.updateUser(user);
    }

    /**
     * Delete an existing User
     * 
     * {@sample.xml ../../../doc/drupal8-connector.xml.sample drupal8:delete-user}
     * 
     * @param uid
     *            The user id to to delete
     * @return void
     * @throws IOException
     *             connection
     */
    @Processor
    public void deleteUser(String uid) throws IOException
    {
        this.client.deleteUser(uid);
    }

    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

}
