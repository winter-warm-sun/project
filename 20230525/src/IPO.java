import java.util.Comparator;
import java.util.PriorityQueue;

public class IPO {
    public static class Program {
        public int cost;
        public int profit;

        public Program(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    public static int findMaximizedCapital(int k,int w,int[] costs,int[] profits) {
        PriorityQueue<Program> minCostQueue=new PriorityQueue<>(new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return o1.cost- o2.cost;
            }
        });
        PriorityQueue<Program> maxProfitQueue=new PriorityQueue<>(new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return o2.profit-o1.profit;
            }
        });
        for (int i = 0; i < costs.length; i++) {
            minCostQueue.add(new Program(costs[i],profits[i]));
        }
        for (int i = 0; i < k; i++) {
            while (!minCostQueue.isEmpty()&&minCostQueue.peek().cost<=w) {
                maxProfitQueue.offer(minCostQueue.poll());
            }
            if(maxProfitQueue.isEmpty()) {
                return w;
            }
            w+=maxProfitQueue.poll().profit;
        }
        return w;
    }
}
