/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mule.modules.drupal8;

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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mule.api.MuleEvent;
import org.mule.construct.Flow;
import org.mule.modules.drupal8.model.DrupalEntity;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.User;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.util.IOUtils;

import com.github.tomakehurst.wiremock.common.Log4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class Drupal8ConnectorTest extends FunctionalTestCase
{
    @Rule
    public DynamicPort drupalPort = new DynamicPort("drupal.port");

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(new WireMockConfiguration().port(
            drupalPort.getNumber()).notifier(new Log4jNotifier()));

    @Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }

    @Before
    public void setupStubs()
    {
        stubFor(post(urlEqualTo("/user")).willReturn(
                aResponse().withStatus(302).withHeader("Set-Cookie", "SESSION:XXX;Path=/")));

    }

    @Test
    public void testGetNode() throws Exception
    {

        stubFor(get(urlMatching("/node/[0-9]+")).willReturn(
                aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/hal+json")
                        .withStatus(200)
                        .withBody(
                                IOUtils.getResourceAsString("json/get-node-response.json",
                                        this.getClass()))));

        Flow flow = lookupFlowConstruct("getNode");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        MuleEvent responseEvent = flow.process(event);

        verify(getRequestedFor(urlMatching("/node/[0-9]+")).withHeader(HttpHeaders.ACCEPT,
                equalTo("application/hal+json")).withHeader(HttpHeaders.COOKIE,
                equalTo("SESSION:XXX=;Version=1;Path=/")));

        assertThat(responseEvent.getMessage().getPayload(), is(instanceOf(Node.class)));
    }

    @Test
    public void testCreateNode() throws Exception
    {
        stubFor(post(urlEqualTo("/entity/node")).willReturn(
                aResponse().withStatus(201).withHeader("Location", "http://localhost:8888/node/1")));

        Flow flow = lookupFlowConstruct("createNode");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        flow.process(event);

        verify(postRequestedFor(urlEqualTo("/entity/node"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/hal+json"))
                .withHeader(HttpHeaders.COOKIE, equalTo("SESSION:XXX=;Version=1;Path=/"))
                .withRequestBody(
                        equalTo(IOUtils.getResourceAsString("json/create-node-request.json",
                                this.getClass()).replace("{drupal.port}",
                                String.valueOf(drupalPort.getNumber())))));
    }

    @Test
    public void testUpdateNode() throws Exception
    {
        stubFor(any(urlMatching("/node/[0-9]+"))
                .withHeader("X-HTTP-Method-Override", equalTo("PATCH")).atPriority(1)
                .willReturn(aResponse().withStatus(204)));

        Flow flow = lookupFlowConstruct("updateNode");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        flow.process(event);

        verify(postRequestedFor(urlMatching("/node/[0-9]+"))
                .withHeader(HttpHeaders.COOKIE, equalTo("SESSION:XXX=;Version=1;Path=/"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/hal+json"))
                .withHeader("X-HTTP-Method-Override", equalTo("PATCH")));
    }

    @Test
    public void testDeleteNode() throws Exception
    {
        stubFor(delete(urlMatching("/node/[0-9]+")).willReturn(aResponse().withStatus(204)));

        Flow flow = lookupFlowConstruct("deleteNode");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        MuleEvent responseEvent = flow.process(event);

        verify(deleteRequestedFor(urlMatching("/node/[0-9]+")).withHeader(HttpHeaders.COOKIE,
                equalTo("SESSION:XXX=;Version=1;Path=/")));

        assertThat(responseEvent.getMessage().getPayload(), is(not(nullValue())));
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

        Flow flow = lookupFlowConstruct("getView");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        MuleEvent responseEvent = flow.process(event);

        verify(getRequestedFor(urlMatching("/rest/views/articles")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaType.APPLICATION_JSON)).withHeader(HttpHeaders.COOKIE,
                equalTo("SESSION:XXX=;Version=1;Path=/")));

        @SuppressWarnings("unchecked")
        List<DrupalEntity> entities = (List<DrupalEntity>) responseEvent.getMessage().getPayload();

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

        Flow flow = lookupFlowConstruct("getUser");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        MuleEvent responseEvent = flow.process(event);

        assertThat(responseEvent.getMessage().getPayload(), is(instanceOf(User.class)));
    }

    @Test
    public void testCreateUser() throws Exception
    {
        stubFor(post(urlEqualTo("/entity/user")).willReturn(
                aResponse().withStatus(201).withHeader("Location", "http://localhost:8888/user/1")));

        Flow flow = lookupFlowConstruct("createUser");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        flow.process(event);

        verify(postRequestedFor(urlEqualTo("/entity/user"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/hal+json"))
                .withHeader(HttpHeaders.COOKIE, equalTo("SESSION:XXX=;Version=1;Path=/"))
                .withRequestBody(
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

        Flow flow = lookupFlowConstruct("updateUser");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        flow.process(event);

        verify(postRequestedFor(urlMatching("/user/[0-9]+"))
                .withHeader(HttpHeaders.COOKIE, equalTo("SESSION:XXX=;Version=1;Path=/"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/hal+json"))
                .withHeader("X-HTTP-Method-Override", equalTo("PATCH")));
    }

    @Test
    public void testDeleteUser() throws Exception
    {
        stubFor(delete(urlMatching("/user/[0-9]+")).willReturn(aResponse().withStatus(204)));

        Flow flow = lookupFlowConstruct("deleteUser");
        MuleEvent event = FunctionalTestCase.getTestEvent(null);
        flow.process(event);

        verify(deleteRequestedFor(urlMatching("/user/[0-9]+")).withHeader(HttpHeaders.COOKIE,
                equalTo("SESSION:XXX=;Version=1;Path=/")));
    }

    /**
     * Retrieve a flow by name from the registry
     * 
     * @param name
     *            Name of the flow to retrieve
     */
    protected Flow lookupFlowConstruct(String name)
    {
        return (Flow) FunctionalTestCase.muleContext.getRegistry().lookupFlowConstruct(name);
    }
}
