public class MaxSubBSTHead {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Info {
        public Node maxSubBSTHead;
        public int maxSubBSTSize;
        public int max;
        public int min;

        public Info(Node maxSubBSTHead, int maxSubBSTSize, int max, int min) {
            this.maxSubBSTHead = maxSubBSTHead;
            this.maxSubBSTSize = maxSubBSTSize;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(Node x) {
        if(x==null) return null;
        Info leftInfo=process(x.left);
        Info rightInfo=process(x.right);
        int max=x.value;
        int min=x.value;
        Node maxSubBSTHead=null;
        int maxSubBSTSize=0;
        if(leftInfo!=null) {
            max=Math.max(max, leftInfo.max);
            min=Math.min(min, leftInfo.min);
            maxSubBSTHead=leftInfo.maxSubBSTHead;
            maxSubBSTSize= leftInfo.maxSubBSTSize;
        }
        if(rightInfo!=null) {
            max=Math.max(max, rightInfo.max);
            min=Math.min(min, rightInfo.min);
            if(rightInfo.maxSubBSTSize>maxSubBSTSize) {
                maxSubBSTHead=rightInfo.maxSubBSTHead;
                maxSubBSTSize= rightInfo.maxSubBSTSize;
            }
        }
        if((leftInfo==null?true:(leftInfo.maxSubBSTHead ==x.left&&leftInfo.max<x.value))&&
                (rightInfo==null?true:(rightInfo.maxSubBSTHead==x.right&&rightInfo.min>x.value))) {
            maxSubBSTHead=x;
            maxSubBSTSize=(leftInfo==null?0: leftInfo.maxSubBSTSize)+(rightInfo==null?0: rightInfo.maxSubBSTSize)+1;
        }
        return new Info(maxSubBSTHead,maxSubBSTSize,max,min);
    }
}
