public class MaxSubBST {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Info {
        public int maxSubBSTSize;
        public int size;
        public int max;
        public int min;

        public Info(int maxSubBSTSize, int size, int max, int min) {
            this.maxSubBSTSize = maxSubBSTSize;
            this.size = size;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(Node x) {
        if(x==null) {
            return null;
        }
        Info leftInfo=process(x.left);
        Info rightInfo=process(x.right);
        int max=x.value;
        int min=x.value;
        int size=1;
        if(leftInfo!=null) {
            max=Math.max(max, leftInfo.max);
            min=Math.min(min, leftInfo.min);
            size+= leftInfo.size;
        }
        if(rightInfo!=null) {
            max=Math.max(max, rightInfo.max);
            min=Math.min(min, rightInfo.min);
            size+= rightInfo.size;
        }
        int p1=-1;
        if(leftInfo!=null) {
            p1= leftInfo.maxSubBSTSize;
        }
        int p2=-1;
        if(rightInfo!=null) {
            p2= rightInfo.maxSubBSTSize;
        }
        int p3=-1;
        boolean leftBST=leftInfo==null?true:(leftInfo.maxSubBSTSize== leftInfo.size);
        boolean rightBST=rightInfo==null?true:(rightInfo.maxSubBSTSize== rightInfo.size);
        if(leftBST&&rightBST) {
            boolean leftMaxLessX=leftInfo==null?true:(leftInfo.max<x.value);
            boolean rightMinMoreX=rightInfo==null?true:(rightInfo.min>x.value);
            if(leftMaxLessX&&rightMinMoreX) {
                p3=size;
            }
        }
        return new Info(Math.max(p1,Math.max(p2,p3)),size,max,min);
    }
}
