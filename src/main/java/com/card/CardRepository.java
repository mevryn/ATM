package com.card;

import com.owner.Owner;

public interface CardRepository {
    Card findCardByOwner(Owner owner);

    Card generateCard(Owner owner, int pin);

    boolean authYourCard(Owner owner, int pin);

}
