// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

public class WarWithBST
{
	private BinaryST S;
  private int size;
  private ArrayList<String> ans;

	public WarWithBST(String[] s, int k)
	{
		this.ans = new ArrayList<String>();
    this.S = new BinaryST();
    this.size = k;
    for(int i = 0; i < s.length; i++){
      this.S.add(s[i]);
      for(int j = 0; j < s.length; j++){
        if(j != i){
          this.ans.add(s[i] + s[j]);
        }
      }
    }
	}

	public ArrayList<String> compute2k()
	{
    Iterator<String> it = ans.iterator();
    String cur;
    String sub;

    while(it.hasNext()){
      cur = it.next();
      for(int i = this.size + 1; i < cur.length(); i++){
        sub = cur.substring(i-this.size, i);
        if(!S.search(sub)){
          it.remove();
          break;
        }
      }
    }
    return ans;
	}
}
