package com.owner;


import com.prepaidcard.PrepaidCard;
import javafx.util.Pair;

public interface OwnerRepository {
    void add(Owner owner);

    Owner getOwnerByHisIdentity(Pair<String, String> identity);

    boolean checkIfCurrentUserInRepo(Owner owner);

    void addPrepaidCard(Owner owner, PrepaidCard prepaidCard);
}
