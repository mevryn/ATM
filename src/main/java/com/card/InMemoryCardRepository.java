package com.card;

import com.owner.Owner;

import java.util.*;

import static java.util.Objects.isNull;

public class InMemoryCardRepository implements CardRepository {

    Map<Owner, Card> cards = new HashMap<>();

    @Override
    public Card findCardByOwner(Owner owner) {
        return cards.get(owner);
    }

    public Card generateCard(Owner owner, int pin) {
        Card generatedCard = new Card(generateCardNumber(), owner, generateExpireTime(), generateCVC(), pin);
        cards.put(owner, generatedCard);
        return generatedCard;
    }

    private Date generateExpireTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 5); // to get previous year add -1
        Date nextYear = cal.getTime();
        return nextYear;
    }

    private int generateCVC() {
        return new Random().nextInt(900) + 100;
    }

    private String generateCardNumber() {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(new Random().nextInt(10));
            if (i == 3 || i == 7 || i == 11) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public boolean authYourCard(Owner owner, int pin) {
        return !isNull(findCardByOwner(owner)) && findCardByOwner(owner).getPin() == pin;
    }



}

