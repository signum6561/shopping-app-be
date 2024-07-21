package com.java.webdevelopment.shopping_app.constants;

public class Contants {
    public static final String PARAMETERIZED_DEFAULT_NOT_FOUND = "This @resource cannot be found";
    public static final String PARAMETERIZED_USER_NOT_FOUND = "Cannot find user with username '@username'";
    public static final String PARAMETERIZED_PRODUCT_NOT_FOUND = "Cannot find product with id '@id'";
    public static final String PARAMETERIZED_CATEGORY_NOT_FOUND = "Cannot find category with id '@id'";
    public static final String PARAMETERIZED_ROLE_NOT_FOUND = "Cannot find role '@roleName'";
    public static final String PARAMETERIZED_USERNAME_ALREADY_EXIST = "Username '@username' already exists";
    public static final String PARAMETERIZED_EMAIL_ALREADY_EXIST = "Email '@email' already exists";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String ACCESS_DENIED = "You don't have authorization to access this resource";
    public static final String USER_DELETED_SUCCESS = "Deleted user successfully!";
    public static final String CATEGORY_DELETED_SUCCESS = "Deleted category successfully!";
    public static final String PARAMETERIZED_USER_DELETED_SUCCESS = "Deleted user '@username' successfully!";
    public static final String PARAMETERIZED_MUST_POSITIVE = "The @param must be positive";
    public static final String NULL_PAGE_INDEX = "The page index should not be null";
    public static final String NULL_PAGE_SIZE = "The page size should not be null";
    public static final Integer MIN_PAGE_SIZE_PARAM = 1;
    public static final Integer MAX_PAGE_SIZE_PARAM = 20;
    public static final String MAX_PAGE_SIZE = "Maximum of page size is" +  MAX_PAGE_SIZE_PARAM;
    public static final String DEFAULT_PAGE_INDEX = "1";
    public static final String DEFAULT_PAGE_SIZE = "5";
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final String INVALID_PASSWORD_LENGTH = "Minimum password length is {min} characters";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";

    public static String DEFAULT_NOT_FOUND(String resourceName) {
        return PARAMETERIZED_DEFAULT_NOT_FOUND.replaceAll("@resource", resourceName);
    }

    public static String USER_NOT_FOUND(String username) {
        return PARAMETERIZED_USER_NOT_FOUND.replaceAll("@username", username);
    }

    public static String ROLE_NOT_FOUND(String roleName) {
        return PARAMETERIZED_ROLE_NOT_FOUND.replaceAll("@roleName", roleName);
    }

    public static String USERNAME_ALREADY_EXIST(String username) {
        return PARAMETERIZED_USERNAME_ALREADY_EXIST.replaceAll("@username", username);
    }

    public static String EMAIL_ALREADY_EXIST(String email) {
        return PARAMETERIZED_EMAIL_ALREADY_EXIST.replaceAll("@email", email);
    }

    public static String USER_DELETED_SUCCESS(String username) {
        return PARAMETERIZED_USER_DELETED_SUCCESS.replaceAll("@username", username);
    }

    public static String POSITIVE_PAGE_INDEX() {
        return PARAMETERIZED_MUST_POSITIVE .replaceAll("@param", "page index");
    }

    public static String POSITIVE_PAGE_SIZE() {
        return PARAMETERIZED_MUST_POSITIVE .replaceAll("@param", "page size");
    }

    public static String PRODUCT_NOT_FOUND(String id) {
        return PARAMETERIZED_PRODUCT_NOT_FOUND.replaceAll("@id", id);
    }

    public static String CATEGORY_NOT_FOUND(String id) {
        return PARAMETERIZED_CATEGORY_NOT_FOUND.replace("@id", id);
    }
}
