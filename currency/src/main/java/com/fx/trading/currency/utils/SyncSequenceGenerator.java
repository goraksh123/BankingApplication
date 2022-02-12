package com.fx.trading.currency.utils;

public class SyncSequenceGenerator{
    private int value = 1;
    public synchronized int getNext() {
        return value++;
    }


}
