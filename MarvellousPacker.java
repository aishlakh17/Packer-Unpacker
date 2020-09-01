
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MarvellousPacker
{
	FileOutputStream outstream = null;
	
	String ValidExt[]={".c",".txt",".cpp",".java"};
	
	public MarvellousPacker(String src,String Dest) throws Exception
	{
		String Magic ="Marvellous11";
		byte arr[] = Magic.getBytes();
		File outfile = new File(Dest);
		
		File infile = null;
		outstream = new FileOutputStream(Dest);
		outstream.write(arr,0,arr.length);

		File folder = new File(src);
		
		System.setProperty("user.dir",src);
		
		listAllFiles(src);
		System.out.println(src);
	}
	
	public void listAllFiles(String path)
	{
		System.out.println("Hello");
		System.out.println(path);//Demo
		try(Stream<Path>paths = Files.walk(Paths.get(path)))
		{
			paths.forEach(filePath ->
			{
				if(Files.isRegularFile(filePath))
				{
					
					try
					{
						
						String name = filePath.getFileName().toString();
						System.out.println(name);
						String ext = name.substring(name.lastIndexOf("."));
						
						List<String> list = Arrays.asList(ValidExt);
						if(list.contains(ext))
						{
							File file = new File(filePath.toString());
							
							//System.out.println("Absolute path is :"+file.getAbsolutePath());
							Pack(file.getAbsolutePath());
								
								// Path p =Paths.get("");
								// String s =p.toAbsolutePath().toString();
								// String str = s+"\\"+path+"\\"+name;
								// Pack(str);
						}
					}
					
					catch(Exception e)
					{
						System.out.println("listAllFiles Exception" );
						System.out.println(e);
					}
				}
			});
			
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	public void Pack(String filePath)
	{
		System.out.println("Different file path ");
		System.out.println(filePath);
		FileInputStream instream = null;
		
		try
		{
			byte[] buffer = new byte[1024];
			
			int length;
			
			byte temp[]=new byte[100];
			
			File fobj = new File(filePath);

			String 	Header = filePath+" "+fobj.length();
			
			for(int i=Header.length();i<100;i++)
			Header +=" ";
				
				temp = Header.getBytes();
				
				instream = new FileInputStream(filePath);
				
				outstream.write(temp,0,temp.length);
				
				while((length = instream.read(buffer)) > 0)
				{
					outstream.write(buffer,0,length);
				}
				instream.close();
		}
		catch(Exception e)
		{
			System.out.println("Pack Exception" );
			System.out.println(e);
		}
	}
}