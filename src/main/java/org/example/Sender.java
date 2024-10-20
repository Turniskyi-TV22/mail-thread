package org.example;

public class Sender implements Runnable{
    @Override
    public void run() {
        try {
            String color = "\u001B["+ (30 + (Integer.parseInt(Thread.currentThread().getName())% 8) ) + "m";
            String reset = "\u001B[0m";

            System.out.println(color + "Sender from thread " + Thread.currentThread().getName() + " come to mail" + reset);
            Main.mailplace.acquire();

            if(!Main.getIsOpenAsync()) {
                System.out.println(color + "Sender from thread " + Thread.currentThread().getName() + " go out from mail" + reset);
                Main.mailplace.release();
                return;
            }
            System.out.println(color + "Sender from thread " + Thread.currentThread().getName() + " come into mail and got in queue to see the postman" + reset);
            Main.postman.acquire();


            if(!Main.getIsOpenAsync()) {
                System.out.println(color + "Sender from thread " + Thread.currentThread().getName() + " go out from mail" + reset);
                Main.postman.release();
                Main.mailplace.release();
                return;
            }
            Thread.sleep(1000); //talking with postman

            System.out.println(color + "Sender from thread " + Thread.currentThread().getName() + " send his parcel" + reset);
            Main.postman.release();


            System.out.println(color + "Sender from thread " + Thread.currentThread().getName() + " go out from mail" + reset);
            Main.mailplace.release();

            Thread.sleep((Main.rand.nextInt(3) + 1) * 500); //time to send parcel

            System.out.println(color + "Receiver from thread " + Thread.currentThread().getName() + " come to mail" + reset);
            Main.mailplace.acquire();


            if(!Main.getIsOpenAsync()) {
                System.out.println(color + "Receiver from thread " + Thread.currentThread().getName() + " go out from mail" + reset);
                Main.mailplace.release();
                return;
            }
            System.out.println(color + "Receiver from thread " + Thread.currentThread().getName() + " come into mail and got in queue to see the postman" + reset);
            Main.postman.acquire();


            if(!Main.getIsOpenAsync()) {
                System.out.println(color + "Receiver from thread " + Thread.currentThread().getName() + " go out from mail" + reset);
                Main.postman.release();
                Main.mailplace.release();
                return;
            }
            Thread.sleep(1000); //talking with postman

            System.out.println(color + "Receiver from thread " + Thread.currentThread().getName() + " take his parcel" + reset);
            Main.postman.release();


            System.out.println(color + "Receiver from thread " + Thread.currentThread().getName() + " go out from mail" + reset);
            Main.mailplace.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
