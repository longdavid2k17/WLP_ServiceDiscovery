package pl.com.kantoch.WLP_ServiceDiscovery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.kantoch.requests.HttpRequests;

import java.io.IOException;
import java.net.http.HttpResponse;

@Service
public class ServiceDiscoveryService {
    private final Logger LOGGER = LoggerFactory.getLogger(ServiceDiscoveryService.class);

    public String getServiceStatus(String swaggerUrl) {
        if(swaggerUrl==null) return SERVICE_STATUS.NOT_FOUND.name();
        try {
            HttpResponse<String> response = HttpRequests.sendGetRequest(swaggerUrl);
            if(response.statusCode()==200 || response.statusCode()==302) return SERVICE_STATUS.RUNNING.name();
            else return SERVICE_STATUS.DOWN.name();
        } catch (IOException | InterruptedException e) {
            LOGGER.warn("Could not connect to {}",swaggerUrl);
            return SERVICE_STATUS.DOWN.name();
        }
    }
}

enum SERVICE_STATUS {
    NOT_FOUND,
    DOWN,
    RUNNING
}
