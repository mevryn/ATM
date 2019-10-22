package com;

import com.atm.ATM;
import com.atm.ATMController;
import com.atm.ConsoleATMController;

public class Application {

    public static void main(String[] args) {
        ATM atm = new ATM(50000);
        ATMController atmController = new ConsoleATMController(atm);
        atmController.start();
    }
}
