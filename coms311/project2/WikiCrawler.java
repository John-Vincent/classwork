// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashSet;
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
  private Pattern paragraphs = Pattern.compile("<p(.*)");
  private Pattern links = Pattern.compile("<a[^>]*? href=\"(/wiki/[^#:]+?)\"");
  private DiGraph graph;
  private HashSet<String> invalid;

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName)
	{
		this.seed = seedUrl;
    this.max = max;
    this.topics = topics;
    this.fileName = fileName;
    this.graph = new DiGraph();
    this.invalid = new HashSet<String>();
	}

	// NOTE: extractLinks takes the source HTML code, NOT a URL
	public ArrayList<String> extractLinks(String doc)
	{
    ArrayList<String> ans = new ArrayList<String>();
		Matcher par = paragraphs.matcher(doc);
    Matcher url;
    while(par.find()){
      url = links.matcher(par.group());
      while(url.find()){
        String link = url.group(1);
        if(link != null && !ans.contains(link)){
          ans.add(link);
        }
      }
    }
    return ans;
	}

	public void crawl()
	{
		String current_source;
    ArrayDeque<String[]> urls = new ArrayDeque<String[]>();
    int count = 0;
    String[] curPath;
    String doc;
    ArrayList<String> new_urls;
    urls.add(new String[]{seed, ""});
    while(urls.size() > 0){
      if(count > 48){
        count = 0;
        try{
          Thread.sleep(3000);
        }catch(Exception e){
          System.out.println(e);
        }
      }
      curPath = urls.pop();
      //System.out.println("curPath: " + curPath[0] + " curParent:" + curPath[1] + this.graph.size());
      if(this.graph.contains(curPath[0])){
        this.graph.addEdge(curPath[1], curPath[0]);
      }else if(!this.invalid.contains(curPath[0]) && this.graph.size() < this.max){
        doc = readUrl(curPath[0]);
        count++;
        if(this.valid(doc)){
          this.graph.addNode(curPath[0]);
          //System.out.println("adding node " + curPath[0]);
          if(!curPath[1].equals("")){
            this.graph.addEdge(curPath[1], curPath[0]);
            //System.out.println("adding edge " + curPath[1] + " " + curPath[0]);
          }
          new_urls = extractLinks(doc);
          for(String u : new_urls){
            if(!u.equals(curPath[0]) && !this.invalid.contains(u)){
              urls.add(new String[]{u, curPath[0]});
            }
          }
        } else{
          this.invalid.add(curPath[0]);
        }
      }
    }
    this.graph.writeToFile(new File(this.fileName), this.seed);
	}

  private boolean valid(String doc){
    if(this.topics != null){
      Matcher par = paragraphs.matcher(doc);
      par.find();
      String at = par.group();
      for(String t: this.topics){
        if(!at.contains(t)){
          return false;
        }
      }
    }
    return true;
  }

  public static String readUrl(String path){
    String ans = null;
    try{
      URL url = new URL(BASE_URL + path);
      URLConnection connect = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line = in.readLine();

      while(line != null){
        response.append(line);
        line = in.readLine();
      }

      ans = response.toString();
      in.close();
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
    return ans;
  }
}
