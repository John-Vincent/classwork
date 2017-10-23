// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.Hashtable;

public class WarWithHash
{
	private Hashtable<String, String> S;
  private int size;
  private ArrayList<String> ans;

	public WarWithHash(String[] s, int k)
	{
		ans = new ArrayList<String>();
    this.S = new Hashtable<String, String>();
    for(int i = 0; i < s.length; i++){
      for(int j = 0; j < s.length; j++){
        if(j != i){
          ans.add(s[i] + s[j%s.length]);
        }
      }
    }
	}

	public ArrayList<String> compute2k()
	{
    return ans;
	}
}
