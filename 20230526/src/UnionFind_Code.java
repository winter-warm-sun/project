import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class UnionFind_Code {
    public static class UnionFind<V> {
        public HashMap<V,V> represent;
        public HashMap<V,Integer> size;

        /*
        初始化时，每个节点就是单独一个集合，代表节点就是自己
         */
        public UnionFind(List<V> values) {
            represent=new HashMap<>();
            size=new HashMap<>();
            for(V cur:values) {
                represent.put(cur,cur);
                size.put(cur,1);
            }
        }
        /*
        给一个节点，往上到不能再往上，把代表返回（借助栈实现扁平优化，再查询时更快）
         */
        public V findRepresent(V cur) {
            Stack<V> path=new Stack<>();
            while (cur!=represent.get(cur)) {
                // 当cur不是代表节点时，记录路径并继续往上
                path.push(cur);
                cur=represent.get(cur);
            }
            while (!path.isEmpty()) {
                represent.put(path.pop(),cur);
            }
            return cur;
        }
        /*
        判断是否是同一个集合
         */
        public boolean isSameSet(V a,V b) {
            return represent.get(a)==represent.get(b);
        }
        /*
        合并集合
         */
        public void union(V a,V b) {
            V aRepresent=findRepresent(a);
            V bRepresent=findRepresent(b);
            if(aRepresent!=bRepresent) {
                int aSize=size.get(aRepresent);
                int bSize=size.get(bRepresent);
                if(aSize>=bSize) {
                    represent.put(bRepresent,aRepresent);
                    size.put(aRepresent,aSize+bSize);
                    size.remove(bRepresent);
                }else {
                    represent.put(aRepresent,bRepresent);
                    size.put(bRepresent,aSize+bSize);
                    size.remove(aRepresent);
                }
            }
        }

        public int sets() {
            return size.size();
        }
    }
}
