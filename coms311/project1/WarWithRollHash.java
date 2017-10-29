// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class WarWithRollHash
{
	private HashSet<RollingString> S;
  private int size;
  private ArrayList<String> ans;
  private long pow = 1;

	public WarWithRollHash(String[] s, int k)
	{
		this.ans = new ArrayList<String>();
    this.S = new HashSet<RollingString>();
    this.size = k;
    for(int i = 0; i < s.length; i++){
      RollingString c = new RollingString(s[i]);
      //System.out.println(c + ": " + c.hash[c.index]);
      S.add(c);
      for(int j = 0; j < s.length; j++){
        if(j != i){
          ans.add(s[i] + s[j]);
        }
      }
    }
    for(int i = 0; i < k-1; i++){
      this.pow = (this.pow * RollingString.BASE) % RollingString.MOD;
    }
    //System.out.println(this.pow);
	}

	public ArrayList<String> compute2k()
	{
		Iterator<String> it = ans.iterator();
    RollingString cur;

    while(it.hasNext()){
      cur = new RollingString(it.next());
      for(int i = this.size + 1; i <= cur.length(); i++){
        cur.index++;
        //System.out.println(cur.core + ": " + cur + ": " + cur.hash[cur.index]);
        if(!S.contains(cur)){
          it.remove();
          break;
        }
      }
    }

    return ans;
	}


  private class RollingString{

    private String core;

    private int index;

    private long[] hash;

    private static final long BASE = 31;

    private static final long MOD = 1000000007;
                                  //307790412;
                                 //2147483647
                                  //976607406

    public RollingString(String s){
      boolean print = false;
      this.core = s;
      this.index = 0;
      char[] values = s.toCharArray();
      this.hash = new long[s.length()-WarWithRollHash.this.size+1];
      this.hash[0] = 0;
      for(int i = 0; i < WarWithRollHash.this.size; i++){
       this.hash[0] = (this.hash[0] * BASE + values[i]) % MOD;
      }
      for(int i = 0; i < this.hash.length - 1; i++){
        this.hash[i+1] = this.hash[i] - (WarWithRollHash.this.pow * values[i]) % MOD;
        if(this.hash[i+1] < 0)
          this.hash[i+1] += MOD;
        this.hash[i+1] = (this.hash[i+1] * BASE + values[i+ WarWithRollHash.this.size]) % MOD;
      }
    }

    public int hashCode(){
      return (int)hash[this.index];
    }

    public int length(){
      return this.core.length();
    }

    public String toString(){
      return this.core.substring(this.index, this.index + WarWithRollHash.this.size);
    }

    public boolean equals(RollingString s){
      return this.core.substring(this.index, this.index + WarWithRollHash.this.size).equals(s.core.substring(s.index, s.index + WarWithRollHash.this.size));
    }

    public boolean equals(Object o){
      RollingString s = (RollingString) o;
      return this.core.substring(this.index, this.index + WarWithRollHash.this.size).equals(s.core.substring(s.index, s.index + WarWithRollHash.this.size));
    }
  }
}
