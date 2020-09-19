/*
Informatik 2 Sommer 10.06.2019
Aufgabenblatt:    	07
Aufgabe:          	07.01
*/
package aufgabenblatt07;
import Utils.Director;
import Utils.Vec2;

public class IMDBSearch {
	//binaere suche wie in vorlesung nur statt int's mit string's compareTo benutzt
	public static int binarySearch(Director[] directors, String key) {
		int low = 0;
		int high = directors.length-1;
		int mid;
		while(low <= high) {
			mid = low + (high-low)/2;
			if(directors[mid].getName().compareTo(key) == 0) {
				return mid;
			}
			else if(directors[mid].getName().compareTo(key) > 0) {
				high = mid -1;
			}
			else {
				low = mid + 1;
			}
		}
		return -1;
	}
	
	//linear array durchlaufen
	public static int linearSearch(Director[] directors, String key) {
		int tmp = -1;
		for(int i = 0; i<directors.length; i++) {
			if(directors[i].getName().compareTo(key) == 0) {
				tmp = i;
			}
		}
		return tmp;
	}
	
	
	public static void movieQuantity(Director[] directors, String[] wanted) {
		//int array mit anzahl der gedrehten filme fuer jeden aus wanted
		int[] numbers = new int[wanted.length];
		
		//fuer jeden aus wanted mit binaerer suche anzahl der filme rausgesucht
		for(int i = 0; i < wanted.length-1; i++) {
			int tmp = IMDBSearch.binarySearch(directors, wanted[i]);
			//wenn in liste wird anzahl eingetragen
			if(tmp != -1) {
				numbers[i] = directors[tmp].getDirectedMovies().size();
			}
			//wenn nicht in liste -1 
			else
				numbers[i] = -1;
		}
		//zwei vektoren um nummer des directors in wanted und anzahl seiner filme zu merken. min braucht vergleichs 
		//wert deswegen schonmal ersten wert aus numbers zuweisen
		
		Vec2 max = new Vec2(0,0);
		Vec2 min = new Vec2(0,numbers[0]);
		
		//numbers durchlaufen 
		for(int i = 0; i<numbers.length-1;i++) {
			//wenn einer der wanted nicht in director vorkommt wird er rausgefiltert 
			//weil -1 im numbers array steht und schleife faehrt fort mit dem naechsten aus wanted
			if(numbers[i] == -1)
				continue;
			//wenn groesser/kleiner dann merkt x sich den aktuellen director und y seine gedrehten filme
			if(numbers[i] > max.y) {
				max.x = i;
				max.y = numbers[i];
			}
			if(numbers[i] < min.y) {
				min.x = i;
				min.y = numbers[i];
			}
		}
		//ausgabe
		System.out.println(wanted[(int)max.x] + " has most movies with " + (int)max.y);
		System.out.println(wanted[(int)min.x] + " has least movies with " + (int)min.y);

	}
}
