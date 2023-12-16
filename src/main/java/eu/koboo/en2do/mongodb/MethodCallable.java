package eu.koboo.en2do.mongodb;

/**
 * This interface is used to retrieve a return value of the dynamic method
 */
@FunctionalInterface
public interface MethodCallable {

    /**
     * Called to get the object
     *
     * @return The return value of the method
     * @throws Exception if anything bad happens
     */
    Object call() throws Exception;
}
