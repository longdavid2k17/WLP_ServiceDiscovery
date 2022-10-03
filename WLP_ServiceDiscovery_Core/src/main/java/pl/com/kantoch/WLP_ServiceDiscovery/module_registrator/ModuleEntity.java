package pl.com.kantoch.WLP_ServiceDiscovery.module_registrator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.com.kantoch.serialization.LocalDateTimeDeserializer;
import pl.com.kantoch.serialization.LocalDateTimeSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @NotEmpty
    @NotNull
    @Column(name = "host_address")
    private String hostAddress;

    @NotNull
    @Column(name = "first_registration_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime firstRegistrationDate;

    @NotNull
    @Column(name = "last_activity_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastActivityDate;

    @Transient
    private String swaggerUrl;

    @Transient
    private String status;

    public ModuleEntity() {
    }

    public ModuleEntity(Long id, String moduleName, String servicePort, String hostAddress, LocalDateTime firstRegistrationDate, LocalDateTime lastActivityDate) {
        this.id = id;
        this.moduleName = moduleName;
        this.servicePort = servicePort;
        this.hostAddress = hostAddress;
        this.firstRegistrationDate = firstRegistrationDate;
        this.lastActivityDate = lastActivityDate;
        this.swaggerUrl = buildSwaggerUrl();
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

    public LocalDateTime getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(LocalDateTime firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public LocalDateTime getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDateTime lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getSwaggerUrl() {
        return swaggerUrl;
    }

    public void setSwaggerUrl(String swaggerUrl) {
        this.swaggerUrl = swaggerUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String buildSwaggerUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://")
                .append(hostAddress)
                .append(":")
                .append(servicePort)
                .append("/")
                .append("swagger-ui.html");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "ModuleEntity{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", servicePort='" + servicePort + '\'' +
                ", hostAddress='" + hostAddress + '\'' +
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

    public ModuleEntityBuilder hostAddress(String hostAddress){
        this.moduleEntity.setHostAddress(hostAddress);
        return this;
    }

    public ModuleEntityBuilder firstRegistrationDate(LocalDateTime firstRegistrationDate){
        this.moduleEntity.setFirstRegistrationDate(firstRegistrationDate);
        return this;
    }

    public ModuleEntityBuilder lastActivityDate(LocalDateTime lastActivityDate){
        this.moduleEntity.setLastActivityDate(lastActivityDate);
        return this;
    }

    public ModuleEntity get() {
        return this.moduleEntity;
    }
}
