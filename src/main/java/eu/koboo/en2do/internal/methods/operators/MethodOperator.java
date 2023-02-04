package eu.koboo.en2do.internal.methods.operators;

import eu.koboo.en2do.internal.exception.methods.*;
import eu.koboo.en2do.utility.GenericUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Represents the MethodOperator of a method inside a repository.
 */
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MethodOperator {

    /**
     * Searches the first entity with the given filters.
     */
    FIND_FIRST("findFirstBy", (method, returnType, entityClass, repoClass) -> {
        if (GenericUtils.isNotTypeOf(entityClass, returnType)) {
            throw new MethodFindReturnTypeException(method, entityClass, repoClass);
        }
    }),
    /**
     * Searches all entities with the given filters.
     */
    FIND_MANY("findManyBy", (method, returnType, entityClass, repoClass) -> {
        if (GenericUtils.isNotTypeOf(List.class, returnType)) {
            throw new MethodFindListReturnTypeException(method, entityClass, repoClass);
        }
        Class<?> listType = GenericUtils.getGenericTypeOfReturnType(method);
        if (!listType.isAssignableFrom(entityClass)) {
            throw new MethodFindListTypeException(method, repoClass, listType);
        }
    }),
    /**
     * Deletes all entities with the given filters.
     */
    DELETE("deleteBy", (method, returnType, entityClass, repoClass) -> {
        if (GenericUtils.isNotTypeOf(Boolean.class, returnType)) {
            throw new MethodBooleanReturnTypeException(method, repoClass);
        }
    }),
    /**
     * Checks if any entity exists with the given filters.
     */
    EXISTS("existsBy", (method, returnType, entityClass, repoClass) -> {
        if (GenericUtils.isNotTypeOf(Boolean.class, returnType)) {
            throw new MethodBooleanReturnTypeException(method, repoClass);
        }
    }),
    /**
     * Counts all entities with the given filters.
     */
    COUNT("countBy", (method, returnType, entityClass, repoClass) -> {
        if (GenericUtils.isNotTypeOf(Long.class, returnType)) {
            throw new MethodLongReturnTypeException(method, repoClass);
        }
    }),
    /**
     * Creates pagination on all entities with the given filters.
     */
    PAGE("pageBy", (method, returnType, entityClass, repoClass) -> {
        if (GenericUtils.isNotTypeOf(List.class, returnType)) {
            throw new MethodFindListReturnTypeException(method, entityClass, repoClass);
        }
        Class<?> listType = GenericUtils.getGenericTypeOfReturnType(method);
        if (!listType.isAssignableFrom(entityClass)) {
            throw new MethodFindListTypeException(method, repoClass, listType);
        }
    });

    public static final MethodOperator[] VALUES = MethodOperator.values();

    @Getter
    String keyword;
    ReturnTypeValidator returnTypeValidator;

    /**
     * Replaces the method operator keyword from the given text and returns it.
     *
     * @param textWithOperator The text, with the method operator at the start
     * @return The text, without the method operator
     */
    public @NotNull String removeOperatorFrom(@NotNull String textWithOperator) {
        return textWithOperator.replaceFirst(getKeyword(), "");
    }

    /**
     * Validates the return type of the specific method operator, using the given parameters.
     *
     * @param method      The method, which should be validated
     * @param returnType, The return type of the method (Could be overridden, due to async methods)
     * @param entityClass The entity class of the validated repository
     * @param repoClass   THe repository classs
     * @throws Exception if the validation is unsuccessful.
     */
    public void validate(@NotNull Method method, @NotNull Class<?> returnType,
                         @NotNull Class<?> entityClass, @NotNull Class<?> repoClass) throws Exception {
        returnTypeValidator.check(method, returnType, entityClass, repoClass);
    }

    /**
     * Parses the method operator by the name of the method. It just checks if the method is starting
     * with any method operator of the enumeration.
     *
     * @param methodNamePart The name of the method.
     * @return The MethodOperator if any is found, otherwise null.
     */
    public static @Nullable MethodOperator parseMethodStartsWith(@NotNull String methodNamePart) {
        for (MethodOperator operator : VALUES) {
            if (!methodNamePart.startsWith(operator.getKeyword())) {
                continue;
            }
            return operator;
        }
        return null;
    }
}
