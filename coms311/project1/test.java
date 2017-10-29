
import java.util.Arrays;
import java.util.ArrayList;

public abstract class test{

  static String[] testSet = new String[] {
    "matt",     //0
    "bat",      //1
    "fat",      //2
    "cat",      //3
    "lat",      //4
    "hat",      //5
    "nat",      //6
    "spat",     //7
    "tat",      //8
    "sat",      //9
    "pat",      //10
    "plat",     //11
    "rat",      //12
    "begining", //13
    "bananas",  //14
    "lirik",    //15
    "cap",      //16
    "wap",      //17
    "rolf",     //18
    "trystan"   //19
  };

  static String testString = "Stranger Things Season 2 is probably better than stranger things season 1 alksdhjf aklsdjhf laksdhf lkasdfh laksdhjf laksdjhf fhq3i9o47hf02794bf80qervg0pef9vub8o06234gfv134780b9f80qsdgvpasdubvf801347bvf08qebvoasuixcd 3408bf 0378rbvf0 sdgcvb139p4u bf 08dbcvpqeibrv128o34bvgf 80sdcbv314bfp 897gfih1pio3b rp29b f-90qerbv13p4iubrpaxdgvlkjbp98g0a8s7dg134iv5po789sg d80fg 304gr 802we7gf0apsd8bv13il4bro yusdfc97iS6DVFCV321KUHV4RO876ASVDOVB2534HJLBVT OQ8DFG0Q3R8BF IBF O8G7 7g0823gb4oibv87";

  static BinaryST testTree = new BinaryST();

  static String spacing = "";

  static int size = 50;

  public static void main(String[] args){
    //testAddRemove();
    //testFrequency();
    //testRankOf();

    double time1 = 0;
    double time2 = 0;

    boolean printArrays = false;

    ArrayList<String> testSet2L = new ArrayList<String>();
    for(int i = size; i <= testString.length(); i++){
      testSet2L.add(testString.substring(i-size, i));
    }

    String[] testSet2 = new String[testSet2L.size()];
    testSet2 = testSet2L.toArray(testSet2);

    //System.out.println(Arrays.toString(testSet2));

    WarWithArray wwa = new WarWithArray(testSet2, size);
    WarWithBST wwb = new WarWithBST(testSet2, size);
    WarWithHash wwh = new WarWithHash(testSet2, size);
    WarWithRollHash wwr = new WarWithRollHash(testSet2, size);
    ArrayList<String> ans;

    time1 = System.currentTimeMillis();
    ans = wwa.compute2k();
    time2 = System.currentTimeMillis();
    System.out.println("array:");
    if(printArrays)
      System.out.println(Arrays.toString(ans.toArray()));
    System.out.println(time2 - time1);
    time1 = System.currentTimeMillis();
    ans = wwb.compute2k();
    time2 = System.currentTimeMillis();
    System.out.println("BST:");
    if(printArrays)
      System.out.println(Arrays.toString(ans.toArray()));
    System.out.println(time2 - time1);
    time1 = System.currentTimeMillis();
    ans = wwh.compute2k();
    time2 = System.currentTimeMillis();
    System.out.println("hash:");
    if(printArrays)
      System.out.println(Arrays.toString(ans.toArray()));
    System.out.println(time2 - time1);
    time1 = System.currentTimeMillis();
    ans = wwr.compute2k();
    time2 = System.currentTimeMillis();
    System.out.println("RollHash: " + (ans.size() != 0));
    if(printArrays)
      System.out.println(Arrays.toString(ans.toArray()));
    System.out.println(time2 - time1);
  }

  public static boolean testConstructors(){
    BinaryST tree = new BinaryST();
    BinaryST tree2 = new BinaryST(testSet);
    for(int i = 0; i < testSet.length; i++){
      tree.add(testSet[i]);
    }
    return Arrays.equals(tree.inOrder(), tree2.inOrder()) && Arrays.equals(tree.preOrder(), tree2.preOrder());
  }

  public static void testAddRemove(){
    ArrayList<String> additions = new ArrayList<String>();
    int index;
    spacing = "";

    for(int i = 0; i < 30; i++){
      spacing += '-';
      index = (int)(Math.random() * 20);
      additions.add(testSet[index]);
      testTree.add(testSet[index]);
    }
    System.out.println(spacing);
    System.out.println("Strings entered: " + Arrays.toString(additions.toArray()));
    printTree();
    for(int i = 0; i < 10; i++){
      index = (int)(Math.random() * additions.size());
      System.out.println("removing: " + additions.get(index));
      testTree.remove(additions.remove(index));
      index = (int)(Math.random() * additions.size());
      System.out.println("removing: " + additions.get(index));
      testTree.remove(additions.remove(index));
      index = (int)(Math.random() * additions.size());
      System.out.println("removing: " + additions.get(index));
      testTree.remove(additions.remove(index));
      printTree();
    }
  }

  public static void testFrequency(){
    ArrayList<String> additions = new ArrayList<String>();
    int index;
    String[] list = new String[30];
    spacing = "";

    for(int i = 0; i < 30; i++){
      spacing += '-';
      index = (int)(Math.random() * 20);
      if(!additions.contains(testSet[index])){
        additions.add(testSet[index]);
      }
      list[i] = testSet[index];
      testTree.add(testSet[index]);
    }
    Arrays.sort(list);
    System.out.println(spacing);
    System.out.println("Strings entered: " + Arrays.toString(list));
    printTree();
    while(additions.size() > 0){
      System.out.println(additions.get(0) + " occurs " + testTree.frequency(additions.remove(0)) + " times");
    }
  }

  public static void testRankOf(){
    ArrayList<String> additions = new ArrayList<String>();
    int index;
    String[] list = new String[30];
    spacing = "";

    for(int i = 0; i < 30; i++){
      spacing += '-';
      index = (int)(Math.random() * 20);
      if(!additions.contains(testSet[index])){
        additions.add(testSet[index]);
      }
      list[i] = testSet[index];
      testTree.add(testSet[index]);
    }
    Arrays.sort(list);
    System.out.println(spacing);
    System.out.println("Strings entered: " + Arrays.toString(list));
    printTree();
    System.out.println("Rank of a: " + testTree.rankOf("a"));
    System.out.println("Rank of z: " + testTree.rankOf("z"));
    System.out.println("Rank of fg:" + testTree.rankOf("fg"));
    System.out.println("Rank of dd:" + testTree.rankOf("dd"));
    System.out.println("Rank of qr:" + testTree.rankOf("qr"));
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
