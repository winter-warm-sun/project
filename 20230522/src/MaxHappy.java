import java.util.ArrayList;
import java.util.List;

public class MaxHappy {
    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int happy) {
            this.happy = happy;
            this.nexts = new ArrayList<>();
        }
    }

    public static int maxHappy(Employee boss) {
        Info allInfo=process(boss);
        return Math.max(allInfo.no, allInfo.yes);
    }

    public static class Info {
        public int no;
        public int yes;

        public Info(int no, int yes) {
            this.no = no;
            this.yes = yes;
        }
    }

    public static Info process(Employee x) {
        if(x==null) {
            return new Info(0,0);
        }
        int no=0;
        int yes=x.happy;
        for (Employee next:x.nexts) {
            Info nextInfo=process(next);
            no+=Math.max(nextInfo.no, nextInfo.yes);
            yes+=nextInfo.no;
        }
        return new Info(no,yes);
    }
}
