package com.tathao.eshopping.model.enumerate;

public enum OrderOutletEnum {

    WAITING_CONFIRM("WAITING_CONFIRM"),
    WAITING_PICKING("WAITING_PICKING"),
    DELIVERING("DELIVERING"),
    SUCCESS("SUCCESS"),
    CANCEL("CANCEL"),
    RETURN("RETURN");

    private String value;
    OrderOutletEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
