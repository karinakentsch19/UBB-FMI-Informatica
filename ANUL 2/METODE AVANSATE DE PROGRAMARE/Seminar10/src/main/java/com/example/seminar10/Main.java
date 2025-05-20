package com.example.seminar10;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankAccountCondVar bankAccount = new BankAccountCondVar();

        Thread t1 = new Thread(() -> bankAccount.deposit(100));
        Thread t2 = new Thread(() -> bankAccount.deposit(200));

//        t1.start();
//        t2.start();
//
//        t1.join();
//        t2.join();

        Thread t3 = new Thread(bankAccount::getBalance);
        Thread t4 = new Thread(bankAccount::getBalance);

//        t3.start();
//        t4.start();
//
//        t3.join();
//        t4.join();

        Thread[] producers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            producers[i] = new Thread(new Producer(bankAccount, 100, i*1000));
            producers[i].start();
        }

        Thread[] consumers = new Thread[10];
        for (int i = 0; i < 10; i++) {
            consumers[i] = new Thread(new Consumer(bankAccount, 50));
            consumers[i].start();
        }

        for (int i = 0; i < 3; i++) {
            producers[i].join();
        }


        for (int i = 0; i < 10; i++) {
            consumers[i].join();
        }

        System.out.println(bankAccount.getBalance());
    }
}
