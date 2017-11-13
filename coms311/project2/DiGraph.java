

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiGraph{

  private HashMap<String,Node> nodes;

  public DiGraph(){
    this.nodes = new HashMap<String, Node>();
  }

  public DiGraph(File f){
    this.nodes = new HashMap<String, Node>();
    Pattern pat = Pattern.compile("^([^\\s]+) ([^\\s]+)$");
    Matcher mat;
    String line;
    try(BufferedReader in = new BufferedReader(new FileReader(f));){
      while((line = in.readLine()) != null){
        mat = pat.matcher(line);
        if(mat.matches()){
          this.addNode(mat.group(1));
          this.addNode(mat.group(2));
          this.addEdge(mat.group(1), mat.group(2));
        }
      }
    } catch(Exception e){
      System.out.println(e);
    }
  }

  /**
   * adds a node to the graph with the url s if the node doesn't already exist in the graph
   * @param  String s             the relative url for the Node
   * @return        true if a new node was made, false if the node already exist
   */
  public boolean addNode(String s){
    if(!this.nodes.containsKey(s)){
      this.nodes.put(s, new Node(s));
      return true;
    }
    return false;
  }

  /**
   * adds a directed edge from the node with the relative url from, to the node with the relative url to
   * @param  String from          the url for the Node that has the edge pointing out
   * @param  String to            the url for the Node that has the edge pointing in
   * @return        true if the edge was added false if one of the nodes doesn't exist in the graph.
   */
  public boolean addEdge(String from, String to){
    Node f = this.nodes.get(from);
    Node t = this.nodes.get(to);
    if(f == null || t == null){
      return false;
    }
    f.addChild(t);
    return true;
  }

  public boolean contains(String s){
    return nodes.containsKey(s);
  }

  public int size(){
    return nodes.size();
  }

  public ArrayList<String> outList(String v){
    Node n = nodes.get(v);
    if(n != null){
      return n.out;
    }
    return null;
  }

  public Set<String> getKeys(){
    return nodes.keySet();
  }

  /**
   * writes a representation of the current graph to a file
   * @param  File f             the file that the representation should be written to.
   * @return      true if the graph was written successfully, false if there was an error. the error message will be printed to the terminal.
   */
  public boolean writeToFile(File f, String root){
    try{
      f.createNewFile();
      PrintWriter out = new PrintWriter(f);
      ArrayDeque<Node> list = new ArrayDeque<Node>();
      Node cur;
      HashSet<String> visited = new HashSet<String>();

      list.add(nodes.get(root));
      out.println(nodes.size());
      visited.add(root);

      while(list.size() > 0){
        cur = list.pop();
        //System.out.println("printing node " + cur.url);
        for(String n: cur.out){
          if(!visited.contains(n)){
            visited.add(n);
            list.add(nodes.get(n));
          }
          out.println(cur.url + " " + n);
        }
      }
      out.close();
    } catch(Exception e){
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }

  private class Node{
    private final String url;
    private ArrayList<String> out;

    public Node(String url){
      this.url = url;
      this.out = new ArrayList<String>();
    }

    public void addChild(Node c){
      if(!this.out.contains(c))
        this.out.add(c.url);
    }
  }
}
