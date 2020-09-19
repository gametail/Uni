package WS18aufgabenblatt09;
/*
Informatik 1 Winter 05.02.2018
Aufgabenblatt:    	09
Aufgabe:          	09.01
*/

public class Game {

	public static void main(String[] args) {
		//Draw.setBackgroundColor(166, 166, 166);
		int width = 800;
		int height = 600;
		int lastPressedKey;
		//head color Draw.setColor(255, 211, 166);
		//hair color Draw.setColor(78, 40, 3);
		//eye color Draw.setColor(255, 255, 255);		
		//body color Draw.setColor(0, 0, 0);

	
		Draw.init(width, height);
		Draw.enableDoubleBuffering(true);
		Draw.setFps(10);
		Draw.setBackgroundColor(128, 128, 128);
		Character player = new Character();
		while(true)
		{
			//draws hair
			Draw.setColor(120, 80, 30);
			Draw.filledRect(player.hair_posx, player.hair_posy, player.hair_w, player.hair_h);
			//draws head
			Draw.setColor(255, 211, 166);
			Draw.filledRect(player.head_posx, player.head_posy, player.head, player.head);
			//draw eye
			Draw.setColor(255, 255, 255);
			Draw.filledRect(player.eye_posx, player.eye_posy, player.eye, player.eye);
			//draws body
			Draw.setColor(0, 0, 0);
			Draw.filledRect(player.body_posx, player.body_posy , player.body_w, player.body_h);
			lastPressedKey = Draw.getLastPressedKey();
			player.moveLeft(lastPressedKey);
			player.moveRight(lastPressedKey);
			player.duck(lastPressedKey);
			Draw.syncToFrameRate();
			Draw.clearScreen();
		}		
	}

}
