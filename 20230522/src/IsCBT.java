public class IsCBT {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    public static Info process(TreeNode x) {
        if(x==null) {
            return new Info(true,true,0);
        }
        Info leftInfo=process(x.left);
        Info rightInfo=process(x.right);
        int height=Math.max(leftInfo.height,rightInfo.height)+1;
        boolean isFull= leftInfo.isFull&& rightInfo.isFull&&leftInfo.height== rightInfo.height;
        boolean isCBT=false;
        if(leftInfo.isFull&& rightInfo.isFull&&leftInfo.height== rightInfo.height) {
            isCBT=true;
        }else if(leftInfo.isCBT&& rightInfo.isFull&&leftInfo.height== rightInfo.height+1) {
            isCBT=true;
        }else if (leftInfo.isFull&& rightInfo.isFull&&leftInfo.height== rightInfo.height+1) {
            isCBT=true;
        }else if(leftInfo.isFull&&rightInfo.isCBT&&leftInfo.height== rightInfo.height) {
            isCBT=true;
        }
        return new Info(isFull,isCBT,height);
    }
}
