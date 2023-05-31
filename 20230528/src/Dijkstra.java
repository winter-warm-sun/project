import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Dijkstra {
    public static HashMap<Node,Integer> dijkstra(Node from) {
        HashMap<Node,Integer> distanceMap=new HashMap<>();// 距离表
        distanceMap.put(from,0);
        // 被选择过的点
        HashSet<Node> selectedNodes=new HashSet<>();
        Node minNode=getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);
        while (minNode!=null) {
            // 原始点 -> minNode（跳转点）  最小距离 distance
            int distance=distanceMap.get(minNode);
            for(Edge edge: minNode.edges) {
                Node toNode=edge.to;
                if(!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode,distance+edge.weight);
                }else {
                    distanceMap.put(edge.to,Math.min(distanceMap.get(toNode),distance+edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode=getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);
        }
        return distanceMap;
    }

    private static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode=null;
        int minDistance=Integer.MAX_VALUE;
        for(Map.Entry<Node,Integer> entry:distanceMap.entrySet()) {
            Node node=entry.getKey();
            int distance= entry.getValue();
            if(!selectedNodes.contains(node)&&distance<minDistance) {
                minNode=node;
                minDistance=distance;
            }
        }
        return minNode;
    }
}
