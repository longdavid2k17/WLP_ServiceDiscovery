package pl.com.kantoch.WLP_ServiceDiscovery.module_registrator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity,Long> {
    ModuleEntity findByModuleName(String moduleName);
}
