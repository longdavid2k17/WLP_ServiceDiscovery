package pl.com.kantoch.WLP_ServiceDiscovery.service;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.com.kantoch.WLP_ServiceDiscovery.exceptions.ModuleParamDoesNotExistException;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleEntity;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleEntityBuilder;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleRegistrationService;
import pl.com.kantoch.mavn_tools.VersionReceiverService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.MAVEN_FILE_PATH;
import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.SERVICE_DISCOVERY_MODULE_NAME;

@Service
public class ServiceDiscoveryModuleRegistration {

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceDiscoveryModuleRegistration.class);

    private final ModuleRegistrationService moduleRegistrationService;
    private final NetworkConfigurationService networkConfigurationService;
    private VersionReceiverService versionReceiverService;

    @Value("${server.port}")
    private String SERVICE_PORT;
    private String HOST_ADDRESS;

    public ServiceDiscoveryModuleRegistration(ModuleRegistrationService moduleRegistrationService, NetworkConfigurationService networkConfigurationService) {
        this.moduleRegistrationService = moduleRegistrationService;
        this.networkConfigurationService = networkConfigurationService;
        try {
            this.versionReceiverService = new VersionReceiverService(MAVEN_FILE_PATH);
            this.HOST_ADDRESS = this.networkConfigurationService.getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("Error occurred during host address property initialization. Class: {}. Message: {}",e.getClass(),e.getMessage());
        } catch (XmlPullParserException | IOException e) {
            LOGGER.error("Error occurred during reading pom.xml file. Exception class: {}. Message: {}",e.getClass(),e.getMessage());
        }
    }

    @Scheduled(fixedRate = 1200000)
    private void checkServiceDiscoveryRegistrationState(){
        ModuleEntity entity = moduleRegistrationService.getModule(SERVICE_DISCOVERY_MODULE_NAME);
        if(entity!=null){
            entity.setLastActivityDate(LocalDateTime.now());
            moduleRegistrationService.save(entity);
            LOGGER.warn("Current configuration with ID={} has been updated",entity.getId());
        } else registerServiceDiscoveryModule();
    }

    public void registerServiceDiscoveryModule(){
        try {
            if(SERVICE_PORT.isEmpty() || SERVICE_PORT.isBlank()) throw new ModuleParamDoesNotExistException(SERVICE_DISCOVERY_MODULE_NAME,SERVICE_PORT);
            //sprawdzenie istnienia modułu w bazie
            ModuleEntity entity = moduleRegistrationService.getModule(SERVICE_DISCOVERY_MODULE_NAME);
            if(entity!=null){
                moduleRegistrationService.delete(entity);
                LOGGER.warn("Current configuration with ID={} has been deleted from storage",entity.getId());
            }
            //jego brak
            moduleRegistrationService.save(buildModuleEntity(LocalDateTime.now(), versionReceiverService.getVersion()));
            LOGGER.info("Successfully registered service discovery with actual configuration");
        }
        catch (Exception e){
            LOGGER.error("ServiceDiscoveryModuleRegistration has occurred exception {} with message: {}",e.getClass(),e.getMessage());
        }
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
