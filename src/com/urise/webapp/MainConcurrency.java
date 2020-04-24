package com.urise.webapp;

public class MainConcurrency {
    public static void main(String[] args) {
        String resource1 = "resource1";
        String resource2 = "resource2";

        Thread thread1 = new Thread(() -> {
            doLock(resource1, resource2);
        });

        Thread thread2 = new Thread(() -> {
            doLock(resource2, resource1);
        });
        thread1.start();
        thread2.start();
    }

    public static <T, K> void doLock(T resource1, K resource2) {
        synchronized (resource1) {
            System.out.println("Resource 1 locked by " + resource1);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (resource2) {
                System.out.println("Resource 2 locked by " + resource2);
            }
        }
    }
}
