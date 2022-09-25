package pl.com.kantoch.WLP_ServiceDiscovery.payloads;

public class RegisterModuleRequest {
    private String moduleName;
    private String servicePort;
    private String applicationContext;

    public RegisterModuleRequest() {
    }

    public RegisterModuleRequest(String moduleName, String servicePort, String applicationContext) {
        this.moduleName = moduleName;
        this.servicePort = servicePort;
        this.applicationContext = applicationContext;
    }

    public String getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(String applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }
}
