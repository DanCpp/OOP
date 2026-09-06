package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    void checkHeap(int[] result) {
        for (int i = 0; i < result.length; i++) {
            Assertions.assertTrue((2 * i + 1) >= result.length || result[i] >= result[2 * i + 1]);
            Assertions.assertTrue((2 * i + 2) >= result.length || result[i] >= result[2 * i + 2]);
        }
    }

    void checkSort(int[] result) {
        for (int i = 0; i < result.length - 1; i++) {
            Assertions.assertTrue(result[i] <= result[i + 1]);
        }
    }

    @Test
    void makeHeap_test_1() {
        int[] result = new int[] {1, 3, 5, 2, 8, 9, 10};

        Main.makeHeap(result);
        checkHeap(result);
    }

    @Test
    void makeHeap_test_2() {
        int[] result = new int[] {1, 7, 20, 21, 52, 100, 1, 2, 3};
        Main.makeHeap(result);
        checkHeap(result);
    }

    @Test
    void makeHeap_test_3() {
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
    void heapsort_test_1() {
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] result = new int[] {6, 2, 1, 3, 4, 8, 7, 10, 9, 5};
        Main.heapsort(result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void heapsort_test_2() {
        int size = 1000;
        int seed = 500;
        int[] result = new int[size];
        Random randomizer = new Random(seed);

        for (int i = 0; i < size; i++) {
            result[i] = randomizer.nextInt(size);
        }

        Main.heapsort(result);
        checkSort(result);
    }


    @Test
    void heapsort_test_3() {
        int[] actual = new int[]{};
        int[] expected = new int[]{};

        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }


    @Test
    void heapsort_test_4() {
        int[] actual = new int[]{5};
        int[] expected = new int[]{5};

        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void heapsort_test_5() {
        int[] actual = new int[]{5, 4};
        int[] expected = new int[]{4, 5};

        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }
}