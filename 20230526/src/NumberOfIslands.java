import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class NumberOfIslands {
    public static int numIslands1(char[][] board) {
        int islands=0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j]=='1') {
                    islands++;
                    infect(board,i,j);
                }
            }
        }
        return islands;
    }

    // 从(i,j)位置出发，把所有连成一片的"1"字符，变成2
    private static void infect(char[][] board, int i, int j) {
        if(i<0||i== board.length||j<0||j==board[0].length||board[i][j]!='1') {
            return;
        }
        board[i][j]=2;
        infect(board,i-1,j);
        infect(board,i+1,j);
        infect(board,i,j-1);
        infect(board,i,j+1);
    }

    public static int numsIsland2(char[][] board) {
        int row=board.length;
        int col=board[0].length;
        Dot[][] dots=new Dot[row][col];
        List<Dot> dotList=new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(board[i][j]=='1') {
                    dots[i][j]=new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind1 unionFind1=new UnionFind1<>(dotList);
        for (int j = 1; j < col; j++) {
            if(board[0][j-1]=='1'&&board[0][j]=='1') {
                unionFind1.union(dots[0][j-1],dots[0][j]);
            }
        }
        for(int i=1;i<row;i++) {
            if(board[i-1][0]=='1'&&board[i][0]=='1') {
                unionFind1.union(dots[i-1][0],dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if(board[i][j]=='1') {
                    if(board[i][j-1]=='1') {
                        unionFind1.union(dots[i][j-1],dots[i][j]);
                    }
                    if(board[i-1][j]=='1') {
                        unionFind1.union(dots[i-1][j],dots[i][j]);
                    }
                }
            }
        }
        return unionFind1.size();
    }

    public static class Dot {

    }
    public static class UnionFind1<V> {
        public HashMap<V,V> represents;
        public HashMap<V,Integer> sizeMap;

        public UnionFind1(List<V> values) {
            represents=new HashMap<>();
            sizeMap=new HashMap<>();
            for(V cur:values) {
                represents.put(cur,cur);
                sizeMap.put(cur,1);
            }
        }

        private V findRepresent(V cur) {
            Stack<V> path=new Stack<>();
            while (cur!=represents.get(cur)) {
                path.push(cur);
                cur=represents.get(cur);
            }
            while (!path.isEmpty()) {
                represents.put(path.pop(),cur);
            }
            return cur;
        }

        public void union(V a,V b) {
            V aRepresent=findRepresent(a);
            V bRepresent=findRepresent(b);
            if(aRepresent!=bRepresent) {
                int aSize=sizeMap.get(aRepresent);
                int bSize=sizeMap.get(bRepresent);
                if(aSize>=bSize) {
                    represents.put(bRepresent,aRepresent);
                    sizeMap.put(aRepresent,aSize+bSize);
                    sizeMap.remove(bRepresent);
                }else {
                    represents.put(aRepresent,bRepresent);
                    sizeMap.put(bRepresent,aSize+bSize);
                    sizeMap.remove(aRepresent);
                }
            }
        }

        public int size() {
            return sizeMap.size();
        }
    }

    public int numIslands(char[][] board) {
        int row=board.length;
        int col=board[0].length;
        UnionFind2 unionFind2=new UnionFind2(board);
        for (int j = 1; j < col; j++) {
            if(board[0][j-1]=='1'&&board[0][j]=='1') {
                unionFind2.union(0,j-1,0,j);
            }
        }
        for (int i = 1; i < row; i++) {
            if(board[i-1][0]=='1'&&board[i][0]=='1') {
                unionFind2.union(i-1,0,i,0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if(board[i][j]=='1') {
                    if(board[i-1][j]=='1') {
                        unionFind2.union(i-1,j,i,j);
                    }
                    if(board[i][j-1]=='1') {
                        unionFind2.union(i,j-1,i,j);
                    }
                }
            }
        }
        return unionFind2.sets;
    }
    public static class UnionFind2 {
        private int[] represent;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

        public UnionFind2(char[][] board) {
            col=board[0].length;
            sets=0;
            int row= board.length;
            int len=row*col;
            represent=new int[len];
            size=new int[len];
            help=new int[len];
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if(board[r][c]=='1') {
                        int i=index(r,c);
                        represent[i]=i;
                        size[i]=i;
                        sets++;
                    }
                }
            }
        }

        private int index(int r,int c) {
            return r*col+c;
        }

        private int find(int i) {
            int hi=0;
            while (i!=represent[i]) {
                help[hi++]=i;
                i=represent[i];
            }
            while (hi-->0) {
                represent[help[hi]]=i;
            }
            return i;
        }

        public void union(int r1,int c1,int r2,int c2) {
            int i1=index(r1,c1);
            int i2=index(r2,c2);
            int f1=find(i1);
            int f2=find(i2);
            if(f1!=f2) {
                if(size[f1]>=size[f2]) {
                    size[f1]+=size[f2];
                    represent[f2]=f1;
                }else {
                    size[f2]+=size[f1];
                    represent[f1]=f2;
                }
                sets--;
            }
        }
    }
}
