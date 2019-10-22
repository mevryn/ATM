package com.atm;

import com.account.Account;
import com.account.AccountRepository;
import com.account.InMemoryAccountRepository;
import com.card.Card;
import com.card.CardRepository;
import com.card.InMemoryCardRepository;
import com.owner.InMemoryOwnerRepository;
import com.owner.Owner;
import com.owner.OwnerRepository;
import com.prepaidcard.PrepaidCard;
import javafx.util.Pair;

import java.util.Scanner;

public class ConsoleATMController implements ATMController {

    private ATM atm;

    private CardRepository cardRepository = new InMemoryCardRepository();
    private OwnerRepository ownerRepository = new InMemoryOwnerRepository();
    private AccountRepository accountRepository = new InMemoryAccountRepository();
    private Owner currentUser;

    public ConsoleATMController(ATM atm) {
        this.atm = atm;
    }

    private String getMenuText() {
        return "Hello in World Bank ATM " + currentUser.getName() + " " + currentUser.getSurname() +
                "\nType 'insert' to insert your card\n" +
                "Type 'exit' to exit ATM";

    }

    private String provideActiveMenuText() {
        return "What do you want to do?\n" +
                "-'withdraw' for money withdrawal.\n" +
                "-'deposit' for money deposit.\n" +
                "-'prepaid' for pre-paid telephone cards.\n" +
                "-'balance' for your bank account balance.\n" +
                "-'transfer' for money transfer to another account.\n" +
                "-'stop' for card removal.\n" +
                "-'add' to add Sample Account";
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        currentUser = introduction(scanner);
        if (!ownerRepository.checkIfCurrentUserInRepo(currentUser)) {
            addCardAndUser(scanner);
        }
        while (running) {
            System.out.println(getMenuText());
            scanner.nextLine();
            String input = scanner.nextLine();
            switch (input) {
                case "insert": {
                    boolean waitingForPin = true;
                    while (waitingForPin) {
                        System.out.println("Provide your Pin");
                        int pin = scanner.nextInt();
                        if (cardRepository.authYourCard(currentUser, pin)) {
                            waitingForPin = false;
                            System.out.println(provideActiveMenuText());
                            boolean isSessionActive = true;
                            scanner.nextLine();
                            while (isSessionActive) {
                                String menuOption = scanner.nextLine();
                                switch (menuOption) {
                                    case "withdraw": {
                                        System.out.println("How much you want to withdraw?");
                                        Double moneyAmount = scanner.nextDouble();
                                        accountRepository.withdrawMoney(currentUser, moneyAmount);
                                        scanner.nextLine();
                                        break;
                                    }
                                    case "deposit": {
                                        System.out.println("How much you want to deposit?");
                                        Double moneyAmount = scanner.nextDouble();
                                        accountRepository.depositMoney(currentUser, moneyAmount);
                                        scanner.nextLine();
                                        break;
                                    }
                                    case "prepaid": {
                                        System.out.println("From what operator you want to buy a pre-paid card?");
                                        String operator = scanner.nextLine();
                                        System.out.println("What amount of money will be in this pre-paid card?");
                                        double amount = scanner.nextDouble();
                                        PrepaidCard prepaidCard = new PrepaidCard(operator, amount);
                                        scanner.nextLine();
                                        if (checkIfUserCanAffordPrepaid(currentUser, amount)) {
                                            System.out.println("Buying pre-paid to " + operator + " for " + amount);
                                            accountRepository.buyPrepaidCard(currentUser, amount);
                                            ownerRepository.addPrepaidCard(currentUser, prepaidCard);
                                            System.out.println("Account balance after payment: " + accountRepository
                                                    .checkBalance(currentUser));
                                        } else {
                                            System.out.println("You have not enough money on account");
                                        }
                                        break;
                                    }
                                    case "balance": {
                                        System.out.println(accountRepository.checkBalance(currentUser));
                                        break;
                                    }
                                    case "transfer": {
                                        transferMoney(scanner);
                                        break;
                                    }
                                    case "stop": {
                                        isSessionActive = false;
                                        break;
                                    }
                                    case "add": {
                                        accountRepository.generateSampleAccount(ownerRepository,cardRepository);
                                        break;
                                    }
                                }
                                if (isSessionActive) {
                                    System.out.println(provideActiveMenuText());
                                }
                            }
                        } else {
                            System.out.println("Provide valid pin.\n");
                        }
                    }
                    break;
                }
                case "exit": {
                    running = false;
                    break;
                }
            }
        }

    }

    private void transferMoney(Scanner scanner) {
        if (accountRepository.findAccountByOwner(currentUser).isAcceptingTransfers()) {
            System.out.println("Where you want to transfer your money?\n" +
                    "Reminder: You can only transfer your money to accounts where " +
                    "transfers are accepted.");
            StringBuilder sb = new StringBuilder();
            for (Account account : accountRepository.getAllAccounts()) {
                if (account.isAcceptingTransfers() && !accountRepository.findAccountByOwner(currentUser).equals(account)) {
                    sb.append(account);
                }
            }
            System.out.println(sb.toString());
            System.out.println("Type Account Owner name to transfer");
            String targetAccountOwnersName = scanner.nextLine();
            String[] nameAndSurnameArray = targetAccountOwnersName.split(" ");
            Pair<String,String> targetIdentity = new Pair<>(nameAndSurnameArray[0],nameAndSurnameArray[1]);
            if(accountRepository.getIdentities().contains(targetIdentity)){
                System.out.println("How much you want to transfer?");
                Double amount = scanner.nextDouble();
                scanner.nextLine();
                accountRepository.transferMoney(currentUser,ownerRepository.getOwnerByHisIdentity(targetIdentity),amount);
            }else{
                System.out.println("Provided Owner does not exists in repository");
            }
        } else {
            System.out
                    .println("Your account need to accept transfers to proceed. Activate transfers for your account?\n" +
                            "yes/no?");
            String input = scanner.nextLine();
            if (input.equals("yes")) {
                accountRepository.findAccountByOwner(currentUser).setAcceptsTransfers(true);
                transferMoney(scanner);
            }
        }
    }

    private boolean checkIfUserCanAffordPrepaid(Owner owner, Double moneyAmount) {
        return accountRepository.checkBalance(owner) >= moneyAmount;
    }

    private void addCardAndUser(Scanner scanner) {
        ownerRepository.add(currentUser);
        System.out.println("You are new ATM user.\n" +
                "Generate your new Credit Card");
        boolean waitingForValidPin = true;
        int pin = 0;
        while (waitingForValidPin) {
            System.out.println("Provide 4-digit pin");
            pin = scanner.nextInt();
            if (pin >= 1000 && pin < 10000) {
                waitingForValidPin = false;
            }
        }
        Card card = cardRepository.generateCard(currentUser, pin);
        accountRepository.add(currentUser, card);
        System.out.println("Your credit card is: \n");
        System.out.println(cardRepository.findCardByOwner(currentUser).toString());
    }

    private Owner introduction(Scanner scanner) {
        System.out.println("Introduce Yourself\n" +
                "Your name: ");
        String name = scanner.nextLine();
        System.out.println("Your surname: ");
        String surname = scanner.nextLine();

        return (new Owner(name, surname));
    }

}
