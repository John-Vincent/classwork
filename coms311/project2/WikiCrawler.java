// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.net.*;

public class WikiCrawler
{
	static final String BASE_URL = "https://en.wikipedia.org";
  private final String seed;
  private final int max;
  private ArrayList<String> topics;
  private final String fileName;
  private Pattern paragraphs = Pattern.compile("<p.*?>(.*?)</p>");
  private Pattern links = Pattern.compile("<a.*? href=\"("+BASE_URL+")?(.+?)\"");

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName)
	{
		this.seed = seedUrl;
    this.max = max;
    this.topics = topics;
    this.fileName = fileName;
	}

	// NOTE: extractLinks takes the source HTML code, NOT a URL
	public ArrayList<String> extractLinks(String doc)
	{
    ArraList<String> ans = new ArrayList<String>();
		Matcher par = paragraphs.matcher(doc);
    Matcher url;

    while(par.find()){
      url = links.matcher(par.group());
      while(url.find()){
        if(url.group(1) != null){
          ans.add(url.group(1));
        }
      }
    }

    return ans;
	}

	public void crawl()
	{
		// implementation
	}

  public static String readUrl(String url){
    URL url = new URL(url);
    URLConnection connect = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    StringBuilder response = new StringBuilder();
    String line = in.readLine();

    while(line != null){
      response.append(line);
      line = in.readLine();
    }

    return response.toString();
  }
}
