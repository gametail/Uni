/*
Informatik 2 Sommer 22.05.2019
Aufgabenblatt:    	05
Aufgabe:          	05.02
*/
package aufgabenblatt06;
import java.util.NoSuchElementException;

import Utils.QueueInterface;

public class Queue<T> implements QueueInterface<T> {
	private T[] queue = null;
	private int size;
	
	public Queue() {
		//Queue aös array physische groesse 2
		queue =(T[]) new Object[2];
		size = 0;
	}
	@Override
	public void enqueue(T data) {
		//size um 1 erhoeht --> wenn size groesser als array length dann array verdoppeln selbes prinzip wie beim stack
		size++;
		if(size > queue.length) {
			T[] tmp = (T[])new Object[queue.length*2];
			for(int i = 0; i < queue.length; i++) {
				tmp[i] = queue[i];
			}
			queue = tmp;
		}	
		//durch die logische groesse -1 (wegen array start bei 0) die in size steht kann man data ans ende platzieren
		queue[size-1] = data;
	}

	@Override
	public T dequeue() {
		//wenn leer exception
		if(isEmpty())
			throw new NoSuchElementException();
		
		//ansonsten tmp array mit selber groesse wie queue
		T[] tmp = (T[])new Object[queue.length];
		//erstes element in queue zwischenspeichern
		T tmp2 = queue[0];
		//elemente von queue um -1 verschoben in tmp schreiben 
		for(int i = 1; i<queue.length; i++)
			tmp[i-1] = queue[i];
		//queue durch tmp ersetzen groesse verringern und zwischengespeichertes element wird zurueckgegeben
		queue = tmp;
		size--;
		return tmp2;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void printAll() {
		for(int i = 0; i<size; i++)
			System.out.print(queue[i] + " ");
		
		System.out.println();
	}

}
