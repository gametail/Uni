/*
Informatik 2 Sommer 10.06.2019
Aufgabenblatt:    	07
Aufgabe:          	07.01
*/
package aufgabenblatt07;
import Utils.Director;
import Utils.IMDB;

public class TestSearch {

	public static void main(String[] args) {
		IMDB imdb =  new IMDB();
		Director[] directors = imdb.allDirectors;
		String[] wanted = imdb.wantedDirectorNames;
		
		//binary
		System.out.println("binary --------------------------------------------------");
		double start = (double)System.nanoTime();
		for(int i = 0; i < wanted.length-1; i++) {
			int tmp = IMDBSearch.binarySearch(directors, wanted[i]);
			if(tmp != -1) {
			System.out.println(directors[tmp].getName()+ " : " + directors[tmp].getDirectedMovies());
			}
		}
		double stop = (double)System.nanoTime();
		System.out.println((stop-start)*Math.pow(10, -6) + "ms");
		
		
		//linear
		System.out.println("linear --------------------------------------------------");
		start = (double)System.nanoTime();
		for(int i = 0; i < wanted.length-1; i++) {
			int tmp = IMDBSearch.linearSearch(directors, wanted[i]);
			if(tmp != -1) {
			System.out.println(directors[tmp].getName()+ " : " + directors[tmp].getDirectedMovies());
			}
		}
		stop = (double)System.nanoTime();
		System.out.println((stop-start)*Math.pow(10, -6) + "ms");
		
		IMDBSearch.movieQuantity(directors, wanted);
		}
		
		
}
