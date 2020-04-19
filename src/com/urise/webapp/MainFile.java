package com.urise.webapp;

import java.io.File;
import java.util.Stack;

public class MainFile {
    static Stack<String> stack = new Stack<>();

    public static void main(String[] args) {
        File dir = new File(".");
        stack.push("\t");
        printFiles(dir, stack.firstElement());
    }

    public static void printFiles(File dir, String tabs) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    StringBuilder sb = new StringBuilder();
                    stack.push("\t");
                    for (String tabString : stack) {
                        sb.append(tabString);
                    }
                    System.out.println(tabs + "Folder " + file.getName() + " : ");
                    printFiles(file, sb.toString());
                }

                if (file.isFile()) {
                    System.out.println(tabs + file.getName());
                }
            }
            stack.pop();
        }
    }
}
