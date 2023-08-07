package eu.koboo.en2do.mongodb.exception.repository;

public class RepositoryIdNotFoundException extends Exception {

    public RepositoryIdNotFoundException(Class<?> repoClass, Class<?> idClass) {
        super("Couldn't find " + idClass.getName() + " field in entity of " + repoClass.getName() + "! " +
            "That's a required annotation.");
    }
}
