public class Code_UnionFind {
    // 最大测试数据量
    public static int MAXN = 1000001;
    // 记录i节点的代表节点
    public static int[] represent=new int[MAXN];
    // 记录代表节点所在集合的元素个数
    public static int[] size=new int[MAXN];
    // 辅助数组用于实现扁平优化
    public static int[] help=new int[MAXN];

    /*
    初始化并查集
     */
    public static void init(int n) {
        for (int i = 0; i < n; i++) {
            represent[i]=i;
            size[i]=1;
        }
    }
    /*
    寻找集合代表点
     */
    public static int find(int i) {
        int hi=0;
        // 当i的代表节点不是i自己时，继续往上找代表节点，并记录路径所有节点
        while (i!=represent[i]) {
            help[hi++]=i;
            i=represent[i];
        }
        // 扁平优化，把路径上所有节点的代表节点都设为集合的代表节点
        while (hi-->=0) {
            represent[help[hi]]=i;
        }
        return i;
    }
    /*
    查询x和y是不是一个集合
     */
    public static boolean isSameSet(int x,int y) {
        return find(x)==find(y);
    }
    /*
    合并集合
     */
    public static void union(int x,int y) {
        int fx=find(x);
        int fy=find(y);
        if(fx!=fy) {
            if(size[fx]>=size[fy]) {
                size[fx]+=size[fy];
                represent[fy]=fx;
            }else {
                size[fy]+=size[fx];
                represent[fx]=fy;
            }
        }
    }
}
