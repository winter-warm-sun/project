import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TopologicalOrderDFS1 {
    // 不提交该类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int label) {
            this.label = label;
            neighbors=new ArrayList<>();
        }
    }

    // 提交以下的

    /**
     * 该类用于记录每个节点可以访问的节点次，越大拓扑序越靠前
     */
    public static class Record {
        public DirectedGraphNode node;
        public long nodes;

        public Record(DirectedGraphNode node, long nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode,Record> order=new HashMap<>();
        for(DirectedGraphNode cur:graph) {
            f(cur,order);
        }
        ArrayList<Record> records=new ArrayList<>();
        for(Record r:order.values()) {
            records.add(r);
        }
        records.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
            }
        });
        ArrayList<DirectedGraphNode> result=new ArrayList<>();
        for (Record r:records) {
            result.add(r.node);
        }
        return result;
    }

    // 返回cur节点可到的所有节点次
    public static Record f(DirectedGraphNode cur,HashMap<DirectedGraphNode,Record> order) {
        if(order.containsKey(cur)) {
            return order.get(cur);
        }
        long nodes=0;
        for(DirectedGraphNode next: cur.neighbors) {
            nodes+=f(next,order).nodes;
        }
        Record ans=new Record(cur,nodes+1);
        order.put(cur,ans);
        return ans;
    }
}
