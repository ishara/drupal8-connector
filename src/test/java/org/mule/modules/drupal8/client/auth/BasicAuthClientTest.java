package org.mule.modules.drupal8.client.auth;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mule.modules.drupal8.client.DrupalClient;
import org.mule.modules.drupal8.client.DrupalClientFactory;
import org.mule.modules.drupal8.client.auth.impl.BasicAuthenticationStrategy;
import org.mule.modules.drupal8.model.Node;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.util.Base64;
import org.mule.util.IOUtils;

import com.github.tomakehurst.wiremock.common.Log4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class BasicAuthClientTest
{
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
    public void setup()
    {
        client = DrupalClientFactory.getClient(endpoint, new BasicAuthenticationStrategy(USERNAME,
                PASSWORD));
    }

    @Test
    public void testBasicAuthIsApplied() throws Exception
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
                equalTo(MediaType.APPLICATION_JSON)).withHeader(HttpHeaders.AUTHORIZATION,
                equalTo(createAuthHeader(USERNAME, PASSWORD))));

        assertThat(node, is(not(nullValue())));
    }

    private static String createAuthHeader(String username, String password) throws IOException
    {
        String auth = username + ":" + password;
        String encodedAuth = Base64.encodeBytes(auth.getBytes(Charset.forName("US-ASCII")));

        return ("Basic " + new String(encodedAuth));
    }
}
