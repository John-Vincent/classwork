

import java.util.ArrayList;
import java.io.File;

public class DiGraph{

  private HashMap<String,Node> nodes;

  public DiGraph(){
    this.nodes = new HashMap<String, Node>();
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

  /**
   * writes a representation of the current graph to a file
   * @param  File f             the file that the representation should be written to.
   * @return      true if the graph was written successfully, false if there was an error. the error message will be printed to the terminal.
   */
  public boolean writeToFile(File f){
    return false;
  }

  private class Node{
    private final String url;
    private ArrayList<Node> out;

    public Node(String url){
      this.url = url;
    }

    public void addChild(Node c){
      this.out.add(c);
    }
  }
}
