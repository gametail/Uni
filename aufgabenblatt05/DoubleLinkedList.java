/*
Informatik 2 Sommer 22.05.2019
Aufgabenblatt:    	05
Aufgabe:          	05.01
*/
package aufgabenblatt05;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements Iterable<T>{
	
	private Node<T> first;
	private Node<T> last;
	private int size;
	
	public DoubleLinkedList() {
		first = new Node<T>(null);
		last = new Node<T>(null);
		size = 0;
		first.next = last;
		last.previous = first;
	}
	public void add(T data) {
		Node<T> newData = new Node<T>(data);
		newData.previous = last.previous;
		newData.next = last;
		last.previous.next = newData;
		last.previous = newData;
		size++;
	}
	public void insert(int index, T data) {
		this.add(null);

		Node<T> tmp = last;
		for(int i = size-1+index; i < size; i++)	{
			tmp.data = tmp.previous.data;
		}
		tmp.data = data;
	}
	public T get(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("index invalid");
		Node<T> tmp = first;
		for(int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		return tmp.data;
	}
	public int getSize(	) {
		return this.size;
	}
	public Iterator<T> iterator(){
		return new DoubleLinkedListIterator();
	}
	public static class Node<T> {
		public Node<T> previous;
		public Node<T> next;
		public T data;
		
		public Node(T data) {
			this.data = data;
		}
	}
	public class DoubleLinkedListIterator implements Iterator<T>{
		//referenz auf listen start
		private Node<T> current = first.next;
		private Node<T> lastAccessed;
		
		//gucken ob naechstes element vorhanden
		public boolean hasNext() {
			return current.next != null;
		}
		
		public T next(){
			//wenn kein naechstes element dann exception
			if(!hasNext())
				throw new NoSuchElementException("there is no next");
			//ansonsten soll das alte current gespeichert werden und das neue current auf das naechste listen element zeigen 
			lastAccessed = current;
			current = current.next;
			return lastAccessed.data;
		}
		public void remove() {
			//verweise aendern
			current.next.previous = lastAccessed;
			lastAccessed.next = current.next;
			//current verschieben
			current = current.next;
			// verweis loeschen
			lastAccessed = null;
			//groeﬂe verringern
			size--;
		}
	}
	
	
}