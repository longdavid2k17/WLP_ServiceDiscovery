package pl.com.kantoch.WLP_ServiceDiscovery.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetworkConfigurationServiceTest {

    @Autowired
    private NetworkConfigurationService networkConfigurationService;

    @Test
    public void shouldReturnHostAddress() throws UnknownHostException {
        String hostAddress = networkConfigurationService.getHostAddress();
        assertNotNull(hostAddress);
    }

    @Test
    public void shouldReturnHostName() throws UnknownHostException {
        String hostName = networkConfigurationService.getHostName();
        assertNotNull(hostName);
    }
}
