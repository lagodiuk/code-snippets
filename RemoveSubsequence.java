import java.util.Stack;

// My answer to the following question:
// http://stackoverflow.com/questions/39060558/how-can-i-determine-all-possible-ways-a-subsequence-can-be-removed-from-a-sequen/39064867?stw=2#39064867

public class RemoveSubsequence {

	public static void main(String[] args) {
		remove_bottom_up(new int[]{1, 2, 1, 3, 1, 4, 4}, new int[]{1, 4, 4});
		remove_bottom_up(new int[]{8, 6, 4, 4}, new int[]{6, 4, 8});
		remove_bottom_up(new int[]{1, 1, 2}, new int[]{1});
		remove_bottom_up(new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 1, 1});
	}

	static void subseq(int[] arr1, int[] arr2) {
		boolean canBeRemoved = subseq(arr1, arr2, 0, 0, new Stack<>());
		System.out.println(canBeRemoved);
	}

	static boolean subseq(int[] arr1, int[] arr2, int i1, int i2, Stack<Integer> stack) {
		if (i1 == arr1.length) {
			if (i2 == arr2.length) {
				// print yet another version of arr1, after removal of arr2
				System.out.println(stack);
				return true;
			}
			return false;
		}

		boolean canBeRemoved = false;
		if ((i2 < arr2.length) && (arr1[i1] == arr2[i2])) {
			// current item can be removed
			canBeRemoved |= subseq(arr1, arr2, i1 + 1, i2 + 1, stack);
		}

		stack.push(arr1[i1]);
		canBeRemoved |= subseq(arr1, arr2, i1 + 1, i2, stack);
		stack.pop();

		return canBeRemoved;
	}

	static void remove_bottom_up(int[] arr1, int[] arr2) {
		boolean[][] memoized = calculate_memoization_table(arr1, arr2);
		backtrack(arr1, arr2, 0, 0, memoized, new Stack<>());
	}

	/**
	 * Has a polynomial runtime complexity: O(length(arr1) * length(arr2))
	 */
	private static boolean[][] calculate_memoization_table(int[] arr1, int[] arr2) {
		boolean[][] memoized = new boolean[arr1.length + 1][arr2.length + 1];
		memoized[arr1.length][arr2.length] = true;
		for (int i1 = arr1.length - 1; i1 >= 0; i1--) {
			for (int i2 = arr2.length; i2 >= 0; i2--) {
				if ((i2 < arr2.length) && (arr1[i1] == arr2[i2])) {
					memoized[i1][i2] = memoized[i1 + 1][i2 + 1];
				}
				memoized[i1][i2] |= memoized[i1 + 1][i2];
			}
		}
		return memoized;
	}

	/**
	 * Might have exponential runtime complexity.
	 *
	 * E.g. consider the instance of the problem, when it is needed to remove
	 * arr2 = [1,1,1] from arr1 = [1,1,1,1,1,1,1].
	 *
	 * There are 7!/(3! * 4!) = 35 ways to do it.
	 */
	static void backtrack(int[] arr1, int[] arr2, int i1, int i2, boolean[][] memoized, Stack<Integer> stack) {
		if (!memoized[i1][i2]) {
			return;
		}
		if (i1 == arr1.length) {
			System.out.println(stack);
			return;
		}
		if ((i2 < arr2.length) && (arr1[i1] == arr2[i2])) {
			backtrack(arr1, arr2, i1 + 1, i2 + 1, memoized, stack);
		}
		stack.push(arr1[i1]);
		backtrack(arr1, arr2, i1 + 1, i2, memoized, stack);
		stack.pop();
	}
}