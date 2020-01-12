import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//updated missions 
/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap // iris- min cuts and link fields and constractor
{
	private HeapNode min;
	HeapNode start;
	private int size;
	private int numOfTrees;
	private int numOfMarked;
	public static int totalNumOfCuts = 0;
	public static int totalNumOfLinks = 0;

	public FibonacciHeap() {
		this.min = null;
		this.start = null;
		this.numOfMarked = 0;
		this.numOfTrees = 0;
		this.size = 0;
	}

	public FibonacciHeap(HeapNode node) {
		this.min = node;
		this.start = node;
		this.numOfMarked = 0;
		this.numOfTrees = 1;
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
			nodeToInsert.setNext(nodeToInsert);
			nodeToInsert.setPrev(nodeToInsert);

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
		if (isEmpty()) {
			return;
		} else {
			if (this.min.getRank() == 1) {
				if (this.size == 1) {
					this.min = null;
					numOfMarked = 0;
				} else {
					this.min = this.min.getChild();
					this.min.setParent(null);
				}

			} else {
				if (this.min.isMark()) {
					numOfMarked--;
				}
				HeapNode child = this.min.getChild();
				this.min.getPrev().setNext(this.min.getNext());
				this.min.getNext().setPrev(this.min.getPrev());
				// this.min = this.start;
				Map<Integer, HeapNode> buckets = new HashMap<Integer, HeapNode>();
				// linking to the trees without the deleted node
				HeapNode temp = this.start;
				HeapNode tempNext = temp.getNext();
				do {
					//temp.setNext(temp);
					//temp.setPrev(temp);
					System.out.println(tempNext.getKey());
					tempNext = temp.getNext();
					System.out.println("next is "+tempNext.getKey());
					if (!buckets.containsKey(temp.getRank())) {
						buckets.put(temp.getRank(), temp);
					} else {
						while (buckets.containsKey(temp.getRank())) {
							int prevRank = temp.getRank();
							temp = link(temp, buckets.get(temp.getRank()));
							buckets.remove(prevRank);
						}
						buckets.put(temp.getRank(), temp);
					}
					temp = tempNext;
				} while (tempNext != start);

				// linking to the childrens of the deleted node
				HeapNode tempChild = child;
				if(child != null) {
					do {
						child.setParent(null); // now he is a root
						tempNext = tempChild.getNext();
						//tempChild.setNext(tempChild);
						//tempChild.setPrev(tempChild);
						if (!buckets.containsKey(tempChild.getRank())) {
							buckets.put(tempChild.getRank(), tempChild);
						} else {
							while (buckets.containsKey(tempChild.getRank())) {
								int prevRank = tempChild.getRank();
								tempChild = link(tempChild, buckets.get(tempChild.getRank()));
								buckets.remove(prevRank);
							}
							buckets.put(tempChild.getRank(), tempChild);
						}
						//tempChild = tempChild.getNext();
					} while (tempNext != child);
				}
				

				// put them together in the heap
				List<Integer> ranks = new ArrayList<Integer>(buckets.keySet());
				//System.out.println(buckets.toString());
				//System.out.println(buckets.get(3).getKey());
				int min = buckets.get(ranks.get(0)).getKey();
				this.min = buckets.get(ranks.get(0));
				System.out.println("the new min is "+this.min.getKey());//
				HeapNode tempNode = this.min;
				this.start = tempNode;
				for (int i = 0; i < ranks.size() - 1; i++) {
					HeapNode next = buckets.get(ranks.get(i + 1));
					tempNode.setNext(next);
					next.setPrev(tempNode);
					if (tempNode.getKey() > min) {
						min = tempNode.getKey();
						this.min = tempNode;
					}
					tempNode = next;
				}
				tempNode.setNext(start);
				this.start.setPrev(tempNode);
				if (tempNode.getKey() > min) {
					min = tempNode.getKey();
					this.min = tempNode;
				}
			}
		}
		this.size--;
	}

	// gets 2 nodes with the same rank and linking them.
	private HeapNode link(HeapNode node1, HeapNode node2) {
		if (node1.getKey() < node2.getKey()) {
			if(node1.getChild() == null) {
				node1.setChild(node2);
				node2.setParent(node1);
				node1.setRank(node1.getRank() + 1);
				return node1;
			}
			else {
				HeapNode tempFirstChild = node1.getChild();
				HeapNode tempLastChild = tempFirstChild.getPrev();
				node1.setChild(node2);
				node2.setNext(tempFirstChild);
				node2.getNext().setPrev(node2);
				node2.setPrev(tempLastChild);
				node2.getPrev().setNext(node2);
				node2.setParent(node1);
				node1.setRank(node1.getRank() + 1);
				return node1;
			}
		} else {// node2.getKey < node1.getKey
			if(node2.getChild() == null) {
				node2.setChild(node1);
				node1.setParent(node2);
				node2.setRank(node2.getRank() + 1);
				return node2;
			}
			else {
				HeapNode tempFirstChild = node2.getChild();
				HeapNode tempLastChild = tempFirstChild.getPrev();
				node2.setChild(node1);
				node1.setNext(tempFirstChild);
				node1.getNext().setPrev(node1);
				node1.setPrev(tempLastChild);
				node1.getPrev().setNext(node1);
				node1.setParent(node2);
				node2.setRank(node2.getRank() + 1);
				return node2;
			}	
		}
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
			start2.setPrev(end1);
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
		this.decreaseKey(x, (x.getKey() - this.min.getKey()) + 1);
		this.deleteMin();
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
		cutXfromY(x, x.getParent());
		cascadingCut(x.getParent());

	}

	public void cascadingCut(HeapNode x) {
		HeapNode y = x.getParent();
		if (y != null) {// x was not root

			if (y.getMark() == false) {
				// if y was unmarked, mark it and cut x.
				y.setMark(true);
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
		FibonacciHeap.totalNumOfCuts++;

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
		x.setMark(false);
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
		return this.numOfTrees + 2 * this.numOfMarked;
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
		return totalNumOfLinks;
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * diconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	public static int totalCuts() {
		return totalNumOfCuts;
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

	public void printHeap(FibonacciHeap H) {
		FiboHeapPrinterSid.printHeap(H);
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
