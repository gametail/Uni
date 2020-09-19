package aufgabenblatt05;
/*
Informatik 2 Sommer 22.05.2019
Aufgabenblatt:    	05
Aufgabe:          	05.02
*/
public class TestClient {

	public static void main(String[] args) {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		Iterator<Integer> iterator = list.iterator();
		System.out.println(iterator.next());
		iterator.remove();
		System.out.println(iterator.next());
		System.out.println(iterator.next());
		
		System.out.println("---------------------------");
		
		
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(12);
		stack.push(13);
		stack.push(14);
		stack.pop();
		stack.printAll();
		System.out.println("-------------------------");
		
		Queue<Integer> queue = new Queue<Integer>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.printAll();
		queue.dequeue();
		queue.printAll();
	}

}
