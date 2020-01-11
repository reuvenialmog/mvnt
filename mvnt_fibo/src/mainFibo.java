
public class mainFibo {
	public static void main(String[] args) {

		FibonacciHeap h = new FibonacciHeap();
	h.insert(2);
	h.insert(3);
	h.insert(6);
	h.insert(1);
		System.out.println("min:"+h.findMin().getKey());

		FiboHeapPrinter.Print(h);
		FiboHeapPrinterSid.printTree(h.getStart());

	}
}
