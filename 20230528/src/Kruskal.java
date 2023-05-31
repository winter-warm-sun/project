import java.util.*;

public class Kruskal {
    public static class UnionFind {
        // key：某个节点  value：key节点的代表节点
        private HashMap<Node,Node> representMap;
        // key：某个集合的代表节点  value：key所在集合的节点个数
        private HashMap<Node,Integer> sizeMap;

        public UnionFind(Graph graph) {
            representMap=new HashMap<>();
            sizeMap=new HashMap<>();
            for (Node node:graph.nodes.values()) {
                representMap.put(node,node);
                sizeMap.put(node,1);
            }
        }

        private Node findRepresent(Node x) {
            Stack<Node> path=new Stack<>();
            while (x!=representMap.get(x)) {
                path.add(x);
                x=representMap.get(x);
            }
            while (!path.isEmpty()) {
                representMap.put(path.pop(),x);
            }
            return x;
        }

        public boolean isSameSet(Node a,Node b) {
            return findRepresent(a)==findRepresent(b);
        }

        public void union(Node a,Node b) {
            if(a==null||b==null) {
                return;
            }
            Node fa=findRepresent(a);
            Node fb=findRepresent(b);
            if(fa!=fb) {
                int aSetSize=sizeMap.get(fa);
                int bSetSize=sizeMap.get(fb);
                if(aSetSize>=bSetSize) {
                    representMap.put(fb,fa);
                    sizeMap.put(fa,aSetSize+bSetSize);
                    sizeMap.remove(fb);
                }else {
                    representMap.put(fa,fb);
                    sizeMap.put(fb,aSetSize+bSetSize);
                    sizeMap.remove(fa);
                }
            }
        }
    }

    public static Set<Edge> kruskal(Graph graph) {
        UnionFind unionFind=new UnionFind(graph);
        // 默认小根堆
        PriorityQueue<Edge> priorityQueue=new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight-o2.weight;
            }
        });
        for (Edge edge:graph.edges) {
            priorityQueue.add(edge);
        }
        Set<Edge> result=new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            Edge edge=priorityQueue.poll();
            if(!unionFind.isSameSet(edge.from,edge.to)) {
                result.add(edge);
                unionFind.union(edge.from,edge.to);
            }
        }
        return result;
    }
}
