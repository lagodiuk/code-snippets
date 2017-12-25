import java.util.Arrays;
import java.util.BitSet;

/**
 * - The code for generation of all permutations (exponential complexity).
 * - The code for generation of the k-th permutation with complexity O(N^2)
 * 
 * @author Yurii Lahodiuk
 */
public class Permutations {

    public static void main(String[] args) {
        int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7 };

        int count = printAllPermutations(arr);

        System.out.println();
        System.out.println(count);
        System.out.println(countAllPermutations(arr.length, 0));
        System.out.println();

        for (int i = 1; i <= count; i++) {
            printKthPermutation(arr, i);
        }
    }

    /**
     * Generates all N! permutations.
     * Complexity is O(N!).  
     */
    static int printAllPermutations(int[] arr) {
        return printAllPermutations(arr, 0, new int[arr.length], new BitSet(arr.length), 0);
    }

    /**
     * Prints all permutations according to the order of the items in the original array
     */
    static int printAllPermutations(
            int[] arr, // Array of items, which are being rearranged
            int pos, // Index of the current position in the permutation (in the array tgt)
            int tgt[], // The array with the permutation
            BitSet used, // Contains the indices of the already used items
            int permutationsSoFar // Amount of permutations, generated so far
    ) {

        if (pos == arr.length) {
            // The whole permutation is constructed
            System.out.println(Arrays.toString(tgt));
            // The index of the current permutation is: (permutationsSoFar + 1)
            return permutationsSoFar + 1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (!used.get(i)) {
                used.set(i);
                tgt[pos] = arr[i];
                permutationsSoFar =
                        printAllPermutations(arr, pos + 1, tgt, used, permutationsSoFar);
                used.clear(i);
            }
        }
        return permutationsSoFar;
    }

    /**
     * Returns the amount of possible permutations of the length N, 
     * where the first K positions are already defined.
     * 
     * Actually, the function just returns the value: (N-K)!
     */
    static int countAllPermutations(
            int size, // N
            int pos // K
    ) {
        // As far as the values of the factorial can be precalculated,
        // it means that the complexity of this function can be O(1).
        int length = size - pos;

        if (length == 0) {
            return 1;
        }
        int count = 1;
        for (int i = 1; i <= length; i++) {
            count *= i;
        }
        return count;
    }

    /**
     * Complexity is O(N^2).  
     */
    static void printKthPermutation(int[] arr, int k) {
        printKthPermutation(arr, 0, new int[arr.length], new BitSet(arr.length), k, 0);
    }

    /**
     * For every position (out of N possible positions), the N possible values are being inspected.
     * Thus, the runtime complexity is O(N^2).  
     */
    static void printKthPermutation(
            int[] arr,
            int pos,
            int tgt[],
            BitSet used,
            int kth,
            int permutationsSoFar) {

        if (pos == arr.length) {
            // The whole permutation is constructed successfully
            System.out.println(Arrays.toString(tgt));
            return;
        }

        // Iterate over all items
        for (int i = 0; i < arr.length; i++) {
            // We are interested only in the not-yet used items
            if (!used.get(i)) {

                // After the item arr[i] will be placed into the position pos,
                // the amount of all ordered permutations will increase by the following delta:
                int delta = countAllPermutations(arr.length, pos + 1);
                // As far as the values of the factorial can be precalculated,
                // it means that the complexity of the function countAllPermutations can be O(1).

                // In case if the k-th position 
                // is between permutationsSoFar and (permutationsSoFar + delta) -
                // it means, that the item arr[i] should be placed into the position pos.
                if (kth <= (permutationsSoFar + delta)) {
                    used.set(i);
                    tgt[pos] = arr[i];
                    printKthPermutation(arr, pos + 1, tgt, used, kth, permutationsSoFar);
                    return;
                }

                permutationsSoFar += delta;
            }
        }
    }
}
