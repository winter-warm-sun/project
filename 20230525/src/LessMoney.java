import java.util.PriorityQueue;

public class LessMoney {
    public static int lessMoney(int[] arr) {
        PriorityQueue<Integer> queue=new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            queue.add(arr[i]);
        }
        int sum=0;
        int cur=0;
        while (queue.size()>1) {
            cur=queue.poll()+queue.poll();
            sum+=cur;
            queue.offer(cur);
        }
        return sum;
    }
}
