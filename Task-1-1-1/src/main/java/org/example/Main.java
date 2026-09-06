package org.example;

/**
 * Main class of program.
 */
public class Main {

    /**
     * Repairs broken path in binHeap starting from i index.
     * @param binHeap - current broken heap
     * @param i - index of broken element in heap
     * @param size - size of heap that we should repair
     */
    private static void repairFrom(int[] binHeap, int i, int size) {
        int it = i;
        while (true) {
            int left = 2 * it + 1;
            int right = 2 * it + 2;
            int highest = it;

            if (left < size && binHeap[left] > binHeap[highest]) {
                highest = left;
            }
            if (right < size && binHeap[right] > binHeap[highest]) {
                highest = right;
            }

            if (highest == it) {
                break;
            }

            int temp = binHeap[it];
            binHeap[it] = binHeap[highest];
            binHeap[highest] = temp;

            it = highest;
        }
    }

    /**
     * Makes from standard array a max-binHeap.
     * @param array - array of ints that needs to be a binHeap
     */
    public static void makeHeap(int[] array) {
        for (int current = array.length / 2 - 1; current >= 0; current--) {
            repairFrom(array, current, array.length);
        }
    }


    /**
     * Sorts array using heapsort algorithm that behaves on max-binHeap.
     * @param array - array of ints that needs to be sorted
     */
    public static void heapsort(int[] array) {
        makeHeap(array);

        int heapSize = array.length;
        while (heapSize > 0) {
            heapSize--;
            int temp = array[0];
            array[0] = array[heapSize];
            array[heapSize] = temp;

            repairFrom(array, 0, heapSize);
        }
    }
}