package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void repair_from(int[] array, int i, int size) {
        int it = i;
        while (true) {
            int left = 2 * it + 1;
            int right = 2 * it + 2;
            int highest = it;

            if (left < size && array[left] > array[highest]) {
                highest = left;
            }
            if (right < size && array[right] > array[highest]) {
                highest = right;
            }

            if (highest == it) {
                break;
            }

            int temp = array[it];
            array[it] = array[highest];
            array[highest] = temp;

            it = highest;
        }
    }

    public static void makeHeap(int[] array) {
        for (int current = array.length / 2 - 1; current >= 0; current--) {
            repair_from(array, current, array.length);
        }
    }

    public static void heapsort(int[] array) {
        makeHeap(array);

        int heapSize = array.length;
        while (heapSize > 0) {
            heapSize--;
            int temp = array[0];
            array[0] = array[heapSize];
            array[heapSize] = temp;

            repair_from(array, 0, heapSize);
        }
    }
}