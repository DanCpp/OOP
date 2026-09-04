package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void repair_from() {
    }

    @Test
    void makeHeap_test_1() {
        int[] expected = new int[] {10, 8, 9, 1, 3, 5, 2};
        int[] result = new int[] {1, 3, 5, 2, 8, 9, 10};

        Main.makeHeap(result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void makeHeap_test_2() {
        int[] result = new int[] {1, 7, 20, 21, 52, 100, 1, 2, 3};
        Main.makeHeap(result);

        for (int i = 0; i < result.length; i++) {
            Assertions.assertTrue((2 * i + 1) >= result.length || result[i] > result[2 * i + 1]);
            Assertions.assertTrue((2 * i + 2) >= result.length || result[i] > result[2 * i + 2]);
        }
    }

    @Test
    void heapsort() {
    }
}