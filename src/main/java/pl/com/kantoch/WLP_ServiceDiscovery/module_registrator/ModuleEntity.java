package pl.com.kantoch.WLP_ServiceDiscovery.module_registrator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "modules")
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(name = "module_name")
    private String moduleName;

    @NotEmpty
    @NotNull
    @Column(name = "service_port")
    private String servicePort;

    @NotNull
    @Column(name = "first_registration_date")
    private Date firstRegistrationDate;

    @NotNull
    @Column(name = "last_activity_date")
    private Date lastActivityDate;

    public ModuleEntity() {
    }

    public ModuleEntity(Long id, String moduleName, String servicePort, Date firstRegistrationDate, Date lastActivityDate) {
        this.id = id;
        this.moduleName = moduleName;
        this.servicePort = servicePort;
        this.firstRegistrationDate = firstRegistrationDate;
        this.lastActivityDate = lastActivityDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(Date firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    @Override
    public String toString() {
        return "ModuleEnity{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", servicePort='" + servicePort + '\'' +
                ", firstRegistrationDate=" + firstRegistrationDate +
                ", lastActivityDate=" + lastActivityDate +
                '}';
    }
}

class ModuleEntityBuilder {
    ModuleEntity moduleEntity;
    public ModuleEntityBuilder() {
        this.moduleEntity = new ModuleEntity();
    }

    public ModuleEntityBuilder moduleName(String moduleName){
        this.moduleEntity.setModuleName(moduleName);
        return this;
    }

    public ModuleEntityBuilder servicePort(String servicePort){
        this.moduleEntity.setServicePort(servicePort);
        return this;
    }

    public ModuleEntityBuilder firstRegistrationDate(Date firstRegistrationDate){
        this.moduleEntity.setFirstRegistrationDate(firstRegistrationDate);
        return this;
    }

    public ModuleEntityBuilder lastActivityDate(Date lastActivityDate){
        this.moduleEntity.setLastActivityDate(lastActivityDate);
        return this;
    }

    public ModuleEntity get() {
        return this.moduleEntity;
    }
}
