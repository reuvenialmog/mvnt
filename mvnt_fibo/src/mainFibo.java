
public class mainFibo {
	public static void main(String[] args) {

		FibonacciHeap h = new FibonacciHeap();
	h.insert(2);
	h.insert(3);
	h.insert(6);
	h.insert(1);
		System.out.println("min:"+h.findMin().getKey());
		FibonacciHeap t= new FibonacciHeap();
		t.insert(9);
		t.insert(3);
		t.insert(8);
		t.insert(4);
		FiboHeapPrinterSid.printHeap(h);
h.deleteMin();
System.out.println("after");
FiboHeapPrinterSid.printHeap(h);



	}
}
