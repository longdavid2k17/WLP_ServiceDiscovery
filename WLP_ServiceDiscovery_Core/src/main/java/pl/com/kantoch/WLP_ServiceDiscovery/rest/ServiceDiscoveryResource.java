package pl.com.kantoch.WLP_ServiceDiscovery.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleEntity;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleRegistrationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/service-discovery")
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
}
