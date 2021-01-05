package parsingAssembly;

public class Node {
    Node next;
	Object data;

	public Node(Object dataValue) {
		next = null;
		data = dataValue;
	}

	public Node(Object dataValue, Node nextValue) {
		next = nextValue;
		data = dataValue;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object dataValue) {
		data = dataValue;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node nextValue) {
		next = nextValue;
	}
}
