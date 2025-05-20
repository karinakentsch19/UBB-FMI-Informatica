package com.example.seminar10;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {
    private double balance;

//    private Object mutex = new Object();

    private final ReadWriteLock mutex = new ReentrantReadWriteLock();
    private final Lock readLock = mutex.readLock();
    private final Lock writeLock = mutex.writeLock();

    public BankAccount(double bal) {
        balance = bal;
    }

    public BankAccount() {
        this(0);
    }

    public double getBalance() {
        try {
            System.out.println(Thread.currentThread().getName() + " acquiring lock");
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " acquired lock");
            Thread.sleep(3000);
            return balance;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " release lock");
        }
    }

    public void deposit(double amt) {
        writeLock.lock();
        double temp = balance + amt;
        try {
            Thread.sleep(300); // simulate processing time
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
        balance = temp;
        System.out.println("after deposit balance = $" + balance);
        writeLock.unlock();
    }

    public void withdraw(double amt) {
        writeLock.lock();
        if (balance < amt) {
            System.out.println("Insufficient funds!");
            return;
        }
        double temp = balance - amt;
        try {
            Thread.sleep(200); // simulate processing time
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
        balance = temp;
        System.out.println("after withdrawal balance = $" + balance);
        writeLock.unlock();
    }
}