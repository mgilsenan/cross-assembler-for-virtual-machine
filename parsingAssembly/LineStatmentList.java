package parsingAssembly;

public class LineStatmentList {
    private static int counter;
	private Node head;

	public LineStatmentList() {

	}

	public void add(Object data) {

		if (head == null) {
			head = new Node(data);
		}

		Node temp = new Node(data);
		Node current = head;

		if (current != null) {

			while (current.getNext() != null) {
				current = current.getNext();
			}

			current.setNext(temp);
		}

		counter++;
	}

	private static int getCounter() {
		return counter;
	}

	public void add(Object data, int index) {
		Node temp = new Node(data);
		Node current = head;

		if (current != null) {
			for (int i = 0; i < index && current.getNext() != null; i++) {
				current = current.getNext();
			}
		}

		temp.setNext(current.getNext());

		current.setNext(temp);

		counter++;
	}

	public Object get(int index)
	{

		if (index < 0)
			return null;
		Node current = null;
		if (head != null) {
			current = head.getNext();
			for (int i = 0; i < index; i++) {
				if (current.getNext() == null)
					return null;

				current = current.getNext();
			}
			return current.getData();
		}
		return current;

	}

	public int size() {
		return getCounter();
	}
}
