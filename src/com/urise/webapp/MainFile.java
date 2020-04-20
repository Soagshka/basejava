package com.urise.webapp;

import java.io.File;

public class MainFile {

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        File dir = new File("./src");
        printFiles(dir, "");
    }

    public static void printFiles(File dir, String tabs) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(sb.append("\t") + "Folder " + file.getName() + " : ");
                    printFiles(file, sb.toString());
                }

                if (file.isFile()) {
                    System.out.println(tabs + file.getName());
                }
            }
            int i = sb.indexOf("\t");
            if (i != -1) {
                sb.delete(i, i + "\t".length());
            }
        }
    }
}
