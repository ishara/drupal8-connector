package org.mule.modules.drupal8;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mule.modules.drupal8.client.DrupalClient;
import org.mule.modules.drupal8.client.impl.DrupalRestClient;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.TaxonomyTerm;
import org.mule.modules.drupal8.model.User;

public class Drupal8ConnectorTest
{
    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNode() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        Mockito.when(client.getNode(Mockito.anyString())).thenReturn(new Node());

        Node node = connector.getNode("1");

        Mockito.verify(client).getNode("1");
        assertThat(node, not(nullValue()));
    }

    @Test
    public void testCreateNode() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        Node node = new Node();
        node.setNid("1");

        connector.createNode(node);
        Mockito.verify(client).createNode(node);
    }

    @Test
    public void testUpdateNode() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        Node node = new Node();
        node.setNid("1");

        connector.updateNode(node);
        Mockito.verify(client).updateNode(node);
    }

    @Test
    public void testDeleteNode() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        Node node = new Node();
        node.setNid("1");

        connector.deleteNode("1");
        Mockito.verify(client).deleteNode("1");
    }

    @Test
    public void testGetUser() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        Mockito.when(client.getUser(Mockito.anyString())).thenReturn(new User());

        User user = connector.getUser("1");

        Mockito.verify(client).getUser("1");
        assertThat(user, not(nullValue()));
    }

    @Test
    public void testCreateUser() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        User user = new User();
        user.setUid("1");

        connector.createUser(user);
        Mockito.verify(client).createUser(user);
    }

    @Test
    public void testUpdateUser() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        User user = new User();
        user.setUid("1");

        connector.updateUser(user);
        Mockito.verify(client).updateUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        User user = new User();
        user.setUid("1");

        connector.deleteUser("1");
        Mockito.verify(client).deleteUser("1");
    }

    @Test
    public void testGetTaxonomyTerm() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        Mockito.when(client.getTaxonomyTerm(Mockito.anyString())).thenReturn(new TaxonomyTerm());

        TaxonomyTerm taxonomyTerm = connector.getTaxonomyTerm("1");

        Mockito.verify(client).getTaxonomyTerm("1");
        assertThat(taxonomyTerm, not(nullValue()));
    }

    @Test
    public void testCreateTaxonomyTerm() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        TaxonomyTerm taxonomyTerm = new TaxonomyTerm();
        taxonomyTerm.setTid("1");

        connector.createTaxonomyTerm(taxonomyTerm);
        Mockito.verify(client).createTaxonomyTerm(taxonomyTerm);
    }

    @Test
    public void testUpdateTaxonomyTerm() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        TaxonomyTerm taxonomyTerm = new TaxonomyTerm();
        taxonomyTerm.setTid("1");

        connector.updateTaxonomyTerm(taxonomyTerm);
        Mockito.verify(client).updateTaxonomyTerm(taxonomyTerm);
    }

    @Test
    public void testDeleteTaxonomyTerm() throws Exception
    {
        Drupal8Connector connector = new Drupal8Connector();
        DrupalClient client = Mockito.mock(DrupalRestClient.class);
        connector.setClient(client);

        TaxonomyTerm taxonomyTerm = new TaxonomyTerm();
        taxonomyTerm.setTid("1");

        connector.deleteTaxonomyTerm("1");
        Mockito.verify(client).deleteTaxonomyTerm("1");
    }
}
