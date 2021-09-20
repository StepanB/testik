package cz.istep.javatest.rest;

public class FrameworkNotFoundException extends RuntimeException {

    public FrameworkNotFoundException(Long id) {
        super("Could not find framework " + id);
    }

}
