package service_discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public abstract class ModuleRegistrationService {
    private String SERVICE_PORT;
    private String HOST_ADDRESS;
    private String APPLICATION_CONTEXT;
    private String MODULE_NAME;
    private Map<String,String> configuration;

    abstract void initializeConfiguration();
    abstract String getModuleConfiguration(String moduleName);
    abstract void registerService();

    private void initializeHostAddress() throws UnknownHostException {
        HOST_ADDRESS = InetAddress.getLocalHost().getHostAddress();
    }

    public Map<String, String> getCurrentConfiguration() {
        return configuration;
    }
}
