package aufgabenblatt06;
import java.awt.Color;

import Utils.FloodFillAlgorithm;
import Utils.ImageViewer;
/*
Informatik 2 Sommer 2.06.2019
Aufgabenblatt:    	06
Aufgabe:          	06.01
*/

public class FloodFill extends FloodFillAlgorithm {
	private Color seedColor;
	private Color red = new Color(255,0,0);
	private int count;
	private Stack<Node> s = new Stack<Node>();
	private Queue<Node> q = new Queue<Node>();
	
	@Override
	public boolean checkCurrentNode(Node n, ImageViewer viewer, int threshold, Color seedColor) {
		//gucken ob node koordinaten im fenster liegen
		if(n.y >= 0 && n.y < viewer.getHeight() && n.x >= 0 && n.x < viewer.getWidth()) {
			//color auslesen und konvertieren in int, abstand ausrechnen
            int diff = viewer.getColor(n.x, n.y).getRGB() - seedColor.getRGB();
            //betrag nehmen
            if (diff < 0)
                diff *= -1;
            //und mit schwellenwert vergleichen
            return (diff < threshold);
        }
		//falls koordinaten nicht im fenster liegen 
        return false;
	}

	@Override
	public void depthFirst(ImageViewer viewer, int x, int y, int threshold) {
		
		int currentX = x;
        int currentY = y;

        do {
        	//seedColor speichern um weitere nodes zu checken
            if (count == 0)
                seedColor = viewer.getColor(currentX, currentY);
            
            //pixel einfaerben count um 1 erhoehen
            viewer.setColor(currentX, currentY, red, count);
            count++;
            
            //temp node erstellen mit aktuellen position
            Node tmp = new Node(currentX, currentY);
            //schleife+switch um alle 4 benachbarten pixel positionen zu berechnen
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        tmp = new Node(currentX, currentY + 1);
                        break;
                    case 1:
                        tmp = new Node(currentX, currentY - 1);
                        break;
                    case 2:
                        tmp = new Node(currentX + 1, currentY);
                        break;
                    case 3:
                        tmp = new Node(currentX - 1, currentY);
                        break;
                    default:
                        break;
                }
                //tmp node nach jedem case checken und wenn valide auf stack pushen
                if (checkCurrentNode(tmp, viewer, threshold, seedColor)) {
                    s.push(tmp);
                }
            }
            //stack pop fuer naechste position
            tmp = s.pop();
            viewer.sleep();
            currentX = tmp.x;
            currentY = tmp.y;
            //solange stack nicht leer ist alles wiederholen
        } while (!s.isEmpty());
	}
	
	/*
	 * irgendwie breitet sich das nicht genau aus wie auf dem bild im aufgabenblatt
	 * und es ist auch viel langsamer wie die andere methode ich denke der macht die 
	 * koordinaten mehr als einmal wenn man im programm auf die koordinaten guckt aendern
	 * die sich langsamer. Ich bin nicht draufgekommen wie ich das behebe
	 */
	@Override
	public void breadthFirst(ImageViewer viewer, int x, int y, int threshold) {
		int currentX = x;
        int currentY = y;

        do {
            if (count == 0) {
            	seedColor = viewer.getColor(currentX, currentY);
            }
            viewer.setColor(currentX, currentY, red, count);
            count++;
            
            //temp node erstellen mit aktuellen position
            Node tmp = new Node(currentX, currentY);
            //schleife+switch um alle 4 benachbarten pixel positionen zu berechnen
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        tmp = new Node(currentX + 1, currentY );
                        break;
                    case 1:
                        tmp = new Node(currentX, currentY + 1);
                        break;
                    case 2:
                        tmp = new Node(currentX - 1, currentY);
                        break;
                    case 3:
                        tmp = new Node(currentX, currentY-1);
                        break;
                    default:
                        break;
                }
                //tmp node nach jedem case checken und wenn valide in queue einreihen
                if (checkCurrentNode(tmp, viewer, threshold, seedColor)) {
                    q.enqueue(tmp);
                }
            }
            //dequeue fuer naechste position
            tmp = q.dequeue();
            viewer.sleep();
            currentX = tmp.x;
            currentY = tmp.y;
            //solange queue nicht leer ist alles wiederholen
        } while (!q.isEmpty());
	}

}
