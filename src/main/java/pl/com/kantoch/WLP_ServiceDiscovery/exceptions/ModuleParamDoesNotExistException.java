package pl.com.kantoch.WLP_ServiceDiscovery.exceptions;

public class ModuleParamDoesNotExistException extends RuntimeException{
    public ModuleParamDoesNotExistException(String module, String paramName) {
        super("Module named '"+module+"' does not have provided "+paramName+" value.");
    }

    public ModuleParamDoesNotExistException(String message) {
        super(message);
    }
}
