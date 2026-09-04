package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void repair_from(int[] array, int i, int size) {
        int it = i;
        while (true) {
            int left = 2 * (it - i) + 1;
            int right = 2 * (it - i) + 2;
            int highest = it - i;

            if (left < size && array[left + i] > array[highest + i]) {
                highest = left;
            }
            if (right < size && array[right + i] > array[highest + i]) {
                highest = right;
            }

            if (highest == (it - i)) {
                break;
            }

            int temp = array[it];
            array[it] = array[highest + i];
            array[highest + i] = temp;

            it = highest + i;
        }
    }

    public static void makeHeap(int[] array) {
        for (int heapTop = array.length - 2; heapTop >= 0; heapTop--) {
            repair_from(array, heapTop, array.length - heapTop);
        }
    }

    public static void heapsort(int[] array) {
        
    }
}