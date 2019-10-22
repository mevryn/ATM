package com.card;

import com.owner.Owner;

import java.util.Date;

public class Card {
    private final String number;
    private final Owner owner;
    private final Date expireTime;
    private final int cvc;
    private int pin;

    public Card(String number, Owner owner, Date expireTime, int cvc, int pin) {
        this.number = number;
        this.owner = owner;
        this.expireTime = expireTime;
        this.cvc = cvc;
        this.pin = pin;
    }


    @Override
    public String toString() {
        return "Card{" +
                "number='" + number + '\'' +
                ", owner=" + owner +
                ", expireTime=" + expireTime +
                ", cvc=" + cvc +
                ", pin=" + pin +
                '}';
    }

    public Owner getOwner() {
        return owner;
    }

    public int getPin() {
        return pin;
    }
}
