import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TopologicalOrderBFS {
    // 不提交该类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int label) {
            this.label = label;
            neighbors=new ArrayList<>();
        }
    }

    // 提交以下代码
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode,Integer> indegreeMap=new HashMap<>();
        for (DirectedGraphNode cur:graph) {
            indegreeMap.put(cur,0);
        }
        for (DirectedGraphNode cur:graph) {
            for (DirectedGraphNode next: cur.neighbors) {
                indegreeMap.put(next,indegreeMap.get(next)+1);
            }
        }
        Queue<DirectedGraphNode> zeroQueue=new LinkedList<>();
        for (DirectedGraphNode cur:indegreeMap.keySet()) {
            if(indegreeMap.get(cur)==0) {
                zeroQueue.offer(cur);
            }
        }
        ArrayList<DirectedGraphNode> result=new ArrayList<>();
        while (!zeroQueue.isEmpty()) {
            DirectedGraphNode cur=zeroQueue.poll();
            result.add(cur);
            for(DirectedGraphNode next: cur.neighbors) {
                indegreeMap.put(next,indegreeMap.get(next)-1);
                if(indegreeMap.get(next)==0) {
                    zeroQueue.offer(next);
                }
            }
        }
        return result;
    }
}
