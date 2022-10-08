package pl.com.kantoch.WLP_ServiceDiscovery.exceptions;

public class ModuleDoesNotExistException extends Exception{
    public ModuleDoesNotExistException(String moduleName) {
        super("Module "+moduleName+" does not exist! Check naming and try again.");
    }
}
