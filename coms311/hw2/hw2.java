


public class hw2{



  public static void main(String[] args){
    long nineA = 314606891L;
    long nineB = 817504243L;
    long tenA = 5915587277L;
    long tenB = 5463458053L;
    long end = 0;
    long start = System.currentTimeMillis();
    long ans = 0;

    ans = GCD(nineA, nineB);
    end = System.currentTimeMillis();
    System.out.println("9-digit GCD run time: " + (end-start) + " value: " + ans);

    start = System.currentTimeMillis();
    ans = FastGCD(nineA, nineB);
    end = System.currentTimeMillis();
    System.out.println("9-digit fastGCD run time: " + (end-start) + " value: " + ans);

    start = System.currentTimeMillis();
    ans = GCD(tenA, tenB);
    end = System.currentTimeMillis();
    System.out.println("10-digit GCD run time: " + (end-start) + " value: " + ans);

    start = System.currentTimeMillis();
    ans = FastGCD(tenA, tenB);
    end = System.currentTimeMillis();
    System.out.println("10-digit fastGCD run time: " + (end-start) + " value: " + ans);
  }

  public static long GCD(long a, long b){
    for(long i = a < b ? a : b; i >=1; i--){
      if(a%i == 0 && b%i == 0){
        return i;
      }
    }
    return 1;
  }

  public static long FastGCD(long a, long b){
    if(b == 0){
      return a;
    } else{
      return FastGCD(b, a%b);
    }
  }


}
