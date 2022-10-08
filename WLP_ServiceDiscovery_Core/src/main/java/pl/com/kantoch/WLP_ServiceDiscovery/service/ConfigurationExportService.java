package pl.com.kantoch.WLP_ServiceDiscovery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import pl.com.kantoch.JsonLoader;
import pl.com.kantoch.WLP_ServiceDiscovery.exceptions.ModuleDoesNotExistException;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleEntity;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleRepository;

@Service
public class ConfigurationExportService {
    private final ModuleRepository moduleRepository;
    private final JsonLoader jsonLoader;

    public ConfigurationExportService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
        this.jsonLoader = new JsonLoader();
    }

    public String exportModuleConfiguration(String moduleName) throws ModuleDoesNotExistException, JsonProcessingException {
        ModuleEntity module = moduleRepository.findByModuleName(moduleName);
        if(module==null) throw new ModuleDoesNotExistException(moduleName);
        return jsonLoader.convertIntoJsonFile(module);
    }
}
