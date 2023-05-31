import java.util.ArrayList;

public class Node {
    public int value;
    public int in;   // 入度：表示有多少个节点指向该节点
    public int out;  // 出度：表示该节点指向了多少个节点
    public ArrayList<Node> nexts;  // 存储该节点直接指向节点的集合
    public ArrayList<Edge> edges;  // 存储该节点直接访问的边的集合

    public Node(int value) {
        this.value=value;
        in=0;
        out=0;
        nexts=new ArrayList<>();
        edges=new ArrayList<>();
    }
}
