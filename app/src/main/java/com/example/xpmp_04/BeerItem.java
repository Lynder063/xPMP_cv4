package com.example.xpmp_04;

import java.io.Serializable;

public class BeerItem implements Serializable {

    private final long id;
    private final String stupen;
    private final int amount;

    public BeerItem(long id, String stupen, int amount) {
        this.id = id;
        this.stupen = stupen;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public String getStupen() {
        return stupen;
    }

    public int getAmount() {
        return amount;
    }
}
