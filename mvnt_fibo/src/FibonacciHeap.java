//updated missions 
/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap // iris- min cuts and link fields and constractor
{
	private HeapNode min;
	private HeapNode start;
	private int size;
	public static int totalNumOfCuts = 0;
	public static int totalNumOfLinks = 0;

	public FibonacciHeap() {
		this.min = null;
		this.start = null;
		this.size = 0;
	}

	public FibonacciHeap(HeapNode node) {
		this.min = node;
		this.start = min;
		this.size = 1;
	}

	/**
	 * almog addition- in case we write a rec size, that we dont need to update:
	 */
	public FibonacciHeap(HeapNode node, int k) { // IF USED, REMOVE K
		this.min = node;
		this.start = min;
	}

	/**
	 * public boolean isEmpty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 */
	public boolean isEmpty() // iris
	{
		return (min == null);
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and inserts
	 * it into the heap.
	 */

	public HeapNode getStart() // almog
	{
		return this.start;
	}

	public void setStart(HeapNode newStart) // almog
	{
		this.start = newStart;
	}

	public void setSize(int k) // almog
	{
		this.size = k;
	}

	public HeapNode insert(int key) // almog. we CANT use MELD! (forum)
	{

		HeapNode nodeToInsert = new HeapNode(key);
		if (this.size() == 0) {
			// if heap is empty
			this.min = nodeToInsert;
			this.start = nodeToInsert;
		} else {
			/**
			 * single or multi node. not going to have problem with null- cause in single
			 * node, getNext is the node itself (and not null)/
			 */
			// changing pointers
			nodeToInsert.setPrev(start.getPrev());
			start.getPrev().setNext((nodeToInsert));
			nodeToInsert.setNext(start);
			start.setPrev(nodeToInsert);
		}

		// updating start
		this.start = nodeToInsert;

		// updating size
		this.size++;

		// updating min
		if (this.min.getKey() > key) {
			this.min = nodeToInsert;
		}

		// return the node
		return nodeToInsert; // should be replaced by student code
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 *
	 */
	public void deleteMin() // iris
	{
		HeapNode newRoot = this.min.getChild();
		newRoot.setParent(null);
		newRoot.setPrev(this.min.getPrev());
		this.min.getPrev().setNext(newRoot);
		newRoot.setNext(this.min.getNext());
		this.min.getNext().setPrev(newRoot);
		HeapNode tempRoot = newRoot.getNext();
		HeapNode[] bucketArray = new Array[Math.log10(this.size) / Math.log10(2)];
		while (tempRoot != newRoot) {
			if (bucketArray[tempRoot.getRank()] != null) {

			}
		}
		return; // should be replaced by student code

	}

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 *
	 */
	public HeapNode findMin() // iris
	{
		return this.min;// should be replaced by student code
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */

	public void meld(FibonacciHeap heap2) // almog
	{

		if (heap2.size() == 0) {
			// if heap2 is empty- do nothing.
			return;
		}
		if (this.size() == 0) {
			// ifheap 2 not empty, heap1 is empty- set it as heap2
			this.min = heap2.findMin();
			this.start = heap2.getStart();
		} else {
			// setting pointers to beginnings and endings
			HeapNode start2 = heap2.getStart();
			HeapNode end2 = start2.getPrev();
			HeapNode end1 = this.getStart().getPrev();

			// changing pointers
			end1.setNext(start2);
			start2.setPrev(end2);
			this.getStart().setPrev(end2);
			end2.setNext(this.getStart());

			// start remains the same.

			// updating min
			if (this.findMin().getKey() > heap2.findMin().getKey()) {
				// we still didn't change heap2 fields, do i can get it's last minimum here.
				this.min = heap2.findMin();
			}

			// updating size
			this.size += heap2.size();

		}

	}

	public void link(FibonacciHeap heap2) // almog
	{

	}

	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() // iris
	{
		return this.size; // should be replaced by student code
	}

	/**
	 * public int[] countersRep()
	 *
	 * Return a counters array, where the value of the i-th entry is the number of
	 * trees of order i in the heap.
	 * 
	 */
	public int[] countersRep() // almog
	{

		if (this.size() == 0) {
			// empty tree
			int[] empty = new int[0];
			return empty;
		}
		// find max degree
		int maxRank = this.getStart().getRank();
		HeapNode heapPointer = this.getStart();
		while (heapPointer.getNext() != start) { // going through all roots
			if (heapPointer.getRank() > maxRank) {// found new max
				maxRank = heapPointer.getRank();
			}
			heapPointer = heapPointer.getNext(); // moving forward
		}

		// Initializing new array
		int[] arr = new int[maxRank];

		// counting ranks
		HeapNode rankPointer = this.getStart();
		do {
			arr[rankPointer.getRank()]++;
			heapPointer = heapPointer.getNext();
		} while (rankPointer != start); // going through all roots

		return arr; // to be replaced by student code
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 *
	 */
	public void delete(HeapNode x) { // iris
		return; // should be replaced by student code
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * The function decreases the key of the node x by delta. The structure of the
	 * heap should be updated to reflect this chage (for example, the cascading cuts
	 * procedure should be applied if needed).
	 */
	public void decreaseKey(HeapNode x, int delta) { // almog

		// updating key
		x.setKey(x.getKey() - delta);

		// check if need to be cut
		if (x.getParent() == null) { // if root, do nothing
			return;
		}

		if (x.getKey() > x.getParent().getKey()) {
			// child is bigger, no need to cut
			return;
		}

		// if we got here, we need to cut
		cascadingCut(x);

	}

	public void cascadingCut(HeapNode x) {
		HeapNode y = x.getParent();
		if (y != null) {// x was not root

			if (y.getMark() == false) {
				// if y was unmarked, mark it and cut x.
				y.setMark(true);
				cutXfromY(x, y);
			} else {
				/**
				 * if y was marked, cut x from y, and recursively call cascading on y and it's
				 * father
				 */
				cutXfromY(x, y);
				cascadingCut(y);
			}

		}
	}

	/** cuts x from y */
	public void cutXfromY(HeapNode x, HeapNode y) {

		if (x.getNext() == x) {// x is only child
			y.setChild(null);
		}

		else { // x is not the only child
			if (y.getChild() == x) { // x is direct child of y
				y.setChild(x.getNext()); // make x's brother the new direct child
			}
			// mending sibiling's list
			x.getNext().setPrev(x.getPrev());
			x.getPrev().setNext(x.getNext());
		}

		// updating y's rank
		y.setRank(y.getRank() - 1);

		// disconnecting x into an heap
		x.setNext(x);
		x.setPrev(x);
		FibonacciHeap xAsHeap = new FibonacciHeap(x, 1);

		// adding x to this heap's root
		this.meld(xAsHeap);

	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is: Potential
	 * = #trees + 2*#marked The potential equals to the number of trees in the heap
	 * plus twice the number of marked nodes in the heap.
	 */
	public int potential() { // iris
		return 0; // should be replaced by student code
	}

	/**
	 * public static int totalLinks()
	 *
	 * This static function returns the total number of link operations made during
	 * the run-time of the program. A link operation is the operation which gets as
	 * input two trees of the same rank, and generates a tree of rank bigger by one,
	 * by hanging the tree which has larger value in its root on the tree which has
	 * smaller value in its root.
	 */
	public static int totalLinks() {
		return totalNumOfLinks; // should be replaced by student code
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * diconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	public static int totalCuts() {
		return totalNumOfCuts; // should be replaced by student code
	}

	/**
	 * public static int[] kMin(FibonacciHeap H, int k)
	 *
	 * This static function returns the k minimal elements in a binomial tree H. The
	 * function should run in O(k(logk + deg(H)).
	 */
	public static int[] kMin(FibonacciHeap H, int k) {
		int[] arr = new int[42];
		return arr; // should be replaced by student code
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than FibonacciHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode { // almog - constructor and fields

		public int key;
		// almog's fields addition
		public int rank = 0;
		public boolean mark = false;
		public HeapNode child = null;
		public HeapNode next = null;
		public HeapNode prev = null;
		public HeapNode parent = null;

		// end of almog's fields addition
		public HeapNode(int key) {
			this.key = key;
		}

		public int getKey() {
			return this.key;
		}

		// almog's getters additions
		public int getRank() {
			return this.rank;
		}

		public boolean getMark() {
			return this.mark;
		}

		public void setMark(boolean b) {
			this.mark = b;
		}

		public void setKey(int k) {
			this.key = k;
		}

		// added by Iris
		public void setRank(int rank) {
			this.rank = rank;
		}

		public boolean isMark() {
			return this.mark;
		}

		// added by Iris
		public void markNode() {
			this.mark = true;
		}

		public HeapNode getChild() {
			return this.child;
		}

		// added by Iris
		public void setChild(HeapNode node) {
			this.child = node;
		}

		public HeapNode getNext() {
			return this.next;
		}

		// added by Iris
		public void setNext(HeapNode node) {
			this.next = node;
		}

		public HeapNode getPrev() {
			return this.prev;
		}

		// added by Iris
		public void setPrev(HeapNode node) {
			this.prev = node;
		}

		public HeapNode getParent() {
			return this.parent;
		}

		// added by Iris
		public void setParent(HeapNode node) {
			this.parent = node;
		}
		// end of almog's getters additions
	}

}
