package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File(".");
        printFiles(dir);
    }

    public static void printFiles(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printFiles(file);
                }

                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        }
    }
}
