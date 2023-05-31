import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TopologicalOrderDFS2 {
    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 提交下面的
    public static class Record {
        public DirectedGraphNode node;
        public int deep;

        public Record(DirectedGraphNode node, int deep) {
            this.node = node;
            this.deep = deep;
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
                return o2.deep- o1.deep;
            }
        });
        ArrayList<DirectedGraphNode> result=new ArrayList<>();
        for(Record r:records) {
            result.add(r.node);
        }
        return result;
    }

    public static Record f(DirectedGraphNode cur,HashMap<DirectedGraphNode,Record> order) {
        if(order.containsKey(cur)) {
            return order.get(cur);
        }
        int follow=0;
        for(DirectedGraphNode next: cur.neighbors) {
            follow=Math.max(follow,f(next,order).deep);
        }
        Record ans=new Record(cur,follow+1);
        order.put(cur,ans);
        return ans;
    }
}
