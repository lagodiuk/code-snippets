import java.util.Arrays;

/**
 * - The code for generation of K-combinations out of N items (exponential complexity).
 * - The code for generation of the i-th K-combination out of N items with complexity O(N*K)
 * 
 * @author Yurii Lahodiuk
 */
public class Combinations {

    public static void main(String[] args) {

        int[] arr = new int[] { 1, 2, 3, 4, 5 };
        int k = 3;

        printAllCombinations(arr, k);

        System.out.println();
        int combinationsCount = nChooseK(arr.length, k);

        for (int i = 1; i <= combinationsCount; i++) {
            printIthCombination(new int[] { 1, 2, 3, 4, 5 }, 3, i);
        }
    }

    /**
     * Prints all (N choose K) K-combinations of N items from the array arr.
     * Where, N is the length of the array arr.
     * Complexity is exponential.
     * Amount of all combinations is: N!/(K!*(N-K)!) 
     */
    static void printAllCombinations(int[] arr, int k) {
        combinations(arr, arr.length, k, new int[k]);
    }

    static void combinations(
            int[] arr, // Array of items, which are being used for creation of the combinations
            int n, // Amount of available items from the array arr
            int k, // Amount of items, which is needed to choose for the purpose of creation of the combination
            int res[] // The combination of the chosen items will be in this array
    ) {
        if (k == 0) {
            // All items are already chosen
            System.out.println(Arrays.toString(res));
            return;
        }

        if (k > n) {
            // If k > n we can't make the combination of k items out of n items
            return;
        }

        // Two branches of recursion.
        // Based on the identity:
        // (n choose k) = ((n-1) choose (k-1)) + ((n-1) choose k)

        // We choose the current item.
        // Thus, we need to do ((n-1) choose (k-1)).
        res[k - 1] = arr[n - 1];
        combinations(arr, n - 1, k - 1, res);

        // We don't choose the current item.
        // Thus, we need to do ((n-1) choose k).
        combinations(arr, n - 1, k, res);
    }

    /**
     * Prints the i-th K-combination of N items from the array arr.
     * Where, N is the length of the array arr.
     * Complexity is O(N*K). 
     */
    static void printIthCombination(int[] arr, int k, int ith) {
        // Initialization of the memoization table
        int[][] mem = new int[arr.length + 1][k + 1];
        for (int[] row : mem) {
            Arrays.fill(row, NOT_INITIALIZED);
        }
        ith(arr, arr.length, k, ith, mem, new int[k]);
    }

    /**
     * Complexity: O(N*K).
     */
    static void ith(
            int[] arr, // Array of items, which are being used for creation of the combinations
            int n, // Amount of available items from the array arr
            int k, // Amount of items, which is needed to choose for the purpose of creation of the combination
            int ith, // Index of the K-combination to print
            int[][] mem, // Memoization table for the values of (N choose K)
            int[] res // The combination of the chosen items will be in this array
    ) {

        if (k == 0) {
            // All items are already chosen
            System.out.println(Arrays.toString(res));
            return;
        }
        if (n < k) {
            // If k > n we can't make any combination of k items out of n items
            throw new RuntimeException("Unreachable code!");
        }

        // Amount of combinations in case if the current item will be included
        int cnk = nChooseK(n - 1, k - 1, mem);
        if (ith <= cnk) {
            // If index of the i-th combination is smaller than cnk -
            // we should include the current item
            res[k - 1] = arr[n - 1];
            ith(arr, n - 1, k - 1, ith, mem, res);
        } else {
            // Otherwise we shouldn't include the current item
            ith(arr, n - 1, k, ith - cnk, mem, res);
        }
    }

    /**
     * Returns N!/(K!*(N-K)!)
     * Based on the identity: (n choose k) = ((n-1) choose (k-1)) + ((n-1) choose k)
     * Uses the memoization table.
     * Complexity: O(N*K).
     */
    static int nChooseK(int n, int k, int[][] mem) {
        if (k == 0) {
            return 1;
        }
        if (n < k) {
            return 0;
        }
        if (mem[n][k] != NOT_INITIALIZED) {
            return mem[n][k];
        }
        mem[n][k] = nChooseK(n - 1, k - 1, mem);
        mem[n][k] += nChooseK(n - 1, k, mem);
        return mem[n][k];
    }

    /**
     * Returns N!/(K!*(N-K)!)
     */
    static int nChooseK(int N, int K) {
        // Initialization of the memoization table
        int[][] mem = new int[N + 1][K + 1];
        for (int[] row : mem) {
            Arrays.fill(row, NOT_INITIALIZED);
        }
        return nChooseK(N, K, mem);
    }

    static final int NOT_INITIALIZED = -1;
}
