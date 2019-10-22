package com.account;

import com.card.Card;
import com.card.CardRepository;
import com.owner.Owner;
import com.owner.OwnerRepository;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryAccountRepository implements AccountRepository {

    Map<Owner, Account> accounts = new HashMap<>();

    public Account findAccountByOwner(Owner owner) {
        return accounts.get(owner);
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public Set<Pair<String, String>> getIdentities() {
        return accounts.keySet().stream().map(Owner::getIdentity).collect(Collectors.toSet());
    }

    @Override
    public Double checkBalance(Owner owner) {
        return findAccountByOwner(owner).getAccountBalance();
    }

    @Override
    public void depositMoney(Owner owner, Double moneyAmount) {
        Account accountByOwner = findAccountByOwner(owner);
        System.out.println("Depositing money\n" +
                "was: " + accountByOwner.getAccountBalance());
        accountByOwner.setAccountBalance(accountByOwner.getAccountBalance() + moneyAmount);
        System.out.println("is: " + accountByOwner.getAccountBalance());
    }

    @Override
    public void withdrawMoney(Owner owner, Double moneyAmount) {
        Account accountByOwner = findAccountByOwner(owner);
        if (checkIfOwnerHaveEnoughMoney(accountByOwner, moneyAmount)) {
            System.out.println("Withdrawing money\n" +
                    "was: " + accountByOwner.getAccountBalance());
            accountByOwner.setAccountBalance(accountByOwner.getAccountBalance() - moneyAmount);
            System.out.println("is: " + accountByOwner.getAccountBalance());
        } else {
            System.out.println("You have not enough money on account");
        }
    }

    public void transferMoney(Owner source,Owner target,Double moneyAmount){
        changeMoneyInAccounts(source,-moneyAmount);
        System.out.println("Transfering "+moneyAmount+" to "+ target.getIdentity());
        changeMoneyInAccounts(target,moneyAmount);
    }

    @Override
    public void generateSampleAccount(OwnerRepository ownerRepository, CardRepository cardRepository) {
        Owner sampleOwner = new Owner("SAMPLE_NAME", "SAMPLE_SURNAME");
        ownerRepository.add(sampleOwner);
        cardRepository.generateCard(ownerRepository.getOwnerByHisIdentity(sampleOwner.getIdentity()),new Random().nextInt(9000)+1000);
        this.add(ownerRepository.getOwnerByHisIdentity(sampleOwner.getIdentity()),cardRepository.findCardByOwner(sampleOwner));
        findAccountByOwner(sampleOwner).setAcceptsTransfers(true);
    }

    private boolean checkIfOwnerHaveEnoughMoney(Account account, Double moneyAmount) {
        return account.getAccountBalance() >= moneyAmount;
    }

    private void changeMoneyInAccounts(Owner owner,Double moneyAmount){
        Account accountByOwner = findAccountByOwner(owner);
        accountByOwner.setAccountBalance(accountByOwner.getAccountBalance()+moneyAmount);
    }
    @Override
    public void buyPrepaidCard(Owner owner, Double moneyAmount) {
        Account accountByOwner = findAccountByOwner(owner);
        accountByOwner.setAccountBalance(accountByOwner.getAccountBalance() - moneyAmount);
    }

    @Override
    public void add(Owner owner, Card card) {
        accounts.put(owner, new Account(card, false));
    }


}
