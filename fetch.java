import java.io.*;
import java.net.*;
import java.util.*;

public class fetch
{
    public static String fetchPage(String name)
    {
	System.out.println("[FETCH] " + name);
	StringBuffer res = new StringBuffer();		
	try
	    {
		String prefix = "http://";
		URL url = new URL(prefix + name);

		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String text;
		while((text=reader.readLine())!=null)
		    {
			//			res.append(text);
		    }	
		reader.close();

	    }
	catch(Exception e)
	    {
		res.append("Error fetching page: " + name);
	    }
	return res.toString();
    }

    public static String resolveName(String name)
    {
	System.out.println("[RESOLVE] " + name);
	try {
	    InetAddress address = InetAddress.getByName(name);	    
	    return address.getHostAddress();
	}
	catch(Exception e)
	    {
		return null;
	    }
    }

    public static void main(String args[]) throws Exception
    {

	LinkedList<String> urls = new LinkedList<String>();
	

	BufferedReader reader = new BufferedReader(new FileReader("load.dat"));
	String name;	
	while((name=reader.readLine())!=null)
	    {
		urls.add(name);
		resolveName(name);
	    }	
	reader.close();

	PrintWriter writer = new PrintWriter(new FileWriter("stats.dat"));

	LinkedList queue;

	double totalTime = 0;

	int iter = 50;

	for(int i=1;i<=iter;i++)
	    {
		System.out.println("ITERATION " + i);
		
		queue = (LinkedList) urls.clone();
		double timeTaken = 0;
		
		while(!queue.isEmpty())
		    {
			long start = System.currentTimeMillis();	       
			fetchPage((String) queue.poll());
			long end = System.currentTimeMillis();
			timeTaken += (end - start);
		    }				
		
		writer.println((timeTaken / 1000));
		totalTime += timeTaken;		
	    }	
	
	System.out.println("Average time taken: " + (totalTime / (iter * 1000)) + " s");

	writer.close();

    }
}