package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File(".");
        printFiles(dir);
    }

    public static void printFiles(File dir) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                printFiles(file);
            }

            if (file.isFile())
            {
                System.out.println(file.getName());
            }
        }
    }
}
