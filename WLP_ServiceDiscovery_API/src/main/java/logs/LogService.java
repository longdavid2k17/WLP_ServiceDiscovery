package logs;

import response.LogFileContentResponse;

import java.io.IOException;
import java.util.Collection;

public interface LogService {
    Collection<String> listLogFiles(String moduleName) throws IOException, InterruptedException;
    LogFileContentResponse readLogFile(String moduleName, String fileName) throws IOException, InterruptedException;
}
