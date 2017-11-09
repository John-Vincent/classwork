


import java.util.ArrayList;
import java.util.Arrays;

public abstract class test{

  public static void main(String[] args){
    WikiCrawler c = new WikiCrawler();
    ArrayList<String> links = c.extractLinks(WikiCrawler.readUrl("/wiki/St_Helen%27s_Church,_Ashby-de-la-Zouch"));

    System.out.println(Arrays.toString(links.toArray()));
  }
}
