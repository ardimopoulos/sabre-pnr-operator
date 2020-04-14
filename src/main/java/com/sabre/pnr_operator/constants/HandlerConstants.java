package com.sabre.pnr_operator.constants;

public final class HandlerConstants {

    public static final String ERROR_DESC = "error.desc";
    public static final String APPROVED = "Approved";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String FAIL = "fail";

    private HandlerConstants() {
        throw new UnsupportedOperationException("Cannot instantiate constant class:" + HandlerConstants.class.getSimpleName());
    }
}
