package com.system.kupon.ex;

public class CouponNotInStockException extends RuntimeException {
    public CouponNotInStockException(String message) {
        super(message);
    }
}
