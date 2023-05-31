import java.util.ArrayList;
import java.util.List;

public class MaxHappy {
    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int happy) {
            this.happy = happy;
            nexts=new ArrayList<>();
        }
    }

    public static class Info {
        public int yes;
        public int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static Info process(Employee x) {
        if(x==null) {
            return new Info(0,0);
        }
        int no=0;
        int yes=x.happy;
        for(Employee next:x.nexts) {
            Info info=process(next);
            no+=Math.max(info.no, info.yes);
            yes+= info.no;
        }
        return new Info(yes,no);
    }
}
