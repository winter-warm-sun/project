public class Hanoi {
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    // 请把1~N层圆盘   从左->右
    public static void leftToRight(int n) {
        if(n==1) {  // base case
            System.out.println("Move 1 from left to right");
            return;
        }
        leftToMid(n-1);
        System.out.println("Move "+n+" from left to right");
        midToRight(n-1);
    }

    public static void leftToMid(int n) {
        if(n==1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n-1);
        System.out.println("Move "+n+" from left to mid");
        rightToMid(n-1);
    }

    public static void rightToMid(int n) {
        if(n==1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n-1);
        System.out.println("Move "+n+" from right to mid");
        leftToMid(n-1);
    }

    public static void rightToLeft(int n) {
        if(n==1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        rightToMid(n-1);
        System.out.println("Move "+n+" from right to left");
        midToLeft(n-1);
    }

    public static void midToLeft(int n) {
        if(n==1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        midToRight(n-1);
        System.out.println("Move "+n+" from mid to left");
        rightToLeft(n-1);
    }

    public static void midToRight(int n) {
        if(n==1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n-1);
        System.out.println("Move "+n+" from mid to right");
        leftToRight(n-1);
    }

    public static void hanoi2(int n) {
        if(n>0) {
            func(n,"left","right","mid");
        }
    }

    public static void func(int n,String from,String to,String other) {
        if(n==1) {
            System.out.println("Move 1 from "+from+" to "+to);
        }else {
            func(n-1,from,other,to);
            System.out.println("Move "+n+" from "+from+" to "+to);
            func(n-1,other,to,from);
        }
    }

    public static void main(String[] args) {
        int n=3;
        hanoi1(3);
        System.out.println();
        hanoi2(3);

    }
}
