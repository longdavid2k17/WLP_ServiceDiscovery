package pl.com.kantoch.requests;

public class ModuleEntity {
    private String moduleName;
    private String servicePort;
    private String hostAddress;

    public ModuleEntity() {
    }

    public ModuleEntity(String moduleName, String servicePort, String hostAddress) {
        this.moduleName = moduleName;
        this.servicePort = servicePort;
        this.hostAddress = hostAddress;
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
}
