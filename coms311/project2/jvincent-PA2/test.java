


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
        case "r":
          reportStats();
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
    ArrayList<String> topics = new ArrayList<String>();

    //topics.add("Computer");
    //topics.add("digital");

    WikiCrawler c = new WikiCrawler("/wiki/Computer_Science", 200, topics, "coms311/project2/WikiCS.txt");
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
    GraphProcessor p = new GraphProcessor("coms311/project2/WikiCS.txt");
    System.out.println(p.diameter());
  }

  public static void cTest(){
    GraphProcessor p = new GraphProcessor("coms311/project2/WikiCS.txt");
    Set<String> keys = p.graph.getKeys();

    for(String i: keys){
      System.out.println(i + ": " + p.centrality(i));
    }
  }

  public static void reportStats(){
    GraphProcessor p = new GraphProcessor("coms311/project2/WikiCS.txt");
    Set<String> keys = p.graph.getKeys();
    ArrayList<String> list = new ArrayList<String>();
    int num =0;

    for(String i: keys){
      int temp = p.outDegree(i);
      if(temp > num){
        list.clear();
        list.add(i);
        num = temp;
      } else if(temp == num){
        list.add(i);
      }
    }
    System.out.println("The largest out degree is: " + num + ", and the nodes with this degree are: ");
    System.out.println(Arrays.toString(list.toArray()));
    System.out.println("---------------------------------------");
    System.out.println("The Diameter of the graph is: " + p.diameter());
    System.out.println("---------------------------------------");
    
    num = 0;
    list.clear();
    for(String i: keys){
      int temp = p.centrality(i);
      if(temp > num){
        list.clear();
        list.add(i);
        num = temp;
      } else if(temp == num){
        list.add(i);
      }
    }
    System.out.println("The largest centrality is: " + num + ", and the nodes with this centrality are: ");
    System.out.println(Arrays.toString(list.toArray()));
  }
}
