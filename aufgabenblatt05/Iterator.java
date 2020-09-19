/*
Informatik 2 Sommer 22.05.2019
Aufgabenblatt:    	05
Aufgabe:          	05.01
*/
package aufgabenblatt05;

public interface Iterator<T> {
	public boolean hasNext();
	public T next();
	public void remove();
}
