


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.io.*;

public abstract class test{

  private static String url = "/wiki/St_Helen%27s_Church,_Ashby-de-la-Zouch";
  private static File testFile = new File("coms311/project2/source.html");

  public static void main(String[] args){
    for(String i: args){
      switch(i){
        case "e":
          extractTest();
          break;
        case "cr":
          crawlTest();
          break;
        case "b":
          bfsTest();
          break;
        case "d":
          dTest();
          break;
        case "ce":
          cTest();
          break;
      }
    }
  }

  public static void extractTest(){
    WikiCrawler c = new WikiCrawler(url, 60, null, "coms311/project2/graph.txt");

    String source = WikiCrawler.readUrl(url);
    try{
      testFile.createNewFile();
      PrintWriter out = new PrintWriter(testFile);
      out.print(source);
      out.close();
    } catch(Exception e){
      System.out.println(e.getMessage());
    }

    ArrayList<String> links = c.extractLinks(source);

    System.out.println(Arrays.toString(links.toArray()));
  }

  public static void crawlTest(){
    WikiCrawler c = new WikiCrawler("/wiki/Computer_Science", 200, null, "coms311/project2/graph.txt");
    long start = System.currentTimeMillis();
    c.crawl();
    System.out.println(System.currentTimeMillis() - start);
  }

  public static void bfsTest(){
    GraphProcessor p = new GraphProcessor("coms311/project2/graph.txt");
    System.out.println("finished construction");
    Set<String> keys = p.graph.getKeys();
    ArrayList<String> l;

    for(String i: keys){
      for(String j: keys){
        l = p.bfsPath(i, j);
        System.out.println(Arrays.toString(l.toArray()));
      }
    }
  }

  public static void dTest(){
    GraphProcessor p = new GraphProcessor("coms311/project2/graph.txt");
    System.out.println(p.diameter());
  }

  public static void cTest(){
    GraphProcessor p = new GraphProcessor("coms311/project2/graph.txt");
    Set<String> keys = p.graph.getKeys();

    for(String i: keys){
      System.out.println(i + ": " + p.centrality(i));
    }
  }
}
