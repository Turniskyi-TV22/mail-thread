package org.example;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    static Semaphore mailplace = new Semaphore(3);
    static Semaphore postman = new Semaphore(1);
    static boolean isOpen = true;
    static Random rand = new Random();

    public static synchronized boolean getIsOpenAsync () {
        return isOpen;
    }
    public static synchronized void setIsOpenAsync (boolean value) {
        isOpen = value;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread mailThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while(getIsOpenAsync()) {
                    Thread sender = new Thread(new Sender(), String.valueOf(i));
                    sender.start();
                    i++;
                    try {
                        Thread.sleep((rand.nextInt(3) + 1) * 500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "Mail");
        mailThread.start();
        Thread.sleep(10000);
        setIsOpenAsync(false);
        System.out.println("\u001B["+ (30 + 1) + "m" + "=== MAIL IS CLOSE ===" + "\u001B[0m");

    }
}