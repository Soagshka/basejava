package com.urise.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        File dir = new File("./src");
        printFiles(dir, "");
    }

    public static void printFiles(File dir, String tabs) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(tabs + "Folder " + file.getName() + " : ");
                    printFiles(file, tabs + "\t");
                }

                if (file.isFile()) {
                    System.out.println(tabs + file.getName());
                }
            }
        }
    }
}
