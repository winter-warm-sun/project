import java.util.Comparator;
import java.util.PriorityQueue;

public class IPO {
    public static class Program {
        public int cost;
        public int profit;

        public Program(int profit, int cost) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    public static int findMaximizedCapital(int K,int W,int[] costs,int[] profits) {
        PriorityQueue<Program> minCostQueue=new PriorityQueue<>(new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return o1.cost- o2.cost;
            }
        });
        PriorityQueue<Program> maxProfitQueue=new PriorityQueue<>(new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return o2.profit- o2.profit;
            }
        });
        for (int i = 0; i < costs.length; i++) {
            minCostQueue.add(new Program(costs[i],profits[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!minCostQueue.isEmpty()&&minCostQueue.peek().cost<=W) {
                maxProfitQueue.add(minCostQueue.poll());
            }
            if(maxProfitQueue.isEmpty()) {
                return W;
            }
            W+=maxProfitQueue.poll().profit;
        }
        return W;
    }
}
