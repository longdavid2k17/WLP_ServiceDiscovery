package pl.com.kantoch.WLP_ServiceDiscovery.module_registrator;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.com.kantoch.WLP_ServiceDiscovery.exceptions.ModuleParamDoesNotExistException;
import pl.com.kantoch.WLP_ServiceDiscovery.service.ServiceDiscoveryService;
import pl.com.kantoch.mavn_tools.VersionReceiverService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Collection;

import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.MAVEN_FILE_PATH;
import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.SERVICE_DISCOVERY_MODULE_NAME;

@Service
public class ModuleRegistrationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ModuleRegistrationService.class);

    private final ModuleRepository moduleRepository;
    private final ServiceDiscoveryService serviceDiscoveryService;
    private VersionReceiverService versionReceiverService;

    @Value("${server.port}")
    private String SERVICE_PORT;
    private String HOST_ADDRESS;

    public ModuleRegistrationService(ModuleRepository moduleRepository, ServiceDiscoveryService serviceDiscoveryService) {
        this.moduleRepository = moduleRepository;
        this.serviceDiscoveryService = serviceDiscoveryService;
        try {
            this.versionReceiverService = new VersionReceiverService(MAVEN_FILE_PATH);
            initializeHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("Error occurred during host address property initialization. Class: {}. Message: {}",e.getClass(),e.getMessage());
        } catch (XmlPullParserException | IOException e) {
            LOGGER.error("Error occurred during reading pom.xml file. Exception class: {}. Message: {}",e.getClass(),e.getMessage());
        }
    }

    private void initializeHostAddress() throws UnknownHostException {
        HOST_ADDRESS = InetAddress.getLocalHost().getHostAddress();
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

    @Scheduled(fixedRate = 1200000)
    public void checkServiceDiscoveryRegistrationState(){
        ModuleEntity entity = getModule(SERVICE_DISCOVERY_MODULE_NAME);
        if(entity!=null){
            entity.setLastActivityDate(LocalDateTime.now());
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
            initializeHostAddress();
            //jego brak
            moduleRepository.save(buildModuleEntity(LocalDateTime.now(), versionReceiverService.getVersion()));
        }
        catch (Exception e){
            LOGGER.error("ModuleRegistrationService has occurred exception {} with message: {}",e.getClass(),e.getMessage());
        }
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

    private ModuleEntity buildModuleEntity(String moduleName, String port,String hostAddress, String moduleVersion,LocalDateTime date) {
        return new ModuleEntityBuilder()
                .moduleName(moduleName)
                .servicePort(port)
                .hostAddress(hostAddress)
                .moduleVersion(moduleVersion)
                .firstRegistrationDate(date)
                .lastActivityDate(date)
                .build();
    }

    private ModuleEntity buildModuleEntity(LocalDateTime date,String moduleVersion) {
        return new ModuleEntityBuilder()
                .moduleName(SERVICE_DISCOVERY_MODULE_NAME)
                .servicePort(SERVICE_PORT)
                .hostAddress(HOST_ADDRESS)
                .moduleVersion(moduleVersion)
                .firstRegistrationDate(date)
                .lastActivityDate(date)
                .build();
    }
}
