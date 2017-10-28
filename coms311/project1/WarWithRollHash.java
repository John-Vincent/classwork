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

	public WarWithRollHash(String[] s, int k)
	{
		this.ans = new ArrayList<String>();
    this.S = new HashSet<RollingString>();
    this.size = k;
    for(int i = 0; i < s.length; i++){
      RollingString c = new RollingString(s[i]);
      //System.out.println(c + ": " + c.hashCode());
      S.add(c);
      for(int j = 0; j < s.length; j++){
        if(j != i){
          ans.add(s[i] + s[j]);
        }
      }
    }
	}

	public ArrayList<String> compute2k()
	{
		Iterator<String> it = ans.iterator();
    RollingString cur;

    while(it.hasNext()){
      cur = new RollingString(it.next());
      for(int i = this.size + 1; i <= cur.length(); i++){
        cur.incrementHash();
        //System.out.println(cur.core + ": " + cur + ": " + cur.hashCode());
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

    private char[] values;

    private int hash;

    public RollingString(String s){
      this.core = s;
      this.index = 0;
      this.values = s.toCharArray();
      for(int i = 0; i < WarWithRollHash.this.size; i++){
       this.hash += java.lang.Math.pow(2, i)*(int)this.values[i];
      }
    }

    public void incrementHash(){
      if(this.index + WarWithRollHash.this.size > this.values.length){
        return;
      }
      this.hash -= (int)this.values[index];
      this.hash = this.hash /2;
      this.hash += java.lang.Math.pow(2, WarWithRollHash.this.size-1) * (int)this.values[this.index + WarWithRollHash.this.size];
      this.index++;
    }

    public int hashCode(){
      return hash;
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
