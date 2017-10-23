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
		for(int i = 0; i < s.length; i++){
      this.add(s[i]);
    }
	}

	public int distinctSize()
	{
    if(this.root == null){
		  return 0;
    } else{
      return this.root.uniqueChildren + 1;
    }
	}

	public int size()
	{
    if(this.root == null){
      return 0;
    } else{
		  return this.root.children + this.root.count;
    }
	}

	public int height()
	{
    if(this.root == null){
      return 0;
    } else{
		  return this.root.height;
    }
	}

	public void add(String s)
	{
    if(this.root == null){
		  this.root = new Node(s);
    } else{
      this.root.insert(s);
    }
	}

	public boolean search(String s)
	{
    if(this.root == null){
      return false;
    } else{
		  return this.root.search(s) != null;
    }
	}

	public int frequency(String s)
	{
    if(this.root == null){
      return 0;
    } else{
    	Node ans = this.root.search(s);
      if(ans == null){
        return 0;
      } else{
        return ans.count;
      }
    }
	}

	public boolean remove(String s)
	{
    if(this.root == null){
      return false;
    } else{
      return this.root.remove(s)[0];
    }
	}

	public String[] inOrder()
	{
		int unique = 0;
    String[] ans;
    if(this.root == null){
      return new String[unique];
    }
    unique = this.root.uniqueChildren+1;
    ans = new String[unique];
    inOrder(root, ans, 0);
    return ans;
	}

  /**
   * recursive function that fills the array with the strings in the tree
   * in order
   * @param  Node     n             the current Node
   * @param  String[] ans           the array of Strings
   * @param  int      i             the current vacant index
   * @return          the next vacant index after this funtion completes
   */
  private int inOrder(Node n, String[] ans, int i){
    if(n == null){
      return i;
    }
    i = inOrder(n.lChild, ans, i);
    ans[i] = n.print();
    return inOrder(n.rChild, ans, i+1);
  }

	public String[] preOrder()
	{
		int unique = 0;
    String[] ans;
    if(this.root == null){
      return new String[unique];
    }
    unique = this.root.uniqueChildren+1;
    ans = new String[unique];
    preOrder(root, ans, 0);
    return ans;
	}

  /**
   * recursive function used to print the tree pre order.
   * @param  Node     n             the current node
   * @param  String[] ans           the array of strings
   * @param  int      i             the current location that needs to be filled
   * @return          the next free index after this function returns.
   */
  private static int preOrder(Node n, String[] ans, int i){
    if(n == null){
      return i;
    }
    ans[i] = n.print();
    i = preOrder(n.lChild, ans, i+1);
    return preOrder(n.rChild, ans, i);
  }

	public int rankOf(String s)
	{
		return rankOf(this.root, s, 0);
	}

  private static int rankOf(Node n, String s, int rank){
    if(n == null){
      return rank;
    }
    int compare = n.compareTo(s);
    if(compare > 0){
      rank = rankOf(n.lChild, s, rank);
    } else if(compare < 0){
      if(n.lChild != null){
        rank += n.lChild.children + n.lChild.count;
      }
      rank = rankOf(n.rChild, s, rank + n.count);
    } else if(n.lChild != null){
      rank += n.lChild.children + n.lChild.count;
    }
    return rank;
  }

  /**
   * this is the class that will be used for the Nodes in the tree, the methods in the Node class will handle most
   * of the logic for the BST operations.
   */
  private class Node implements Comparable<String>{

    //This String this Node represents
    public String value;

    //pointer to this Node's left child
    public Node lChild = null;

    //pointer to this Node's right child
    public Node rChild = null;

    //pointer to this nodes parent
    public Node parent = null;

    //height of this Node in the tree, leafs are 1 the parent is = 1 + the height if its highest child
    public int height = 1;

    //number of occurences of this string
    public int count = 1;

    //number of Strings inserted below this Node
    public int children = 0;

    //number of Nodes inserted below this Node
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
          this.lChild = new Node(s, this);
          this.increment(true);
        } else{
          result = this.lChild.insert(s);
          this.increment(result);
        }
      } else if(compare < 0){
        if(this.rChild == null){
          result = true;
          this.rChild = new Node(s, this);
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
        if(ans[0]){
          this.decrement(ans[1]);
        }
      } else if(compare > 0){
        if(this.lChild == null){
          ans[0] = false;
        } else{
          ans = this.lChild.remove(s);
        }
        if(ans[0]){
          this.decrement(ans[1]);
        }
      } else{
        ans[0] = true;
        if(this.count > 1){
          count--;
          ans[1] = false;
        } else{
          ans[1] = true;
          Node replacement = this.findReplacement(true);
          if(replacement == null){
            if(this.parent.lChild == this){
              this.parent.lChild = null;
            } else{
              this.parent.rChild = null;
            }
          } else{
            this.value = replacement.value;
            this.count = replacement.count;
          }
        }
      }
      return ans;
    }

    /**
     * returns a detached node that can be inserted into the place of the node
     * this method was called on. This method also takes care of decrementing the children and unique children fields
     * as well as recalculating the heights of the nodes it passes over while finding the replacement.
     * @param  boolean                 first true if this is the base element that the method was called on
     * @return Node                    A Node that is a child of the root node that has been detached from the graph. null if there are no children.
     * @author Collin Vincent collinvincent96@gmail.com
     * @date   2017-10-22T00:50:41+000
     */
    private Node findReplacement(boolean first){
      Node ans;
      if(first){
        if(this.lChild == null && this.rChild == null){
          return null;
        }
        if(this.lChild == null){
          ans = this.rChild;
          this.lChild = ans.lChild;
          this.rChild = ans.rChild;
          if(ans.lChild != null){
            ans.lChild.parent = this;
          }
          if(ans.rChild != null){
            ans.rChild.parent = this;
          }
        } else if(this.rChild == null){
          ans = this.lChild;
          this.lChild = ans.lChild;
          this.rChild = ans.rChild;
          if(ans.lChild != null){
            ans.lChild.parent = this;
          }
          if(ans.rChild != null){
            ans.rChild.parent = this;
          }
        } else{
          ans = this.rChild.findReplacement(false);
        }
        this.calcHeight();
        this.children -= ans.count;
        this.uniqueChildren -= 1;
      } else{
        if(this.lChild == null){
          if(this.value == "lat"){
            System.out.println(this.parent);
            System.out.println(this.rChild);
            this.parent.rChild = null;
          }

          if(this.parent != null){
            if(this.parent.lChild == this){
              this.parent.lChild = this.rChild;
            } else{
              this.parent.rChild = this.rChild;
            }
          }
          if(this.rChild != null){
            this.rChild.parent = this.parent;
          }
          this.parent = null;
          this.rChild = null;
          ans = this;
        } else{
          ans = this.lChild.findReplacement(false);
          this.calcHeight();
          this.children -= ans.count;
          this.uniqueChildren -= 1;
        }
      }

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
        this.rChild = n;
        n.parent = this;
      } else {
        this.rChild.appendRight(n);
      }
      this.children += n.children + n.count;
      this.uniqueChildren += n.uniqueChildren + 1;
      this.calcHeight();
    }

    /**
     * appends node n to the left most child of this node and then recalculates the height up to
     * the node that this method was called on.
     * @param  Node                    n node to be appended to the first empty right child spot
     * @author Collin Vincent collinvincent96@gmail.com
     * @date   2017-10-22T16:47:40+000
     */
    private void appendLeft(Node n){
      if(this.lChild == null){
        this.lChild = n;
        n.parent = this;
      } else {
        this.lChild.appendRight(n);
      }
      this.children += n.children + n.count;
      this.uniqueChildren += n.uniqueChildren + 1;
      this.calcHeight();
    }

    /**
     * adds a child to this Node
     * @param  boolean                 unique if true then the uniqueChild counter will also be incremented and the height will be recalculated
     * @author Collin Vincent collinvincent96@gmail.com
     * @date   2017-10-22T19:55:27+000
     */
    private void increment(boolean unique){
      this.children++;
      if(unique){
        this.uniqueChildren++;
        this.calcHeight();
      }
    }

    private void decrement(boolean unique){
      this.children--;
      if(unique){
        this.uniqueChildren--;
        this.calcHeight();
      }
    }

    public String print(){
      return this.value;
    }

    private void calcHeight(){
      int lHeight = this.lChild == null ? 0 : this.lChild.height;
      int rHeight = this.rChild == null ? 0 : this.rChild.height;
      this.height = lHeight < rHeight ? rHeight + 1 : lHeight + 1;
    }

    @Override
    public int compareTo(String other){
      return this.value.compareTo(other);
    }

    public int compareTo(Node other){
      return this.value.compareTo(other.value);
    }
  }
}
