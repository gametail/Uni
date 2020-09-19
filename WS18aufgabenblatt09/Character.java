package WS18aufgabenblatt09;
/*
Informatik 1 Winter 05.02.2018
Aufgabenblatt:    	09
Aufgabe:          	09.01
*/

import java.awt.event.KeyEvent;

public class Character {
	
	public int head = 100;
	public int head_posx = 0;
	public int hair_w = 100;
	public int hair_h = 20;
	public int hair_posx = 0;
	public int eye = 20;
	public int eye_posx = 70; 
	public int body_w = 100;
	public int body_h = 200;
	public int body_posx = 0;
	public int hair_posy = 600 - head - hair_h - body_h - 20;
	public int head_posy = 600 - body_h - head - 20;
	public int eye_posy = 600 - body_h - head;
	public int body_posy = 600 - body_h;
	
	
	public Character(int head, int hair_w, int hair_h, int eye, int body_w, int body_h) {
		this.head = head;
		this.hair_w = hair_w;
		this.hair_h = hair_h;
		this.eye = eye;
		this.body_w = body_w;
		this.body_h = body_h;		
	}
	
	public Character() {
	}
	
	public void moveLeft(int keypress) {
		if((keypress == KeyEvent.VK_LEFT) && (this.eye_posx == this.head_posx + 70) ) {
			this.eye_posx = this.head_posx + 10;
		}
		if((keypress == KeyEvent.VK_LEFT) && (body_posx - 20 >= 0)) {
			this.head_posx -= 20;
			this.hair_posx -= 20;
			this.eye_posx  -= 20;
			this.body_posx -= 20;
		}	
		
	}
	public void moveRight(int keypress) {
		if((keypress == KeyEvent.VK_RIGHT) && (this.eye_posx == this.head_posx + 10) ) {
			this.eye_posx = this.head_posx + 70;
		}
		if((keypress == KeyEvent.VK_RIGHT) && (body_posx + 20 <= 700)) {
			this.head_posx += 20;
			this.hair_posx += 20;
			this.eye_posx += 20;
			this.body_posx += 20;

		}	
		
	}
	//ducken ist irgendwie sehr schnell deswegen habe ich die fps auf 10 gesetzt damit man nicht direkt wieder audsteht /passiert auch wenn man gedrückt hält
	public void duck(int keypress) {
		if((keypress == KeyEvent.VK_CONTROL) && (body_posy == 500)) {
			this.hair_posy = 260;
			this.head_posy = 280;
			this.eye_posy = 300;
			this.body_posy = 400;
		}	
		else if((keypress == KeyEvent.VK_CONTROL) && (body_posy == 400)) {
			this.hair_posy = 430;
			this.head_posy = 450;
			this.eye_posy = 470;
			this.body_posy = 500;
		}	
	}
}