package pl.com.kantoch.WLP_ServiceDiscovery.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.kantoch.WLP_ServiceDiscovery.service.ConfigurationExportService;

import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.EXPORTED_MODULE_CONFIGURATION_FILE_NAME;
import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.SERVICE_DISCOVERY_MODULE_NAME;

@RestController
@RequestMapping("/api/configuration")
@CrossOrigin("*")
public class ConfigurationExportResource {

    private final Logger LOGGER = LoggerFactory.getLogger(ConfigurationExportResource.class);

    private final ConfigurationExportService configurationExportService;

    public ConfigurationExportResource(ConfigurationExportService configurationExportService) {
        this.configurationExportService = configurationExportService;
    }

    @GetMapping("/export")
    @ApiOperation(value = "Export current module configuration")
    public ResponseEntity<byte[]> getCurrentConfiguration() {
        try {
            String jsonContent = configurationExportService.exportModuleConfiguration(SERVICE_DISCOVERY_MODULE_NAME);

            byte[] jsonBytes = jsonContent.getBytes();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+EXPORTED_MODULE_CONFIGURATION_FILE_NAME)
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(jsonBytes.length)
                    .body(jsonBytes);
        }
        catch (Exception e){
            String errorMessage = "Error occurred during exporting current ServiceDiscovery configuration. Type: "+e.getClass()+". Message: "+e.getMessage();
            LOGGER.error(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage.getBytes());
        }
    }

    @GetMapping("/export/search")
    @ApiOperation(value = "Export queried module configuration")
    public ResponseEntity<byte[]> getModuleConfiguration(@Parameter(description = "Requested configuration module name") @RequestParam String moduleName) {
        try {
            String jsonContent = configurationExportService.exportModuleConfiguration(moduleName);

            byte[] jsonBytes = jsonContent.getBytes();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+moduleName.toLowerCase()+"_configuration.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(jsonBytes.length)
                    .body(jsonBytes);
        }
        catch (Exception e){
            String errorMessage = "Error occurred during exporting current "+moduleName+" configuration. Type: "+e.getClass()+". Message: "+e.getMessage();
            LOGGER.error(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage.getBytes());
        }
    }
}
