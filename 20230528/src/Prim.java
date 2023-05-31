import java.util.*;

public class Prim {
    public static Set<Edge> prim(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Edge> priorityQueue=new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight- o2.weight;
            }
        });
        // 解锁的点进入set集合
        HashSet<Node> nodeSet=new HashSet<>();

        Set<Edge> result=new HashSet<>();
        for (Node node:graph.nodes.values()) { // 随便挑了一个点
            if(!nodeSet.contains(node)) {
                nodeSet.add(node);
                for(Edge edge:node.edges) { // 由一个点解锁所有相连边
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()) {
                    Edge edge=priorityQueue.poll();// 弹出解锁的边中，最小的边
                    Node toNode=edge.to;
                    if(!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        result.add(edge);
                        for(Edge nextEdge: toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
            // break
        }
        return result;
    }
}
