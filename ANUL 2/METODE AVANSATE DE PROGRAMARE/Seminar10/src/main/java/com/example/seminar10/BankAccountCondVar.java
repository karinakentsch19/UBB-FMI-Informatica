package com.example.seminar10;

import java.util.concurrent.locks.*;

public class BankAccountCondVar {
    private double balance;

    private final Lock mutex = new ReentrantLock();

    private final Condition balanceIncreased = mutex.newCondition();

    public BankAccountCondVar(double bal) {
        balance = bal;
    }

    public BankAccountCondVar() {
        this(0);
    }

    public double getBalance() {
        synchronized (mutex){
            return balance;
        }
    }

    public void deposit(double amt) {
        synchronized (mutex){
            double temp = balance + amt;
//        try {
//            Thread.sleep(3000); // simulate processing time
//        } catch (InterruptedException ie) {
//            System.err.println(ie.getMessage());
//        }
            balance = temp;
            mutex.notifyAll();
            System.out.println("after deposit balance = $" + balance);
        }
    }

    public void withdraw(double amt){
        synchronized (mutex){
            while (balance < amt) {
                System.out.println("Insufficient funds!");
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            double temp = balance - amt;
//        try {
//            Thread.sleep(200); // simulate processing time
//        } catch (InterruptedException ie) {
//            System.err.println(ie.getMessage());
//        }
            balance = temp;
            System.out.println("after withdrawal balance = $" + balance);
        }
    }
}
