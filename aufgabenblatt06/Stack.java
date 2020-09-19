/*
Informatik 2 Sommer 22.05.2019
Aufgabenblatt:    	05
Aufgabe:          	05.02
*/
package aufgabenblatt06;
import java.util.NoSuchElementException;
import Utils.StackInterface;

public class Stack<T> implements StackInterface<T> {
	private T[] stack = null;
	private int index = 0;
	private int size = 0;
	//konstruktor erstellt array mit physischer groesse 2
	public Stack() {
		stack = (T[])new Object[2];
	}
	@Override
	public void push(T data) {
		//size wird erhoeht und es wird geguckt ob size die physische groesse ueberschreitet 
		//wenn ja dann wird neues array mit doppelter physischer groesse erstellt,altes kopiert und ueberschrieben
		size++;
		if(size > stack.length) {
			T[] tmp = (T[])new Object[stack.length*2];
			for(int i = 0; i < stack.length; i++) {
				tmp[i] = stack[i];
			}
			stack = tmp;
		}	
		//stack wird am aktuellen index mit daten belegt und um 1 erhpeht um naechstes element eine stelle weiter im array zu platieren
		stack[index] = data;
		index++;
	}

	@Override
	public T pop() {
		//es wird geguckt ob stack leer ist wenn ja ist der methodenaufruf illegal
		if(isEmpty())
			throw new NoSuchElementException("Stack is Empty");
		//das oberste element auf dem stack wird zwischen gespeichert und im array geloescht und index, sowie size um 1 verringert
		T tmp = stack[size-1];
		stack[size-1] = null;
		index--;
		size--;
		//zwischengespeichertes elemnet wird zurueckgegeben
		return tmp;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}
	
	public void printAll() {
		for(int i = 0; i < size; i++)
			System.out.println(stack[i]);
	}
}
