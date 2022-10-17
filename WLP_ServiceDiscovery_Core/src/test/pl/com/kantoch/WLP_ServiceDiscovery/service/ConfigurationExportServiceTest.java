package pl.com.kantoch.WLP_ServiceDiscovery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.kantoch.WLP_ServiceDiscovery.exceptions.ModuleDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigurationExportServiceTest {

    @Autowired
    ConfigurationExportService configurationExportService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private static final String TEST_MODULE_NAME = "TEST_MODULE_NAME";

    @Test
    public void shouldReturnModuleNotFoundException() throws ModuleDoesNotExistException, JsonProcessingException {
        exception.expect(ModuleDoesNotExistException.class);
        configurationExportService.exportModuleConfiguration(TEST_MODULE_NAME);
    }

    @Test
    public void shouldReturnModuleConfigurationJson() throws ModuleDoesNotExistException, JsonProcessingException {
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
        ConfigurationExportService configurationExportService = Mockito.mock(ConfigurationExportService.class);
        Mockito.when(configurationExportService.exportModuleConfiguration(TEST_MODULE_NAME)).thenReturn(expectedValue);
        String returnedConfiguration = configurationExportService.exportModuleConfiguration(TEST_MODULE_NAME);
        assertTrue(returnedConfiguration.contains(TEST_MODULE_NAME));
        assertTrue(returnedConfiguration.contains("hostAddress"));
        assertTrue(returnedConfiguration.contains("servicePort"));
        assertTrue(returnedConfiguration.contains("id"));
    }
}
