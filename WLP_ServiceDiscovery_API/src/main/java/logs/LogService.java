package logs;

import response.LogFileContentResponse;
import response.LogWrapperEntity;

import java.io.IOException;
import java.util.Collection;

public interface LogService {
    Collection<LogWrapperEntity> getLogWrapper();
    Collection<String> listLogFiles(String moduleName) throws IOException, InterruptedException;
    LogFileContentResponse readLogFile(String moduleName, String fileName) throws IOException, InterruptedException;
}
