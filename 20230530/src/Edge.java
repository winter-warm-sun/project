public class Edge {
    public int weight;  // 边的权重（距离长度）
    public Node from;  // 边的起始点
    public Node to;    // 边的终止点

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
