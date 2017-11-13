// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashMap;
import java.io.File;


public class GraphProcessor
{
	protected DiGraph graph;

	// NOTE: graphData should be an absolute file path
	public GraphProcessor(String graphData)
	{
		this.graph = new DiGraph(new File(graphData));
	}

	public int outDegree(String v)
	{
    return this.graph.outList(v).size();
	}

	public ArrayList<String> bfsPath(String u, String v)
	{
    ArrayList<String> e;
    if(u.equals(v)){
      e = new ArrayList<String>();
      e.add(u);
      return e;
    }
    HashMap<String, String> visited = new HashMap<String,String>();
    String cur;
    ArrayDeque<String> list = new ArrayDeque<String>();

    boolean found = false;
    list.add(u);
    visited.put(u, null);

    while(list.size()>0 && !found){
      cur = list.pop();
      e = graph.outList(cur);
      for(String i: e){
        if(!visited.containsKey(i)){
          visited.put(i, cur);
          list.add(i);
          if(i.equals(v)){
            found = true;
            break;
          }
        }
      }
    }

    e = new ArrayList<String>();
    cur = v;
    while(cur != null){
      if(visited.containsKey(cur)){
        e.add(0, cur);
      }
      cur = visited.get(cur);
    }
    return e;
	}

	public int diameter()
	{
		Set<String> nodes = graph.getKeys();
    ArrayList<String> list;
    int d = 0;

    for(String i: nodes){
      for(String j: nodes){
        list = bfsPath(i,j);
        if(d < list.size()-1){
          d = list.size()-1;
        }
      }
    }
    return d;
	}

	public int centrality(String v)
	{
		Set<String> nodes = graph.getKeys();
    ArrayList<String> list1;
    ArrayList<String> list2;
    ArrayList<String> list3;
    int d = 0;

    for(String i: nodes){
      for(String j: nodes){
        list1 = bfsPath(i,j);
        list2 = bfsPath(i, v);
        list3 = bfsPath(v, j);
        if(list1.size()-1 == list2.size() + list3.size() -2 && list2.size() >0 && list3.size() > 0){
          d++;
        }
      }
    }
    return d;
	}

}
