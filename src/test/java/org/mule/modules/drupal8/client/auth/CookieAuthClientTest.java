package org.mule.modules.drupal8.client.auth;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.mule.modules.drupal8.client.DrupalClient;
import org.mule.modules.drupal8.client.DrupalClientFactory;
import org.mule.modules.drupal8.client.auth.impl.CookieAuthenticationStrategy;
import org.mule.modules.drupal8.model.Node;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.util.IOUtils;

import com.github.tomakehurst.wiremock.common.Log4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class CookieAuthClientTest
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
    public void setup()
    {
        stubFor(post(urlEqualTo("/user")).willReturn(
                aResponse().withStatus(302).withHeader(HttpHeaders.SET_COOKIE, COOKIE_VALUE)));

        client = DrupalClientFactory.getClient(endpoint, new CookieAuthenticationStrategy(USERNAME,
                PASSWORD, endpoint + "/user"));
    }

    @Test
    public void testCookieAuthIsApplied() throws Exception
    {
        stubFor(get(urlMatching("/node/[0-9]+")).willReturn(
                aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withStatus(200)
                        .withBody(
                                IOUtils.getResourceAsString("json/get-node-response.json",
                                        this.getClass()))));

        Node node = client.getNode("1");

        verify(postRequestedFor(urlMatching("/user")).withRequestBody(
                equalTo("name=" + USERNAME + "&form_id=user_login_form&pass=" + PASSWORD)));

        verify(getRequestedFor(urlMatching("/node/[0-9]+")).withHeader(HttpHeaders.ACCEPT,
                equalTo(MediaType.APPLICATION_JSON)).withHeader(HttpHeaders.COOKIE,
                equalTo(COOKIE_VALUE)));

        assertThat(node, is(not(nullValue())));
    }
}
