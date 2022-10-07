package pl.com.kantoch.WLP_ServiceDiscovery.payloads;

public class RegisterModuleRequest {
    private String moduleName;
    private String servicePort;
    private String moduleVersion;
    private String applicationContext;
    private String hostAddress;

    public RegisterModuleRequest() {
    }

    public RegisterModuleRequest(String moduleName, String servicePort, String moduleVersion, String applicationContext, String hostAddress) {
        this.moduleName = moduleName;
        this.servicePort = servicePort;
        this.moduleVersion = moduleVersion;
        this.applicationContext = applicationContext;
        this.hostAddress = hostAddress;
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

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }
}
