package pl.com.kantoch.WLP_ServiceDiscovery.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleEntity;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleRegistrationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/service-discovery")
@CrossOrigin("*")
public class ServiceDiscoveryResource {

    private final ModuleRegistrationService moduleRegistrationService;

    public ServiceDiscoveryResource(ModuleRegistrationService moduleRegistrationService) {
        this.moduleRegistrationService = moduleRegistrationService;
    }

    @GetMapping
    @ApiOperation(value = "Get all module configurations")
    public Collection<ModuleEntity> serviceDiscovery() {
        return moduleRegistrationService.getRegisteredModules();
    }

    @GetMapping("/refresh")
    @ApiOperation(value = "Refresh module service state for queried module name.")
    public ModuleEntity refreshModuleState(@RequestParam String moduleName) {
        return moduleRegistrationService.getRegisteredModule(moduleName);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Get module configurations depending on passed parameter value")
    public Collection<ModuleEntity> serviceDiscoveryFilter(@RequestParam String moduleName) {
        return moduleRegistrationService.getRegisteredModulesFiltered(moduleName);
    }
}
