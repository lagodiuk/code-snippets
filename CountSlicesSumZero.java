/**
 * Counts an amount of array slices, which have the sum of all items equal to zero.
 */
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		System.out.println(test(new int[]{2, -2, 3, 0, 4, -7}));

		System.out.println(test(new int[]{0}));
		System.out.println(test(new int[]{0, 0}));
		System.out.println(test(new int[]{0, 0, 0}));
		System.out.println(test(new int[]{0, 0, 0, 0}));
		System.out.println(test(new int[]{0, 0, 0, 0, 0}));
		System.out.println(test(new int[]{0, 0, 0, 0, 0, 0}));
		System.out.println(test(new int[]{0, 0, 0, 0, 0, 0, 0}));
	}

	// Test using the brute-force O(N^3) algorithm
	static int test(int[] arr) {
		int nZeros = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				int sum = 0;
				for (int k = i; k <= j; k++) {
					sum += arr[k];
				}
				if (sum == 0) {
					nZeros++;
				}
			}
		}

		if (nZeros != solve(arr)) {
			throw new RuntimeException();
		}

		return nZeros;
	}

	// O(N*log(N)) solution
	static int solve(int[] arr) {
		return solve(arr, 0, arr.length - 1);
	}

	static int solve(int[] arr, int L, int R) {
		if (L >= R) {
			return arr[L] == 0 ? 1 : 0;
		}

		int mid = (L + R) / 2;

		int left = solve(arr, L, mid);
		int right = solve(arr, mid + 1, R);

		// O(N)
		Map<Integer, Integer> leftSums = new HashMap<>();
		int currSum = 0;
		for (int i = mid; i >= L; i--) {
			currSum += arr[i];
			leftSums.put(currSum, leftSums.getOrDefault(currSum, 0) + 1);
		}

		// O(N)
		Map<Integer, Integer> rightSums = new HashMap<>();
		currSum = 0;
		for (int i = mid + 1; i <= R; i++) {
			currSum += arr[i];
			rightSums.put(currSum, rightSums.getOrDefault(currSum, 0) + 1);
		}

		// O(N)
		int z = 0;
		for (int leftSum : leftSums.keySet()) {
			if (rightSums.containsKey(-leftSum)) {
				z += leftSums.get(leftSum) * rightSums.get(-leftSum);
			}
		}

		return left + right + z;
	}
}