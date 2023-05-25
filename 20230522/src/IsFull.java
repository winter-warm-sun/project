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
        Info all=process(head);
        return (1<< all.height)-1== all.nodes;
    }

    public static Info process(Node head) {
        if(head==null) {
            return new Info(0,0);
        }
        Info leftInfo=process(head.left);
        Info rightInfo=process(head.right);
        int height=Math.max(leftInfo.height, rightInfo.height)+1;
        int nodes= leftInfo.nodes+ rightInfo.nodes;
        return new Info(height,nodes);
    }
}
