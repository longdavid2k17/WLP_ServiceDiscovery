package pl.com.kantoch.WLP_ServiceDiscovery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import logs.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleEntity;
import pl.com.kantoch.WLP_ServiceDiscovery.module_registrator.ModuleRepository;
import pl.com.kantoch.files.FileOperationServiceImplementation;
import pl.com.kantoch.requests.HttpRequests;
import response.LogFileContentResponse;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

import static pl.com.kantoch.WLP_ServiceDiscovery.tools.NameDictionary.SERVICE_DISCOVERY_MODULE_NAME;

@Service
public class LogServiceImplementation implements LogService {

    private final Logger LOGGER = LoggerFactory.getLogger(LogServiceImplementation.class);

    private final FileOperationServiceImplementation fileOperationService;
    private final ModuleRepository moduleRepository;

    public LogServiceImplementation(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
        this.fileOperationService = new FileOperationServiceImplementation();
    }

    public Collection<String> listLogFiles(String moduleName) throws IOException, InterruptedException {
        LOGGER.warn("Requesting listing log files for {}",moduleName);
        if(moduleName.equals(SERVICE_DISCOVERY_MODULE_NAME)) {
            try {
                Collection<Path> paths = fileOperationService.loadLogFiles();
                return paths
                        .stream()
                        .map(Path::toString)
                        .collect(Collectors.toUnmodifiableList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ModuleEntity requestedModule = moduleRepository.findByModuleName(moduleName);
        final String requestUrl = "http://"+requestedModule.getHostAddress()+":"+requestedModule.getServicePort()
                +"/api/log/get-all?moduleName="+moduleName;
        HttpResponse<String> response = HttpRequests.sendGetRequest(requestUrl);
        if(response.statusCode()==200) {
            return new ObjectMapper().readValue(response.body(),Collection.class);
        }
        throw new IllegalStateException("Cannot receive proper response from "+moduleName);
    }

    public LogFileContentResponse readLogFile(String moduleName, String fileName) throws IOException, InterruptedException {
        LOGGER.warn("Requesting read log file ({}) for {}",fileName,moduleName);
        if(moduleName.equals(SERVICE_DISCOVERY_MODULE_NAME)) {
            try {
                if(fileName.contains(".gz")) return new LogFileContentResponse(fileOperationService.readArchivedFile(fileName));
                return new LogFileContentResponse(fileOperationService.readFile(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ModuleEntity requestedModule = moduleRepository.findByModuleName(moduleName);
        final String requestUrl = "http://"+requestedModule.getHostAddress()+":"+requestedModule.getServicePort()
                +"/api/log/read-file?moduleName="+moduleName+"&fileName="+fileName;
        HttpResponse<String> response = HttpRequests.sendGetRequest(requestUrl);
        if(response.statusCode()==200) {
            return new ObjectMapper().readValue(response.body(), LogFileContentResponse.class);
        }
        throw new IllegalStateException("Cannot receive proper response from "+moduleName);

    }
}
