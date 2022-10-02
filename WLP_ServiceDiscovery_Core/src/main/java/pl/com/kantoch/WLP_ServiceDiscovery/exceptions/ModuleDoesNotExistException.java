package pl.com.kantoch.WLP_ServiceDiscovery.exceptions;

public class ModuleDoesNotExistException extends Exception{
    public ModuleDoesNotExistException(String message) {
        super(message);
    }
}
