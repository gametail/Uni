public class HSV
{
	public static void main (String args [])
	{	
		//quadratisches canvas mit size 500
		int canvas_size = 500;
		Draw.init(canvas_size,canvas_size);
		
		//aeussere for schleife geht alle x von 0 bis 500 durch
        for(int x=0;x<canvas_size;x++)
		{
			//innere schleife geht alle y von 0 bis 500 durch
            for(int y=0; y<canvas_size;y++) 
			{
				//bestimmt die helligkeit in abhaengigkeit vom canvas
				float value = (float)y/canvas_size;
				//bestimmt den farbwert in abhaengigkeit vom canvas
				float hue = (float)x/canvas_size*360;
				//rechnet die rgb werte aus und speichert sie in einem eindimensionalen array mit der laenge 3
				int[]rgb = Draw.hsvToRgb(hue,1f,1-value);
				
				//setzt die farbe mithilfe des erstellten arrays
				Draw.setColor(rgb[0],rgb[1],rgb[2]);
				//setzt den pixel und faengt bei erneut bei der inneren schleife an bis alles y werte gesetzt sind und geht dann wieder zur x schleife
				Draw.setPixel(x ,y);
			}
		}
		
	
	}
}
