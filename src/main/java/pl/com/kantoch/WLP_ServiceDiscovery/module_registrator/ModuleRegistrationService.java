package pl.com.kantoch.WLP_ServiceDiscovery.module_registrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.com.kantoch.WLP_ServiceDiscovery.exceptions.ModuleParamDoesNotExistException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;

@Service
public class ModuleRegistrationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ModuleRegistrationService.class);

    private final ModuleRepository moduleRepository;

    public ModuleRegistrationService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Value("${server.port}")
    private String SERVICE_PORT;
    public final static String SERVICE_DISCOVERY_MODULE_NAME = "SERVICE_DISCOVERY_MODULE";

    public ModuleEntity getModule(String moduleName){
        return moduleRepository.findByModuleName(moduleName);
    }

    public Collection<ModuleEntity> getRegisteredModules() {
        return moduleRepository.findAll();
    }

    @Scheduled(fixedRate = 1200000)
    public void checkServiceDiscoveryRegistrationState(){
        ModuleEntity entity = getModule(SERVICE_DISCOVERY_MODULE_NAME);
        if(entity!=null){
            entity.setLastActivityDate(new Date());
            moduleRepository.save(entity);
            LOGGER.warn("Current configuration with ID={} has been updated",entity.getId());
        } else registerServiceDiscoveryModule();
    }

    public void registerServiceDiscoveryModule(){
        try {
            if(SERVICE_PORT.isEmpty() || SERVICE_PORT.isBlank()) throw new ModuleParamDoesNotExistException(SERVICE_DISCOVERY_MODULE_NAME,SERVICE_PORT);
            //sprawdzenie istnienia modułu w bazie
            ModuleEntity enity = getModule(SERVICE_DISCOVERY_MODULE_NAME);
            if(enity!=null){
                moduleRepository.delete(enity);
                LOGGER.warn("Current configuration with ID={} has been deleted from storage",enity.getId());
            }
            //jego brak
            moduleRepository.save(buildModuleEntity(new Date()));
        }
        catch (Exception e){
            LOGGER.error("ModuleRegistrationService has occurred exception {} with message: {}",e.getClass(),e.getMessage());
        }
    }

    @Transactional
    public String registerModule(String port, String moduleName) throws ModuleParamDoesNotExistException{
        String message;
        try {
            if(port == null || port.isEmpty() || port.isBlank()) throw new ModuleParamDoesNotExistException("New module have not provided port value provided!");
            if(moduleName == null || moduleName.isEmpty() || moduleName.isBlank()) throw new ModuleParamDoesNotExistException("New module have not provided module name value provided!");
            //sprawdzenie istnienia modułu w bazie
            ModuleEntity enity = getModule(moduleName);
            if(enity!=null){
                moduleRepository.delete(enity);
                LOGGER.warn("Current configuration with ID={} has been deleted from storage",enity.getId());
            }
            //jego brak
            moduleRepository.save(buildModuleEntity(moduleName,port,new Date()));
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

    private ModuleEntity buildModuleEntity(String moduleName, String port, Date date) {
        return new ModuleEntityBuilder()
                .moduleName(moduleName)
                .servicePort(port)
                .firstRegistrationDate(date)
                .lastActivityDate(date)
                .get();
    }

    private ModuleEntity buildModuleEntity(Date date) {
        return new ModuleEntityBuilder()
                .moduleName(SERVICE_DISCOVERY_MODULE_NAME)
                .servicePort(SERVICE_PORT)
                .firstRegistrationDate(date)
                .lastActivityDate(date)
                .get();
    }
}
