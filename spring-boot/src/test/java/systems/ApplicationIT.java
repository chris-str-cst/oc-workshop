package systems;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import de.idealo.common.springboot.status.endpoints.AliveEndpoint;
import de.idealo.common.springboot.status.endpoints.CheckEndpoint;
/**
 * Integration test to check if the application context can be loaded and /status/alive returns OK (starter-icinga-status)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

    @Autowired(required = false)
    private CheckEndpoint checkEndpoint;

    @Autowired(required = false)
    private AliveEndpoint aliveEndpoint;

    @LocalServerPort
    private int port;

    @Test
    public void contextStartup() {
        assertThat(checkEndpoint, is(not(nullValue())));
        assertThat(aliveEndpoint, is(not(nullValue())));
    }

    /**
     * so far, this test has been running green. There is a potential racecondition with the initial update of the cached
     * health status which may let this output "UNKNOWN", if this occurs we might need to put some retry mechanism in place
     * If you created an application from the archetype, it is safe to simply delete this test
     */
    @Test
    public void alive() {
        RestOperations restOperations = new RestTemplate();
        String alive = restOperations.getForObject("http://localhost:" + port + "/status/alive", String.class);
        assertThat(alive, is("OK"));
    }
}
