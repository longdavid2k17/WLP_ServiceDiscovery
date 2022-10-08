package pl.com.kantoch.WLP_ServiceDiscovery.module_registrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.kantoch.WLP_ServiceDiscovery.exceptions.ModuleParamDoesNotExistException;
import pl.com.kantoch.WLP_ServiceDiscovery.service.ServiceDiscoveryService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;


@Service
public class ModuleRegistrationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ModuleRegistrationService.class);

    private final ModuleRepository moduleRepository;
    private final ServiceDiscoveryService serviceDiscoveryService;


    public ModuleRegistrationService(ModuleRepository moduleRepository, ServiceDiscoveryService serviceDiscoveryService) {
        this.moduleRepository = moduleRepository;
        this.serviceDiscoveryService = serviceDiscoveryService;
    }

    public ModuleEntity save(ModuleEntity module) {
        return moduleRepository.save(module);
    }

    public ModuleEntity getModule(String moduleName){
        return moduleRepository.findByModuleName(moduleName);
    }

    public Collection<ModuleEntity> getRegisteredModules() {
        Collection<ModuleEntity> moduleEntityCollection = moduleRepository.findAll();
        moduleEntityCollection.forEach(e->e.setSwaggerUrl(e.buildSwaggerUrl()));
        moduleEntityCollection.forEach(e->e.setStatus(serviceDiscoveryService.getServiceStatus(e.getSwaggerUrl())));
        return moduleEntityCollection;
    }

    @Transactional
    public String registerModule(String port, String moduleName,String hostAddress,String moduleVersion) throws ModuleParamDoesNotExistException{
        String message;
        try {
            if(port == null || port.isEmpty() || port.isBlank()) throw new ModuleParamDoesNotExistException("New module have not provided port value provided!");
            if(moduleName == null || moduleName.isEmpty() || moduleName.isBlank()) throw new ModuleParamDoesNotExistException("New module have not provided module name value provided!");
            if(moduleVersion == null || moduleVersion.isEmpty() || moduleVersion.isBlank()) throw new ModuleParamDoesNotExistException("New module have not provided module version value provided!");
            //sprawdzenie istnienia modułu w bazie
            ModuleEntity enity = getModule(moduleName);
            if(enity!=null){
                moduleRepository.delete(enity);
                LOGGER.warn("Current configuration with ID={} has been deleted from storage",enity.getId());
            }
            //jego brak
            moduleRepository.save(buildModuleEntity(moduleName,port, hostAddress, moduleVersion,LocalDateTime.now()));
            message = "Module "+moduleName+" has been registered on port "+port;
            LOGGER.info(message);
            return message;
        }
        catch (Exception e){
            message = "ModuleRegistrationService has occurred exception "+e.getClass()+" with message: "+e.getMessage();
            LOGGER.error(message);
            return message;
        }
    }

    private ModuleEntity buildModuleEntity(String moduleName, String port, String hostAddress, String moduleVersion,LocalDateTime date) {
        return new ModuleEntityBuilder()
                .moduleName(moduleName)
                .servicePort(port)
                .hostAddress(hostAddress)
                .moduleVersion(moduleVersion)
                .firstRegistrationDate(date)
                .lastActivityDate(date)
                .build();
    }

    @Transactional
    public void delete(ModuleEntity entity) {
        moduleRepository.delete(entity);
    }
}
