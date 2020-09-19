import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SimpleIO
{
	public static byte[] readFile( String path )
	{
		try
		{
			return Files.readAllBytes( Paths.get(path) );
		}
		catch( IOException e )
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String[] readTextFile( String path )
	{
		try
		{
			List<String> lines = Files.readAllLines( Paths.get(path), Charset.defaultCharset() );
			
			return lines.toArray( new String[lines.size()] );
		}
		catch( IOException e )
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static boolean writeFile( byte[] data, String path )
	{
		try
		{
			Files.write( Paths.get(path), data );
			return true;
		}
		catch( IOException e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean writeTextFile( String[] lines, String path )
	{
		try
		{
			Files.write( Paths.get(path), Arrays.asList(lines), Charset.defaultCharset() );
			return true;
		}
		catch( IOException e )
		{
			e.printStackTrace();
			return false;
		}
	}
}
