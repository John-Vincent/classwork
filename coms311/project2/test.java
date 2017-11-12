


import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public abstract class test{

  private static String url = "/wiki/St_Helen%27s_Church,_Ashby-de-la-Zouch";
  private static File testFile = new File("source.html");

  public static void main(String[] args){
    WikiCrawler c = new WikiCrawler(url, 10, null, "");

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
}
