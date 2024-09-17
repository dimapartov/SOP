package com.example.sop.enums;

public enum OrderStatusEnum {

    ACCEPTED(1),
    PROCESSING(2),
    COMPLETED(3),
    CANCELED(4);


    private final int value;


    OrderStatusEnum(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

}