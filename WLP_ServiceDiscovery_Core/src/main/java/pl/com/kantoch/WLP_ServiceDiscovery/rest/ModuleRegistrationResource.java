package pl.com.kantoch.WLP_ServiceDiscovery.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleEntity;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleRegistrationService;
import pl.com.kantoch.WLP_ServiceDiscovery.payloads.RegisterModuleRequest;
import pl.com.kantoch.WLP_ServiceDiscovery.service.ServiceDiscoveryModuleRegistration;

import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.SERVICE_DISCOVERY_MODULE_NAME;


@RestController
@RequestMapping("/api/module-registration")
public class ModuleRegistrationResource {

    private final ModuleRegistrationService moduleRegistrationService;
    private final ServiceDiscoveryModuleRegistration serviceDiscoveryModuleRegistration;

    public ModuleRegistrationResource(ModuleRegistrationService moduleRegistrationService, ServiceDiscoveryModuleRegistration serviceDiscoveryModuleRegistration) {
        this.moduleRegistrationService = moduleRegistrationService;
        this.serviceDiscoveryModuleRegistration = serviceDiscoveryModuleRegistration;
    }

    @GetMapping
    @ApiOperation(value = "Get current module configuration")
    public ModuleEntity getCurrentConfiguration() {
        ModuleEntity moduleEntity = moduleRegistrationService.getModule(SERVICE_DISCOVERY_MODULE_NAME);
        return moduleEntity;
    }

    @GetMapping("/search")
    @ApiOperation(value = "Get module configuration")
    public ModuleEntity getModuleConfiguration(@RequestParam String moduleName) {
        ModuleEntity moduleEntity = moduleRegistrationService.getModule(moduleName);
        return moduleEntity;
    }

    @PutMapping
    @ApiOperation(value = "Force module registration")
    public void forceRegistration() {
        serviceDiscoveryModuleRegistration.registerServiceDiscoveryModule();
    }

    @PostMapping
    @ApiOperation(value = "Register module request")
    public String registerModule(@RequestBody RegisterModuleRequest moduleRequest) {
        if(moduleRequest.getApplicationContext()==null
                || moduleRequest.getApplicationContext().isBlank()
                || moduleRequest.getApplicationContext().isEmpty()
                || !moduleRequest.getApplicationContext().startsWith("WLP_"))
            throw new IllegalStateException("Request has not provided application context value, or was called from unsafe host");
        return moduleRegistrationService.registerModule(moduleRequest.getServicePort(),moduleRequest.getModuleName(),moduleRequest.getHostAddress(),moduleRequest.getModuleVersion());
    }
}