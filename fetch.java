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
			res.append(text);
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

	Queue<String> urls = new LinkedList<String>();

	BufferedReader reader = new BufferedReader(new FileReader("load.dat"));
	String name;	
	while((name=reader.readLine())!=null)
	    {
		urls.add(name);
	    }	
	reader.close();

	long timeTaken = 0;

	PrintWriter writer = new PrintWriter(new FileWriter("stats.dat"));
	writer.println("HOSTNAME\tIP\tTIME");
	String next = urls.peek();
	while(!urls.isEmpty())
	    {
		long start = System.currentTimeMillis();	       
		fetchPage(urls.poll());
		long end = System.currentTimeMillis();
		timeTaken += (end - start);
		resolveName(urls.peek());
	    }
	
	System.out.println(timeTaken);
	
	writer.close();
    }
}