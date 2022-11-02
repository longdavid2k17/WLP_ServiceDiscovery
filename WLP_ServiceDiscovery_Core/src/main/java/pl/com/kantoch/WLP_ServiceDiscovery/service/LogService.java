package pl.com.kantoch.WLP_ServiceDiscovery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.kantoch.WLP_ServiceDiscovery.payloads.response.LogFileContentResponse;
import pl.com.kantoch.files.FileOperationServiceImplementation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LogService {

    private final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    private final FileOperationServiceImplementation fileOperationService;

    public LogService() {
        this.fileOperationService = new FileOperationServiceImplementation();
    }

    public Collection<String> listLogFiles(){
        LOGGER.warn("Requesting listing log files for ServiceDiscovery Module");
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

    public LogFileContentResponse readLogFile(String fileName){
        LOGGER.warn("Requesting read log file ({}) for ServiceDiscovery Module",fileName);
        try {
            if(fileName.contains(".gz")) return new LogFileContentResponse(fileOperationService.readArchivedFile(fileName));
            return new LogFileContentResponse(fileOperationService.readFile(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
