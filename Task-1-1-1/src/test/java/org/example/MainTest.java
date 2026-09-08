package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    void checkHeap(int[] result) {
        for (int i = 0; i < result.length; i++) {
            Assertions.assertTrue((2 * i + 1) >= result.length || result[i] >= result[2 * i + 1]);
            Assertions.assertTrue((2 * i + 2) >= result.length || result[i] >= result[2 * i + 2]);
        }
    }

    void checkSorted(int[] result) {
        for (int i = 0; i < result.length - 1; i++) {
            Assertions.assertTrue(result[i] <= result[i + 1]);
        }
    }

    @Test
    void makeHeapTestConcreteSmallHeap() {
        int[] result = new int[] {1, 3, 5, 2, 8, 9, 10};

        Main.makeHeap(result);
        checkHeap(result);
    }

    @Test
    void makeHeapTestConcreteBigHeap() {
        int[] result = new int[] {1, 7, 20, 21, 52, 100, 1, 2, 3};
        Main.makeHeap(result);
        checkHeap(result);
    }

    @Test
    void makeHeapTestRandomArray() {
        int size = 100;
        int seed = 500;
        int[] result = new int[size];
        Random randomizer = new Random(seed);

        for (int i = 0; i < size; i++) {
            result[i] = randomizer.nextInt(size);
        }

        Main.makeHeap(result);
        checkHeap(result);
    }

    @Test
    void heapsortTestConcreteArray() {
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] result = new int[] {6, 2, 1, 3, 4, 8, 7, 10, 9, 5};
        Main.heapsort(result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void heapsortTestRandomArray() {
        int size = 1000;
        int seed = 500;
        int[] result = new int[size];
        Random randomizer = new Random(seed);

        for (int i = 0; i < size; i++) {
            result[i] = randomizer.nextInt(size);
        }

        int[] expected = result.clone();
        Arrays.sort(expected);

        Main.heapsort(result);
        checkSorted(result);
        Assertions.assertArrayEquals(expected, result);
    }


    @Test
    void heapsortTestEmptyArray() {
        int[] actual = new int[]{};
        int[] expected = new int[]{};

        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }


    @Test
    void heapsortTestOneElementArray() {
        int[] actual = new int[]{5};
        int[] expected = new int[]{5};

        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void heapsortTestTwoElementArray() {
        int[] actual = new int[]{5, 4};
        int[] expected = new int[]{4, 5};

        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }
}