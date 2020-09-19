package aufgabenblatt08;
/*
Informatik 1 Winter 22.01.2018
Aufgabenblatt:    	08
Aufgabe:          	08.01
*/
public class Robot 
{
	public static void main(String[] args)
	{
		//load and create level
		//String[] leveltmp = SimpleIO.readTextFile("src/level.txt");
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		String[] leveltmp = new String[] {
			"........................................",
			"........................................",
			"........................................",
			".......XXXXXXXXXXXX.....XXXXX...........",
			".......X....................X...........",
			".......X....................X...........",
			".......X....................X...........",
			"............................X...........",
			"............................X...........",
			".................X..........X...........",
			".................X..........X...........",
			".................X..........X...........",
			".................X..........X...........",
			".................X..........X...........",
			"........XXXXXXXXXXXXXXXXXXXXX...........",
			"........X...................X...........",
			"........X...................X...........",
			"..................XXX.......X...........",
			"..................X.........X...........",
			"..................XXX.......X...........",
			"............................X...........",
			"........X...................X...........",
			"........X...................X...........",
			"........XXXXXXX......XXXXXXXX...........",
			"........................................",
			"........................................"
			
		};
		int[][] level = createLevel(leveltmp);
		int rectSize = 20;
		int width = level[0].length*rectSize;
		int height = level.length*rectSize;
		Draw.init(width, height);
		Draw.enableDoubleBuffering(true);
		Draw.setFps(60);
		Draw.setBackgroundColor(128, 128, 128);
		int robot_x = 2;
		int robot_y = 2;
		int direction_x = 1;
		int direction_y = 1;
		int path = 0;
		
		while(true)
		{
			while(!(isPositionValid(level ,robot_x+direction_x ,robot_y+direction_y) &&
					isDirectionValid(direction_x,direction_y) && path < 5)) 
			{
				direction_x = randomVelocity();
				direction_y = randomVelocity();
				path = 0;
			}
			
			robot_x += direction_x;
			robot_y += direction_y;
			path++; 
			level[robot_y][robot_x]++;
			drawLevel(level,robot_x,robot_y);
			Draw.syncToFrameRate();
			Draw.clearScreen();
		}		
	}
	public static int [][] createLevel(String[] lines )
	{
		int[][] result = new int[lines.length][lines[0].length()];
		for(int i = 0;i<lines.length;i++)
		{
			for(int j = 0;j<lines[0].length();j++)
			{
				if(lines[i].charAt(j) == '.')
				{
					result [i][j] = 0;
				}
				else if(lines[i].charAt(j) == 'X')
				{
					result[i][j] = -1;
				}
				else
				{
					System.out.println("Error wrong character");
				}
			}
		}
		return result;
	}
	public static void drawLevel(int[][] level, int robot_x, int robot_y)
	{
		int width = 20;
		int height = 20;
		
		for(int x = 0;x<level.length;x++)
		{
			for(int y = 0;y<level[0].length;y++)
			{
				if(level[x][y] != 0)
				{
					if(level[x][y] < 0)
					{
					Draw.setColor(255, 255, 255);
					Draw.filledRect(y*20, x*20, width, height);
					}
					else if(level[x][y] > 0)
					{
					Draw.setColor(255, 0, 0);
					Draw.filledRect(y*20, x*20, width, height);
					}
				}
				Draw.setColor(0, 255, 0);
				Draw.filledRect(robot_x*20, robot_y*20, width, height);
			}
		}		
	}
	public static boolean isPositionValid(int[][] level, int x, int y)
	{
		return x>=0 && x<level[0].length && y>=0 && y<level.length && level[y][x]>=0;
	}
	public static int randomVelocity()
	{
		 return (int)Math.round((Math.random()-0.5)*3);
	}
	public static boolean isDirectionValid(int direction_x, int direction_y)
	{
		return direction_x != 0 || direction_y != 0;
	}
}
