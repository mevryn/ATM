package com.account;

import com.card.Card;
import com.card.CardRepository;
import com.owner.Owner;
import com.owner.OwnerRepository;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

public interface AccountRepository {

    Account findAccountByOwner(Owner owner);

    List<Account> getAllAccounts();

    Set<Pair<String,String>> getIdentities();

    Double checkBalance(Owner owner);

    void depositMoney(Owner owner, Double moneyAmount);

    void withdrawMoney(Owner owner, Double moneyAmount);

    void buyPrepaidCard(Owner owner, Double moneyAmount);

    void add(Owner owner, Card card);

    void transferMoney(Owner source,Owner target,Double moneyAmount);

    void generateSampleAccount(OwnerRepository ownerRepository, CardRepository cardRepository);
}
