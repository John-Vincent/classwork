// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiCrawler
{
	static final String BASE_URL = "https://en.wikipedia.org";
  private final String seed;
  private final int max;
  private ArrayList<String> topics;
  private final String fileName;
  private Pattern paragraphs = Pattern.compile("<p.*?>(.*?)</p>");
  private Pattern links = Pattern.compile("<a.*? href=\""+BASE_URL+"(.+?)\"");

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
		// implementation
	}

	public void crawl()
	{
		// implementation
	}
}
