public class IsBST2 {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Info {
        public boolean isFull;
        public boolean isBST;
        public int height;

        public Info(boolean isFull, boolean isBST, int height) {
            this.isFull = isFull;
            this.isBST = isBST;
            this.height = height;
        }
    }

    public static Info process(Node x) {
        if(x==null) {
            return new Info(true,true,0);
        }
        Info leftInfo=process(x.left);
        Info rightInfo=process(x.right);
        int height=Math.max(leftInfo.height, rightInfo.height)+1;
        boolean isFull= leftInfo.isFull&& rightInfo.isFull&&leftInfo.height== rightInfo.height;
        boolean isBST=false;
        if(leftInfo.isFull&&rightInfo.isFull&&leftInfo.height== rightInfo.height) {
            isBST=true;
        }else if(leftInfo.isBST&& rightInfo.isFull&&leftInfo.height== rightInfo.height+1) {
            isBST=true;
        }else if(leftInfo.isFull&& rightInfo.isFull&&leftInfo.height== rightInfo.height) {
            isBST=true;
        }else if(leftInfo.isFull&& rightInfo.isBST&&leftInfo.height== rightInfo.height) {
            isBST=true;
        }
        return new Info(isFull,isBST,height);
    }
}
