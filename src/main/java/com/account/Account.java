package com.account;

import com.card.Card;
import com.owner.Owner;

public class Account {

    private Card card;
    private boolean acceptsTransfers;
    private String accountNumber;
    private Double accountBalance;

    public Account(Card card, boolean acceptTransfers) {
        this.card = card;
        this.acceptsTransfers = acceptTransfers;
        this.accountBalance = 0.00D;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }



    public void setAcceptsTransfers(boolean acceptsTransfers) {
        this.acceptsTransfers = acceptsTransfers;
    }

    public Card getCard() {
        return card;
    }

    public boolean isAcceptingTransfers() {
        return acceptsTransfers;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Account{" +
                "card=" + card +
                ",\n acceptsTransfers=" + acceptsTransfers +
                ",\n accountNumber='" + accountNumber + '\'' +
                ",\n accountBalance=" + accountBalance +
                "}\n";
    }
}
