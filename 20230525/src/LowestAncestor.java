public class LowestAncestor {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Info {
        public boolean findA;
        public boolean findB;
        public Node ans;

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    public static Info process(Node x,Node a,Node b) {
        if(x==null) {
            return null;
        }
        Info leftInfo=process(x.left,a,b);
        Info rightInfo=process(x.right,a,b);
        boolean findA=(x==a)|| leftInfo.findA|| rightInfo.findA;
        boolean findB=(x==b)|| leftInfo.findB|| rightInfo.findB;
        Node ans=null;
        if(leftInfo!=null) {
            ans=leftInfo.ans;
        }else if(rightInfo!=null) {
            ans=rightInfo.ans;
        }else {
            if(findA&&findB) {
                ans=x;
            }
        }
        return new Info(findA,findB,ans);
    }
}
