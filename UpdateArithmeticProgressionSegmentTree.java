import java.util.Random;

// My answer to the question:
// http://stackoverflow.com/questions/39929725/update-in-segment-tree/39934580?stw=2#39934580

public class UpdateArithmeticProgressionSegmentTree {

	/**
	 * There are Array A and 2 type of operation
	 *
	 * 1. Find the Sum in Range L to R
	 * 2. Update the Element in Range L to R by Value X
	 *
	 * Update should be like this
	 *
	 * A[L] = 1*X;
	 * A[L+1] = 2*X;
	 * A[L+2] = 3*X;
	 * ...
	 * A[R] = (R-L+1)*X;
	 *
	 * @author Yurii Lahodiuk (yura.lagodiuk@gmail.com)
	 */
	static class Node {
		int left; // Left boundary of the current SegmentTree node
		int right; // Right boundary of the current SegmentTree node
		int sum; // Sum on the interval
		int first; // First item of arithmetic progression
		int last; // Last item of arithmetic progression
		Node left_child;
		Node right_child;

		/**
		 * Construction of a Segment Tree
		 * which spans over the interval [l,r]
		 */
		Node(int[] arr, int l, int r) {
			this.left = l;
			this.right = r;
			if (l == r) { // Leaf
				this.sum = arr[l];
			} else { // Construct children
				int m = (l + r) / 2;
				this.left_child = new Node(arr, l, m);
				this.right_child = new Node(arr, m + 1, r);
				// Update accumulated sum
				this.sum = this.left_child.sum + this.right_child.sum;
			}
		}

		/**
		 * Lazily adds the values of the arithmetic progression
		 * with step X on the interval [l, r]
		 * O(log(N))
		 */
		void add(int l, int r, int X) {
			// Propagate the boundaries of the Arithmetic Progression
			this.propagate();
			if ((r < this.left) || (this.right < l)) {
				// If updated interval doesn't overlap with current subtree
				return;
			} else if ((l <= this.left) && (this.right <= r)) {
				// If updated interval fully covers the current subtree
				// Update the first and last items of the arithmetic progression
				int first_item_offset = (this.left - l) + 1;
				int last_item_offset = (this.right - l) + 1;
				this.first = X * first_item_offset;
				this.last = X * last_item_offset;
				// Propagate the boundaries of the Arithmetic Progression
				this.propagate();
			} else {
				// If updated interval partially overlaps with current subtree
				this.left_child.add(l, r, X);
				this.right_child.add(l, r, X);
				// Update accumulated sum
				this.sum = this.left_child.sum + this.right_child.sum;
			}
		}

		/**
		 * Returns the sum on the interval [l, r]
		 * O(log(N))
		 */
		int query(int l, int r) {
			// Propagate the boundaries of the Arithmetic Progression
			this.propagate();
			if ((r < this.left) || (this.right < l)) {
				// If requested interval doesn't overlap with current subtree
				return 0;
			} else if ((l <= this.left) && (this.right <= r)) {
				// If requested interval fully covers the current subtree
				return this.sum;
			} else {
				// If requested interval partially overlaps with current subtree
				return this.left_child.query(l, r) + this.right_child.query(l, r);
			}
		}

		/**
		 * Lazy propagation
		 * O(1)
		 */
		void propagate() {
			// Update the accumulated value
			// with the sum of Arithmetic Progression
			int items_count = (this.right - this.left) + 1;
			this.sum += ((this.first + this.last) * items_count) / 2;
			if (this.right != this.left) { // Current node is not a leaf
				// Calculate the step of the Arithmetic Progression of the current node
				int step = (this.last - this.first) / (items_count - 1);
				// Update the first and last items of the arithmetic progression
				// inside the left and right subtrees
				// Distribute the arithmetic progression between child nodes
				// [a(1) to a(N)] -> [a(1) to a(N/2)] and [a(N/2+1) to a(N)]
				int mid = (items_count - 1) / 2;
				this.left_child.first += this.first;
				this.left_child.last += this.first + (step * mid);
				this.right_child.first += this.first + (step * (mid + 1));
				this.right_child.last += this.last;
			}
			// Reset the arithmetic progression of the current node
			this.first = 0;
			this.last = 0;
		}
	}
	public static void main(String[] args) {
		// Initialize the random generator with predefined seed,
		// in order to make the test reproducible
		Random rnd = new Random(1);

		int test_cases_num = 40;
		int max_arr_size = 1000;
		int num_queries = 10000;
		int max_progression_step = 100;

		for (int test = 0; test < test_cases_num; test++) {
			// Create array of the random length
			int[] arr = new int[rnd.nextInt(max_arr_size) + 1];
			Node segmentTree = new Node(arr, 0, arr.length - 1);

			for (int query = 0; query < num_queries; query++) {
				if (rnd.nextDouble() < 0.5) {
					// Update on interval [l,r]
					int l = rnd.nextInt(arr.length);
					int r = rnd.nextInt(arr.length - l) + l;
					int X = rnd.nextInt(max_progression_step);
					update_sequential(arr, l, r, X); // O(N)
					segmentTree.add(l, r, X); // O(log(N))
				}
				else {
					// Request sum on interval [l,r]
					int l = rnd.nextInt(arr.length);
					int r = rnd.nextInt(arr.length - l) + l;
					int expected = query_sequential(arr, l, r); // O(N)
					int actual = segmentTree.query(l, r); // O(log(N))
					if (expected != actual) {
						throw new RuntimeException("Results are different!");
					}
				}
			}
		}
		System.out.println("All results are equal!");
	}

	static void update_sequential(int[] arr, int left, int right, int X) {
		for (int i = left; i <= right; i++) {
			arr[i] += X * ((i - left) + 1);
		}
	}

	static int query_sequential(int[] arr, int left, int right) {
		int sum = 0;
		for (int i = left; i <= right; i++) {
			sum += arr[i];
		}
		return sum;
	}
}
