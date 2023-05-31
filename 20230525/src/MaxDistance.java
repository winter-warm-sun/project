public class MaxDistance {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static Info process(Node x) {
        if (x==null) {
            return new Info(0,0);
        }
        Info leftInfo=process(x.left);
        Info rightInfo=process(x.right);
        int height=Math.max(leftInfo.height, rightInfo.height)+1;
        int p1= leftInfo.maxDistance;
        int p2= rightInfo.maxDistance;
        int p3= leftInfo.height+ rightInfo.height+1;
        int maxDistance=Math.max(p1,Math.max(p2,p3));
        return new Info(maxDistance,height);
    }
}
