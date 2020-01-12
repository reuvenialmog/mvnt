import java.util.*;

public class FiboHeapPrinter {
	public static void Print(FibonacciHeap heap) {
		HashMap<Integer, Integer> spacesBeforeKey = new HashMap<>();
		if (!heap.isEmpty()) {
			assignSpaces(heap.start, spacesBeforeKey, 0);
			List<FibonacciHeap.HeapNode> nodesQueue = new ArrayList<>();
			FibonacciHeap.HeapNode currNode = heap.start;
			do {
				nodesQueue.add(currNode);
				currNode = currNode.getNext();
			} while (currNode != heap.start);

			int depth = 0;
			while (!nodesQueue.isEmpty()) {
				List<FibonacciHeap.HeapNode> children = new ArrayList<>();
				StringBuilder builderTop = new StringBuilder();
				StringBuilder builderConnectors = new StringBuilder();
				StringBuilder builderKeys = new StringBuilder();

				int spaces = 0;
				int spacesConnectorLine = 0;
				for (FibonacciHeap.HeapNode node : nodesQueue) {
					boolean firstChild = false;
					if (depth > 0) {
						firstChild = node == node.getParent().getChild();
						int spacesBefore = spacesBeforeKey.get(node.getKey());
						if (firstChild) {
							addSpaces(builderTop, spacesBefore - spacesConnectorLine, ' ');
							FibonacciHeap.HeapNode lastChild = node.getPrev();
							int lastChildSpacesBefore = spacesBeforeKey.get(lastChild.getKey());
							builderTop.append("|");

							if (lastChildSpacesBefore - spacesBefore - 1 > 0) {
								addSpaces(builderTop, lastChildSpacesBefore - spacesBefore - 1, '_');
								builderTop.append(" ");
							}
						}
						addSpaces(builderConnectors, spacesBefore - spacesConnectorLine, ' ');
						spacesConnectorLine = spacesBefore + 1;
						builderConnectors.append("|");
					}

					int spacesBefore = spacesBeforeKey.get(node.getKey());
					char spaceChar = firstChild ? ' ' : '-';
					addSpaces(builderKeys, spacesBefore - spaces, spaceChar);
					spaces = spacesBefore + (int) (Math.log10(node.getKey()) + 1);
					builderKeys.append(node.getKey());

					FibonacciHeap.HeapNode child = node.getChild();
					if (child != null) {
						do {
							children.add(child);
							child = child.getNext();
						} while (child != node.getChild());
					}
				}
				System.out.println(builderTop);
				System.out.println(builderConnectors);
				System.out.println(builderKeys);
				depth++;
				nodesQueue = children;
			}
		}
	}

	private static void addSpaces(StringBuilder sb, int amount, char c) {
		for (int i = 0; i < amount; i++) {
			sb.append(c);
		}
	}

	private static int assignSpaces(FibonacciHeap.HeapNode node, HashMap<Integer, Integer> spacesBeforeKey,
			int spacesBefore) {
		int spaces = 0;
		if (node != null) {
			FibonacciHeap.HeapNode currNode = node;
			do {
				spacesBeforeKey.put(currNode.getKey(), spacesBefore + spaces);
				if (currNode.getChild() != null) {
					spaces += assignSpaces(currNode.getChild(), spacesBeforeKey, spacesBefore + spaces);
				} else {
					int digitsNum = (int) (Math.log10(currNode.getKey()) + 1);
					spaces += digitsNum + 1;
				}
				currNode = currNode.getNext();
			} while (currNode != node);
		}
		return spaces;
	}
}
