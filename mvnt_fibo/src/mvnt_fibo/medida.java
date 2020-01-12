package mvnt_fibo;

public class medida {
	FibonacciHeap h = new FibonacciHeap();
	
	h.zero(); // לאפס את השדות הסטטים

	long start = System.currentTimeMillis();

	

	int max = (int)Math.pow(2,10);
	HeapNode[] arr = new HeapNode[max+1];
	
	for(int i=max; i>=0; i--) {
		arr[i]=h.insert(i);
	}
	h.deleteMin();
	
	Print(h);
	
	for(int i=0; i<=8//9//10; i++) {
		// (2^10) --> 8
		//(2^11) --> 9
		// (2^12) --> 10
				
		double sum = 0;
		for(int k=1; k<=i; k++) {
			sum+=Math.pow(0.5, k);
		}
		System.out.println(sum);
		System.out.println(max*sum+2);
		h.decreaseKey(arr[(int)(max*sum+2)], max+1);
	}
	
	h.decreaseKey(arr[max-1], max+1);
	Print(h);
	
	long elapsedTimeMillis = System.currentTimeMillis()-start;
	

	//System.out.println(h.getStart().getPrev().getChild());
	
	System.out.println(" time: "+ elapsedTimeMillis);
	System.out.println(" link: " + totalLinks());
	System.out.println(" cuts: "+totalCuts());
	System.out.println(" pot: "+ h.potential());
	System.out.println(h.marked);

	
	
}
