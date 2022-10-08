package pl.com.kantoch.WLP_ServiceDiscovery.module_registrator;

import java.time.LocalDateTime;

public class ModuleEntityBuilder {
    private final ModuleEntity moduleEntity;

    public ModuleEntityBuilder() {
        this.moduleEntity = new ModuleEntity();
    }

    public ModuleEntityBuilder moduleName(String moduleName) {
        this.moduleEntity.setModuleName(moduleName);
        return this;
    }

    public ModuleEntityBuilder servicePort(String servicePort) {
        this.moduleEntity.setServicePort(servicePort);
        return this;
    }

    public ModuleEntityBuilder hostAddress(String hostAddress) {
        this.moduleEntity.setHostAddress(hostAddress);
        return this;
    }

    public ModuleEntityBuilder moduleVersion(String moduleVersion) {
        this.moduleEntity.setModuleVersion(moduleVersion);
        return this;
    }

    public ModuleEntityBuilder firstRegistrationDate(LocalDateTime firstRegistrationDate) {
        this.moduleEntity.setFirstRegistrationDate(firstRegistrationDate);
        return this;
    }

    public ModuleEntityBuilder lastActivityDate(LocalDateTime lastActivityDate) {
        this.moduleEntity.setLastActivityDate(lastActivityDate);
        return this;
    }

    public ModuleEntity build() {
        return this.moduleEntity;
    }
}
