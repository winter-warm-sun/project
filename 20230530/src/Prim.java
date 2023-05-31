import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {
    public static Set<Edge> prim(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Edge> priorityQueue=new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight-o2.weight;
            }
        });
        // 解锁的点进入set集合
        HashSet<Node> nodeSet=new HashSet<>();

        Set<Edge> result=new HashSet<>();
        for(Node node:graph.nodes.values()) {
            if(!nodeSet.contains(node)) {
                nodeSet.add(node);
                for(Edge edge: node.edges) {
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()) {
                    Edge edge=priorityQueue.poll();
                    Node toNode=edge.to;
                    if(!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        result.add(edge);
                        for(Edge nextEdge:toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
        }
        return result;
    }
}
