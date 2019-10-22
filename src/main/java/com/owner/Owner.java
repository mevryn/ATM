package com.owner;

import com.prepaidcard.PrepaidCard;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Owner {

    private String name;
    private String surName;
    private Pair<String,String> identity;
    private List<PrepaidCard> prepaidCards = new ArrayList<>();

    public Owner(String name, String surName) {
        this.name = name;
        this.surName = surName;
        identity= new Pair<>(this.name,this.surName);
    }

    public Pair<String, String> getIdentity() {
        return identity;
    }

    List<PrepaidCard> getPrepaidCards() {
        return prepaidCards;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surName;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                '}';
    }
}
