package com.nequi.pruebanequi.infrastructure.output.rest.constants;

public class Constants {

    public static final String PATH_PRODUCT = "/product";
    public static final String PATH_BRANCH = "/branch";
    public static final String PATH_FRANCHISE = "/franchise";
    public static final String PATH_BRANCH_PRODUCT = "/branchProduct";
    public static final String PATH_FRANCHISE_BRANCH_PRODUCT = "/franchiseBranch";
    public static final String BRANCH_ROUTER_FUNCTION = "branchRouterFunction";
    public static final String PRODUCT_ROUTER_FUNCTION = "productRouterFunction";
    public static final String FRANCHISE_ROUTER_FUNCTION = "franchiseRouterFunction";
    public static final String BRANCH_PRODUCT_ROUTER_FUNCTION = "branchProductRouterFunction";
    public static final String FRANCHISE_BRANCH_ROUTER_FUNCTION = "franchiseBranchRouterFunction";
    public static final String PATH_PATTERN = "/";
    public static final String PATH_DELETE_PATTERN = "/{id}";
    public static final String PATH_DELETE_VARIABLE = "id";
    public static final String PATH_UPDATE_PATTERN = "/product";
    public static final String PATH_GET_BRANCH_PATTERN = "/productinfo/{id}";
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;
    public static final String MESSAGE_INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String HANDLING_ERROR = "Handling error: {}";
}
