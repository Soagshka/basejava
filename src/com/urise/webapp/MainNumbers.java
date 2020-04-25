package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainNumbers {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5)));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5, 1)));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).filter(value -> value <= 9 && value > 0).distinct().sorted().reduce(0, (left, right) -> Integer.parseInt(left + String.valueOf(right)));
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        if (integers.stream().mapToInt(value -> value).sum() % 2 == 0) {
            return integers.stream().filter(i -> i % 2 != 0).collect(Collectors.toList());
        }
        return integers.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
    }
}