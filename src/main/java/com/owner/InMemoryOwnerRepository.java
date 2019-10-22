package com.owner;


import com.card.Card;
import com.prepaidcard.PrepaidCard;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class InMemoryOwnerRepository implements OwnerRepository {

    Map<Pair<String, String>, Owner> owners = new HashMap<>();
    @Override
    public void add(Owner owner) {
        owners.put(new Pair<>(owner.getName(), owner.getSurname()), owner);
    }

    public Owner getOwnerByHisIdentity(Pair<String, String> identity) {
        return owners.get(identity);
    }

    public boolean checkIfCurrentUserInRepo(Owner owner) {
        Owner ownerByHisIdentity = this.getOwnerByHisIdentity(new Pair<>(owner.getName(), owner
                .getSurname()));
        return !isNull(ownerByHisIdentity);
    }

    @Override
    public void addPrepaidCard(Owner owner, PrepaidCard prepaidCard) {
        owner.getPrepaidCards().add(prepaidCard);
    }

    public List<PrepaidCard> getPrepaidCards(Owner owner){
        return owner.getPrepaidCards();
    }

}
