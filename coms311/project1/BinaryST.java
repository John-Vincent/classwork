// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)


public class BinaryST
{
	// member fields and methods
	private Node root;

	public BinaryST()
	{
		this.root = null;
	}

	public BinaryST(String[] s)
	{
    this.root = null;
		for(int i = 0; i < s.length; s++){
      this.add(s[i]);
    }
	}

	public int distinctSize()
	{
		return this.root.uniqueChildren + 1;
	}

	public int size()
	{
		return this.root.children + this.root.count;
	}

	public int height()
	{
		return this.root.height
	}

	public void add(String s)
	{
		this.root.insert(s);
	}

	public boolean search(String s)
	{
		return this.root.search(s) != null;
	}

	public int frequency(String s)
	{
		Node ans = this.root.search(s);
    if(ans == null){
      return 0;
    } else{
      return ans.count;
    }
	}

	public boolean remove(String s)
	{
    return this.root.remove(s)[0];
	}

	public String[] inOrder()
	{
		// implementation
	}

	public String[] preOrder()
	{
		// implementation
	}

	public int rankOf(String s)
	{
		// implementation
	}

  private class Node implements comparable{

    public final String value;

    public Node lChild = null;

    public Node rChild = null;

    public Node Parent = null;

    public int height = 1;

    public int count = 1;

    public int children = 0;

    public int uniqueChildren = 0;

    public Node(String value){
      this.value = value;
    }

    public Node(String value, Node parent){
      this.value = value;
      this.parent = parent;
    }

    public boolean insert(String s){
      int compare = this.compareTo(s);
      boolean result;

      if(compare > 0){
        if(this.lChild == null){
          result = true;
          this.lChild = new Node(s);
          this.increment(true);
        } else{
          result = this.lChild.insert(s);
          this.increment(result);
        }
      } else if(compare < 0){
        if(this.rChild == null){
          result = true;
          this.rChild = new Node(s);
          this.increment(true);
        } else{
          result = this.rChild.insert(s);
          this.increment(result);
        }
      } else {
        result = false;
        count++;
      }

      return result;
    }

    public Node search(String s){
      int compare = this.compareTo(s);

      if(compare > 0){
        if(this.lChild == null){
          return null;
        }else{
          return this.lChild.search(s);
        }
      } else if(compare < 0){
        if(this.rChild == null){
          return null;
        }else{
          return this.rChild.search(s);
        }
      } else{
        return this;
      }
    }

    public boolean[] remove(String s){
      boolean[] ans = new boolean[2];
      int compare = this.compareTo(s);

      if(compare < 0){
        if(this.rChild == null){
          ans[0] = false;
        } else{
          ans = this.rChild.remove(s);
        }
      } else if(compare > 0){
        if(this.lChild == null){
          ans[0] = false;
        } else{
          ans = this.lChild.remove(s);
        }
      } else{
        ans[0] = true;
        if(this.count > 1){
          count--;
          ans[1] = false;
        } else{
          ans[1] = true;
          Node replacement = this.findReplacement(true);
          replacement
        }
      }

      return ans;
    }

    /**
     * returns a detached node that can be inserted into the place of the node
     * this method was called on. This method also takes care of decrementing the children and unique children fields of the
     * nodes it passes over while finding the replacement.
     * @param  boolean                 first true if this is the base element that the method was called on
     * @return Node                    A Node that is a child of the root node that has been detached from the graph. null if there are no children.
     * @author Collin Vincent collinvincent96@gmail.com
     * @date   2017-10-22T00:50:41+000
     */
    private Node findReplacement(boolean first){
      Node ans;
      this.uniqueChildren--;
      this.children--;
      if(first){
        if(this.lChild == null){
          ans = this.rChild;
          this.rChild = null;
        } else if(this.rChild == null){
          ans = this.lChild
          this.lChild = null;
        } else{
          ans = this.rChild.findReplacement(false);
        }
      } else{
        if(this.lChild == null){
          this.parent.lChild = null;
          ans = this;
        } else{
          ans = this.lChild.findReplacement(false);
        }
      }
      this.calcHeight();
      return ans;
    }

    /**
     * appends node n to the right most child of this node and then recalculates the height up to
     * the node that this method was called on.
     * @param  Node                    n node to be appended to the first empty right child spot
     * @author Collin Vincent collinvincent96@gmail.com
     * @date   2017-10-22T16:47:40+000
     */
    private void appendRight(Node n){
      if(this.rChild == null){
        this.rChild = n
      } else {
        this.rChild.appendRight(Node);
      }
      this.calcHeight();
    }

    private void increment(boolean unique){
      this.children++
      if(unique){
        this.uniqueChildren++
        this.calcHeight();
      }
    }

    private void calcHeight(){
      int lHeight = this.lChild == null ? 0 : this.lChild.height;
      int rHeight = this.rChild == null ? 0 : this.rChild.height;
      this.height = lHeight < rHeight ? rHeight + 1 : lHeight + 1;
    }

    public int compareTo(String other){
      return this.value.compareTo(other);
    }

    public int compareTo(Node other){
      return this.value.compareTo(other.value);
    }
  }
}
