package pl.com.kantoch.WLP_ServiceDiscovery.rest;

import logs.LogService;
import org.springframework.web.bind.annotation.*;
import response.LogFileContentResponse;
import response.LogWrapperEntity;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/log")
@CrossOrigin("*")
public class LogsResource {

    private final LogService logService;

    public LogsResource(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/wrapper")
    public Collection<LogWrapperEntity> getWrapper(){
        return logService.getLogWrapper();
    }

    @GetMapping("/get-all")
    public Collection<String> listLogFiles(@RequestParam String moduleName)
            throws IOException, InterruptedException {
        return logService.listLogFiles(moduleName);
    }

    @GetMapping("/read-file")
    public LogFileContentResponse readLogFile(@RequestParam String moduleName,@RequestParam String fileName)
            throws IOException, InterruptedException {
        return logService.readLogFile(moduleName,fileName);
    }
}
