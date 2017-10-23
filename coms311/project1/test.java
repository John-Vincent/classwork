
import java.util.Arrays;

public abstract class test{

  static String[] testSet = new String[] {
    "matt", //0
    "bat",  //1
    "fat",  //2
    "cat",  //3
    "lat",  //4
    "hat",  //5
    "nat",  //6
    "spat", //7
    "tat",  //8
    "sat",  //9
    "pat",  //10
    "plat", //11
    "rat",  //12
    "matt"  //13
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
    testTree.remove(testSet[13]);
    printTree();
    testTree.remove(testSet[2]);
    printTree();
    testTree.remove(testSet[10]);
    printTree();
    testTree.remove(testSet[9]);
    printTree();
    testTree.remove(testSet[7]);
    printTree();
    testTree.remove(testSet[1]);
    printTree();
    testTree.remove(testSet[5]);
    printTree();
    testTree.remove(testSet[8]);
    printTree();
    testTree.remove(testSet[4]);
    printTree();
    testTree.remove(testSet[11]);
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
    System.out.println("size of tree: " + testSize());
    System.out.println("distinct size of tree: " + testTree.distinctSize());
    System.out.println("height of tree: " + testTree.height());
    System.out.println("inOrder: " + Arrays.toString(testTree.inOrder()));
    System.out.println("preOrder: " + Arrays.toString(testTree.preOrder()));
    System.out.println(spacing);
  }

}
