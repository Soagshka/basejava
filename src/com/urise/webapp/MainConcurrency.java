package com.urise.webapp;

public class MainConcurrency {
    public static void main(String[] args) {
        String resource1 = "resource1";
        String resource2 = "resource2";

        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1 Locked resource1");

                synchronized (resource2) {
                    System.out.println("Thread 1 locked resource2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2 Locked resource2");

                synchronized (resource1) {
                    System.out.println("Thread 2 locked resource1");
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
