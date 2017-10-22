
import java.util.Arrays;

public abstract class test{

  static String[] testSet = new String[] {
    "matt",
    "bat",
    "fat",
    "cat",
    "lat",
    "hat",
    "nat",
    "spat",
    "tat",
    "sat",
    "pat",
    "plat",
    "rat",
    "matt"
  };

  static BinaryST testTree = new BinaryST(testSet);

  static String spacing = "";

  public static void main(String[] args){
    for(int i = 0; i < 30; i++){
      spacing += '-';
    }
    printTree();
    testTree.remove(testSet[0]);
    printTree();
    testTree.remove(testSet[0]);
    printTree();
    testTree.remove(testSet[2]);
    printTree();
  }

  public static boolean testConstructors(){
    BinaryST tree = new BinaryST();
    BinaryST tree2 = new BinaryST(testSet);
    for(int i = 0; i < testSet.length; i++){
      tree.add(testSet[i]);
    }
    return Arrays.equals(tree.inOrder(), tree2.inOrder()) && Arrays.equals(tree.preOrder(), tree2.preOrder());
  }

  public static int testSize(){
    return testTree.size();
  }

  public static void printTree(){
    System.out.println(spacing);
    System.out.println("inOrder: " + Arrays.toString(testTree.inOrder()));
    System.out.println("preOrder: " + Arrays.toString(testTree.preOrder()));
    System.out.println("size of tree: " + testSize());
    System.out.println("distinct size of tree: " + testTree.distinctSize());
    System.out.println("height of tree: " + testTree.height());
    System.out.println(spacing);
  }

}
