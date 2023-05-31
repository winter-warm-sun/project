import java.util.*;

public class TopologySort {
    public static List<Node> sortedTopology(Graph graph) {
        // key：某个节点   value：剩余的入度
        HashMap<Node,Integer> inMap=new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue=new LinkedList<>();
        for (Node node:graph.nodes.values()) {
            inMap.put(node, node.in);
            if(node.in==0) {
                zeroInQueue.offer(node);
            }
        }
        List<Node> result=new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur=zeroInQueue.poll();
            result.add(cur);
            for (Node next: cur.nexts) {
                inMap.put(next,inMap.get(next)-1);
                if(inMap.get(next)==0) {
                    zeroInQueue.offer(next);
                }
            }
        }
        return result;
    }
}
