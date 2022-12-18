package dfw;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dfw.endpoints.DfwEndpoint;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DfwRestTest {

    @Autowired
    private DfwEndpoint endpoint;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate rest;

    private String getTestUrl() {
        return "http://localhost:" + port + "/api";
    }

    @Test
    void contextLoads() {
        assertThat(endpoint).isNotNull();
    }

    @Test
    void testCompute() {
        String result = rest.getForObject(
                new StringBuilder()
                        .append(this.getTestUrl())
                        .append("/compute")
                        .append("?lat=33.045352")
                        .append("&lng=-96.781508")
                        .append("&rad=2000")
                        .append("&strategyId=1")
                        .toString(),
                String.class);

        assertThat(result).matches(this::isValidJson);
    }

    @Test
    void testGetShapes() {
        String result = rest.getForObject(this.getTestUrl() + "/shapes", String.class);
        assertThat(result).matches(this::isValidJson);
    }

    private boolean isValidJson(String s) {
        try {
            new ObjectMapper().readTree(s);
        } catch (JacksonException e) {
            return false;
        }
        return true;
    }

}
