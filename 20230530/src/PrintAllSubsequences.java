import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrintAllSubsequences {

    public static List<String> subsNoRepeat(String s) {
        char[] str=s.toCharArray();
        String path="";
        HashSet<String> set=new HashSet<>();
        process2(str,0,set,path);
        List<String> ans=new ArrayList<>();
        for(String ss:set) {
            ans.add(ss);
        }
        return ans;
    }

    public static void process2(char[] str, int index, HashSet<String> set, String path) {
        if(index==str.length) {
            set.add(path);
            return;
        }
        process2(str,index+1,set,path);
        process2(str,index+1,set,path+String.valueOf(str[index]));
    }

    public static void main(String[] args) {
        String test="accc";
        List<String> ans=subsNoRepeat(test);

        for(String s:ans) {
            System.out.println(s);
        }
    }
}
