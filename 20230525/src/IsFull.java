public class IsFull {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Info {
        public int height;
        public int nodes;

        public Info(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    public static boolean isFull(Node head) {
        if(head==null) {
            return true;
        }
        Info info=process(head);
        return (1<< info.height)-1== info.nodes;
    }

    public static Info process(Node x) {
        if(x==null) {
            return new Info(0,0);
        }
        Info leftInfo=process(x.left);
        Info rightInfo=process(x.right);
        int height=Math.max(leftInfo.height, rightInfo.height)+1;
        int nodes= leftInfo.nodes+ rightInfo.nodes+1;
        return new Info(height,nodes);
    }
}
