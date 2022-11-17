package response;

import java.util.Collection;

public class LogWrapperEntity {
    private String moduleName;
    private Collection<String> logFiles;

    public LogWrapperEntity() {
    }

    public LogWrapperEntity(String moduleName, Collection<String> logFiles) {
        this.moduleName = moduleName;
        this.logFiles = logFiles;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Collection<String> getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(Collection<String> logFiles) {
        this.logFiles = logFiles;
    }
}
