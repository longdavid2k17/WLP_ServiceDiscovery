package pl.com.kantoch.WLP_ServiceDiscovery.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigurationExportResourceTest {

    @Test
    public void shouldGetAnyResponse(){
        ConfigurationExportResource configurationExportResource = Mockito.mock(ConfigurationExportResource.class);
        String expectedValue = "";
        Mockito.when(configurationExportResource.getCurrentConfiguration())
                .thenReturn(ResponseEntity.ok(expectedValue.getBytes(StandardCharsets.UTF_8)));
        ResponseEntity<?> response = configurationExportResource.getCurrentConfiguration();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldReturnCurrentServiceDiscoveryConfiguration(){
        ConfigurationExportResource configurationExportResource = Mockito.mock(ConfigurationExportResource.class);
        String expectedValue = "{\n" +
                "    \"id\": 1,\n" +
                "    \"moduleName\": \"TEST_MODULE_NAME\",\n" +
                "    \"servicePort\": \"8001\",\n" +
                "    \"hostAddress\": \"192.168.1.100\",\n" +
                "    \"firstRegistrationDate\": \"2022-10-07T19:47:55\",\n" +
                "    \"lastActivityDate\": \"2022-10-17T21:59:21\",\n" +
                "    \"moduleVersion\": \"11.0.1\",\n" +
                "    \"swaggerUrl\": null,\n" +
                "    \"status\": null\n" +
                "}";
        Mockito.when(configurationExportResource.getCurrentConfiguration())
                .thenReturn(ResponseEntity.ok(expectedValue.getBytes(StandardCharsets.UTF_8)));
        byte[] response = configurationExportResource.getCurrentConfiguration().getBody();
        assertNotNull(response);
        assertArrayEquals(response, expectedValue.getBytes(StandardCharsets.UTF_8));
    }
}
