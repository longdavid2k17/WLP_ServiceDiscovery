package pl.com.kantoch.WLP_ServiceDiscovery.rest;

import org.springframework.web.bind.annotation.*;
import pl.com.kantoch.WLP_ServiceDiscovery.payloads.response.LogFileContentResponse;
import pl.com.kantoch.WLP_ServiceDiscovery.service.LogService;

import java.util.Collection;

@RestController
@RequestMapping("/api/log")
@CrossOrigin("*")
public class LogsResource {

    private final LogService logService;

    public LogsResource(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/get-all")
    public Collection<String> listLogFiles(){
        return logService.listLogFiles();
    }

    @GetMapping("/read-file")
    public LogFileContentResponse readLogFile(@RequestParam String fileName){
        return logService.readLogFile(fileName);
    }
}
