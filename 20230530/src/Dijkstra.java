import java.util.HashMap;

public class Dijkstra {
    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private Node[] nodes;// 实际的堆结构
        // key:某个node   value:上面堆中的位置
        private HashMap<Node,Integer> heapIndexMap;// 反向索引表
        // key:某个节点    value:从源节点触发到该节点的目前最小距离
        private HashMap<Node,Integer> distanceMap;
        private int size;// 堆上有多少个点

        public NodeHeap(int size) {
            nodes=new Node[size];
            heapIndexMap=new HashMap<>();
            distanceMap=new HashMap<>();
            size=0;
        }

        public boolean isEmpty() {
            return size==0;
        }

        public void addOrUpdateOrIgnore(Node node,int distance) {
            if(inHeap(node)) {   // update
                distanceMap.put(node,Math.min(distanceMap.get(node),distance));
                heapInsert(heapIndexMap.get(node));
            }
            if(!isEntered(node)) {   //  add
                nodes[size]=node;
                heapIndexMap.put(node,size);
                distanceMap.put(node,distance);
                heapInsert(size++);
            }
            //  ignore
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord=new NodeRecord(nodes[0],distanceMap.get(nodes[0]));
            swap(0,size-1);
            heapIndexMap.put(nodes[size-1],-1);
            distanceMap.remove(nodes[size-1]);
            nodes[size-1]=null;
            shiftDown(0,--size);
            return nodeRecord;
        }

        private void heapInsert(int child) {
            int parent=(child-1)/2;
            while (child>0) {
                if(distanceMap.get(child)<distanceMap.get(parent)) {
                    swap(child,parent);
                    child=parent;
                    parent=(child-1)/2;
                }else {
                    break;
                }
            }
        }

        private void shiftDown(int parent,int size) {
            int child=parent*2+1;
            while (child<size) {
                if(child+1<size&&distanceMap.get(child)>distanceMap.get(child+1)) {
                    child++;
                }
                if(distanceMap.get(child)<distanceMap.get(parent)) {
                    swap(child,parent);
                    parent=child;
                    child=2*parent+1;
                }else {
                    break;
                }
            }
        }

        private void swap(int index1,int index2) {
            heapIndexMap.put(nodes[index1],index2);
            heapIndexMap.put(nodes[index2],index1);
            Node tmp=nodes[index1];
            nodes[index1]=nodes[index2];
            nodes[index2]=tmp;
        }

        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node)&&heapIndexMap.get(node)!=-1;
        }
    }

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个的路径记录并返回
    public static HashMap<Node,Integer> dijkstra(Node head,int size) {
        NodeHeap nodeHeap=new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head,0);
        HashMap<Node,Integer> result=new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record=nodeHeap.pop();
            Node cur=record.node;
            int distance=record.distance;
            for(Edge edge: cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to,edge.weight+distance);
            }
            result.put(cur,distance);
        }
        return result;
    }
}
