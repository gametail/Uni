public class FunWithArrays
{
	public static void main (String args [])
	{	
		int n = 20;
		int startIndex = 5;
		int endIndex = 15;
		findMinimum(createRandomArray(n));
		findMaximumInRange(createRandomArray(n) ,startIndex ,endIndex);
	}
	
		public static int[] createRandomArray(int n)
		{
			//erstellt ein array mit der laenge n
			int [] array = new int [n];
			//schleife die ueber alle elemente von 0 bis laenge n-1 laeuft
			for (int i=0;i<n;i++)
			{
				//eine zufaellige zahl erstellen mal 100 nehmen, in ein int casten und in variable tmp speichern
				int tmp = (int)(Math.random() * 100);
				//Schleife die tmp solange neu zuweist bis tmp kleiner als n ist und dann das element an der gerade die obere schleife ist zuweist
				for(int j=0;tmp>n;j++)
				{	
					tmp = (int)(Math.random() * 100);
					array[i] = tmp;	
				}
			//elment an der stelle im jeweiligen schleifen durchgang wird ausgegeben und schleife beginnt von vorne	
			System.out.print(array[i] + ", ");				
			}	
			return array;
		}
		
		public static int findMinimum(int[] array)
		{
			//erstes element wird in variable min als minimum gesetzt
			int min = array[0];
			//mit for schleife wird jedes weitere element ueberprueft und wenn es kleiner ist als min wird es als neues min gesetzt und nach der schleife wird min auf der konsole ausgegeben
			for(int i=1;i<array.length;i++)
			{
				if(array[i] < min)
				{
					min = array[i];
				}
			}
			System.out.println("Das kleinste Element ist " + min);
			return min;
		}
		
		public static int findMaximumInRange(int[] array, int startIndex, int endIndex)
		{
			//ueberprueft ob index in range wenn nicht dann error
			if(startIndex >= 0 && startIndex < endIndex && endIndex < array.length)
			{
				//erstes element wird in variable max als maximum gesetzt
				int max = array[startIndex];
				//mit for schleife wird jedes weitere element ueberprueft und wenn es groesser ist als max wirrd es als neues max gesetzt und nach der schleife wird max auf der konsole ausgegeben
				for(int i=startIndex;i<=endIndex;i++)
				{
					if(array[i]>max)
					{
					max = array[i];
					}
				}
				System.out.println("Das Maximum des Teilarrays von " + startIndex + " bis " + endIndex + " ist " + max);
				return max;
			}
			else
			{
				System.out.println("Indices falsch ");
				return 0;
			}
			
		}

		
		
		
}
