package org.mule.modules.drupal8.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mule.modules.drupal8.client.auth.impl.CookieAuthenticationStrategy;
import org.mule.modules.drupal8.model.DrupalEntity;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.TaxonomyTerm;
import org.mule.modules.drupal8.model.User;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.util.IOUtils;

import com.github.tomakehurst.wiremock.common.Log4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class DrupalRestClientTest
{
    private static final String COOKIE_VALUE = "SESSION:XXX=;Version=1;Path=/";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Rule
    public DynamicPort drupalPort = new DynamicPort("drupal.port");

    private String endpoint = "http://localhost:" + drupalPort.getNumber();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(new WireMockConfiguration().port(
            drupalPort.getNumber()).notifier(new Log4jNotifier()));

    private DrupalClient client;

    @Before
    public void setupStubs()
    {
        stubFor(post(urlEqualTo("/user")).willReturn(
                aResponse().withStatus(302).withHeader(HttpHeaders.SET_COOKIE, COOKIE_VALUE)));

        client = DrupalClientFactory.getClient(endpoint, new CookieAuthenticationStrategy(USERNAME,
                PASSWORD, endpoint + "/user"));
    }

    @Test
    public void testGetNode() throws Exception
    {
        stubFor(get(urlMatching("/node/[0-9]+")).willReturn(
                aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withStatus(200)
                        .withBody(
                                IOUtils.getResourceAsString("json/get-node-response.json",
                                        this.getClass()))));

        Node node = client.getNode("1");

        verify(getRequestedFor(urlMatching("/node/[0-9]+")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaType.APPLICATION_JSON)));

        assertThat(node, is(instanceOf(Node.class)));
    }

    @Test
    public void testCreateNode() throws Exception
    {
        stubFor(post(urlEqualTo("/entity/node")).willReturn(
                aResponse().withStatus(201).withHeader("Location", "http://localhost:8888/node/1")));

        Node node = new Node();
        node.setTitle("New Article");
        node.setType("article");

        client.createNode(node);

        verify(postRequestedFor(urlEqualTo("/entity/node")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaTypes.HAL_JSON.toString())).withRequestBody(
                equalTo(IOUtils.getResourceAsString("json/create-node-request.json",
                        this.getClass()).replace("{drupal.port}",
                        String.valueOf(drupalPort.getNumber())))));
    }

    @Test
    public void testUpdateNode() throws Exception
    {
        stubFor(post(urlMatching("/node/[0-9]+"))
                .withHeader("X-HTTP-Method-Override", equalTo("PATCH")).atPriority(1)
                .willReturn(aResponse().withStatus(204)));

        Node node = new Node();
        node.setNid("1");
        node.setTitle("New Article");
        node.setType("article");

        client.updateNode(node);

        verify(postRequestedFor(urlMatching("/node/[0-9]+")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaType.APPLICATION_JSON)).withHeader("X-HTTP-Method-Override",
                equalTo("PATCH")));
    }

    @Test
    public void testDeleteNode() throws Exception
    {
        stubFor(delete(urlMatching("/node/[0-9]+")).willReturn(aResponse().withStatus(204)));

        client.deleteNode("1");

        verify(deleteRequestedFor(urlMatching("/node/[0-9]+")));
    }

    @Test
    public void testGetView() throws Exception
    {
        stubFor(get(urlMatching("/rest/views/articles")).willReturn(
                aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withStatus(200)
                        .withBody(
                                IOUtils.getResourceAsString("json/node-view-response.json",
                                        this.getClass()))));

        List<DrupalEntity> entities = client.getView("rest/views/articles");

        verify(getRequestedFor(urlMatching("/rest/views/articles")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaType.APPLICATION_JSON)));

        assertThat(entities.size(), is(equalTo(2)));
        assertThat(entities.get(0), is(instanceOf(Node.class)));
    }

    @Test
    public void testGetUser() throws Exception
    {
        stubFor(get(urlMatching("/user/[0-9]+")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/hal+json")
                        .withBody(
                                IOUtils.getResourceAsString("json/get-user-response.json",
                                        this.getClass()))));

        User user = client.getUser("1");

        assertThat(user, is(instanceOf(User.class)));
    }

    @Test
    public void testCreateUser() throws Exception
    {
        stubFor(post(urlEqualTo("/entity/user")).willReturn(
                aResponse().withStatus(201).withHeader("Location", "http://localhost:8888/user/1")));

        User user = new User();
        user.setName("newuser");
        user.setPassword("pass");
        user.setMail("ryan@example.com");

        client.createUser(user);

        verify(postRequestedFor(urlEqualTo("/entity/user")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaTypes.HAL_JSON.toString())).withRequestBody(
                equalTo(IOUtils.getResourceAsString("json/create-user-request.json",
                        this.getClass()).replace("{drupal.port}",
                        String.valueOf(drupalPort.getNumber())))));
    }

    @Test
    public void testUpdateUser() throws Exception
    {
        stubFor(any(urlMatching("/user/[0-9]+"))
                .withHeader("X-HTTP-Method-Override", equalTo("PATCH")).atPriority(1)
                .willReturn(aResponse().withStatus(204)));

        User user = new User();
        user.setUid("1");
        user.setName("newuser");
        user.setPassword("pass");
        user.setMail("ryan@example.com");

        client.updateUser(user);

        verify(postRequestedFor(urlMatching("/user/[0-9]+")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaType.APPLICATION_JSON)).withHeader("X-HTTP-Method-Override",
                equalTo("PATCH")));
    }

    @Test
    public void testDeleteUser() throws Exception
    {
        stubFor(delete(urlMatching("/user/[0-9]+")).willReturn(aResponse().withStatus(204)));

        client.deleteUser("1");

        verify(deleteRequestedFor(urlMatching("/user/[0-9]+")));
    }

    @Test
    public void testGetTaxonomyTerm() throws Exception
    {
        stubFor(get(urlMatching("/taxonomy/term/[0-9]+")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(
                                IOUtils.getResourceAsString("json/taxonomy-term-resource.json",
                                        this.getClass()))));

        TaxonomyTerm term = client.getTaxonomyTerm("1");

        verify(getRequestedFor(urlMatching("/taxonomy/term/[0-9]+")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaType.APPLICATION_JSON)));

        assertThat(term, is(instanceOf(TaxonomyTerm.class)));
    }

    @Test
    public void testCreateTaxonomyTerm() throws Exception
    {
        stubFor(post(urlEqualTo("/entity/taxonomy_term")).willReturn(
                aResponse().withStatus(201).withHeader("Location", "http://localhost:8888/taxonomy/term/1")));

        TaxonomyTerm term = new TaxonomyTerm();
        term.setName("newtag");
        term.setWeight("1");
        
        client.createTaxonomyTerm(term);

        verify(postRequestedFor(urlEqualTo("/entity/taxonomy_term"))
                .withHeader(HttpHeaders.ACCEPT, equalTo(MediaTypes.HAL_JSON.toString()))
                .withRequestBody(
                        equalTo(IOUtils.getResourceAsString("json/create-taxonomy-term-request.json",
                                this.getClass()).replace("{drupal.port}",
                                String.valueOf(drupalPort.getNumber())))));
    }

    @Test
    public void testUpdateTaxonomyTerm() throws Exception
    {
        stubFor(any(urlMatching("/taxonomy/term/[0-9]+"))
                .withHeader("X-HTTP-Method-Override", equalTo("PATCH")).atPriority(1)
                .willReturn(aResponse().withStatus(204)));

        TaxonomyTerm term = new TaxonomyTerm();
        term.setTid("1");

        client.updateTaxonomyTerm(term);

        verify(postRequestedFor(urlMatching("/taxonomy/term/[0-9]+")).withHeader(
                HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON)).withHeader(
                "X-HTTP-Method-Override", equalTo("PATCH")));
    }

    @Test
    public void testDeleteTaxonomyTerm() throws Exception
    {
        stubFor(delete(urlMatching("/taxonomy/term/[0-9]+"))
                .willReturn(aResponse().withStatus(204)));

        client.deleteTaxonomyTerm("1");

        verify(deleteRequestedFor(urlMatching("/taxonomy/term/[0-9]+")));
    }

}
