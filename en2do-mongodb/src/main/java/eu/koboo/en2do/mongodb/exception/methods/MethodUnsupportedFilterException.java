package eu.koboo.en2do.mongodb.exception.methods;

import java.lang.reflect.Method;

public class MethodUnsupportedFilterException extends Exception {

    public MethodUnsupportedFilterException(Method method, Class<?> repoClass) {
        super("Unsupported filter found on \"" + method.getName() + "\" of repository " + repoClass.getName() + "! " +
            "Please make sure to match the defined filter pattern!");
    }
}
