package com.example.demo.searcher;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.*;
import java.util.*;

public class DocSearcher {
    private static final String STOP_WORD_PATH="D:\\project\\doc_searcher_index\\stop_word.txt";
    private HashSet<String> stopWord=new HashSet<>();

    private Index index=new Index();

    // 在构造该类时，完成索引的加载
    public DocSearcher() {
        index.load();
        loadStopWord();
    }

    // 加载暂停词表
    private void loadStopWord() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(STOP_WORD_PATH))) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    // 读取文件完毕!
                    break;
                }
                stopWord.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据查询词，进行搜索，得到搜索结果集合（先通过查询词进行倒排搜索）
    // 结果集合中包含若干条记录，每个记录中包含搜索结果的标题、描述、URL（再根据查找到的结果进行正排搜索）

    public List<Result> search(String query) {
        // 1. [分词] 针对 query 这个查询词进行分词
        List<Term> oldTerms = ToAnalysis.parse(query).getTerms();
        List<Term> terms = new ArrayList<>();
        // 针对分词结果, 使用暂停词表进行过滤
        for (Term term : oldTerms) {
            if (stopWord.contains(term.getName())) {
                continue;
            }
            terms.add(term);
        }

        // 2. [触发] 针对分词结果来查倒排
        ArrayList<ArrayList<Weight>> termResult = new ArrayList<>();
        for (Term term : terms) {
            String word = term.getName();
            // 虽然倒排索引中, 有很多的词. 但是这里的词一定都是之前的文档中存在的.
            ArrayList<Weight> invertedList = index.getInverted(word);
            if (invertedList == null) {
                // 说明这个词在所有文档中都不存在.
                continue;
            }
            termResult.add(invertedList);
        }
        // 3. [合并] 针对多个分词结果触发出的相同文档, 进行权重合并
        List<Weight> allTermResult = mergeResult(termResult);
        // 4. [排序] 针对触发的结果按照权重降序排序
        allTermResult.sort(new Comparator<Weight>() {
            @Override
            public int compare(Weight o1, Weight o2) {
                // 如果是升序排序: return o1.getWeight() - o2.getWeight();
                // 如果是降序排序: return o2.getWeight() - o1.getWeight();
                return o2.getWeight() - o1.getWeight();
            }
        });
        // 5. [包装结果] 针对排序的结果, 去查正排, 构造出要返回的数据.
        List<Result> results = new ArrayList<>();
        for (Weight weight : allTermResult) {
            DocInfo docInfo = index.getDocInfo(weight.getDocId());
            Result result = new Result();
            result.setTitle(docInfo.getTitle());
            result.setUrl(docInfo.getUrl());
            result.setDesc(GenDesc(docInfo.getContent(), terms));
            results.add(result);
        }
        return results;
    }

    static class Pos {
        public int row;
        public int col;

        public Pos(int row,int col) {
            this.row=row;
            this.col=col;
        }
    }
    private ArrayList<Weight> mergeResult(ArrayList<ArrayList<Weight>> tokenResults) {
        // 1.先把每行的结果按照id升序排序
        for (ArrayList<Weight> row:tokenResults) {
            row.sort(new Comparator<Weight>() {
                @Override
                public int compare(Weight o1, Weight o2) {
                    return o1.getDocId()-o2.getDocId();
                }
            });
        }
        // 2.借助优先级队列，进行归并
        ArrayList<Weight> target=new ArrayList<>();
        //      2.1创建优先级队列，指定比较规则
        PriorityQueue<Pos> queue=new PriorityQueue<>(new Comparator<Pos>() {
            @Override
            public int compare(Pos o1, Pos o2) {
                Weight w1=tokenResults.get(o1.row).get(o1.col);
                Weight w2=tokenResults.get(o2.row).get(o2.col);
                return w1.getDocId()- w2.getDocId();
            }
        });
        //      2.2 初始化队列，放入每行的第一列元素
        for (int row=0;row<tokenResults.size();row++) {
            queue.offer(new Pos(row,0));
        }
        //      2.3 循环从队列中取元素
        while (!queue.isEmpty()) {
            Pos curPos=queue.poll();
            Weight curWeight=tokenResults.get(curPos.row).get(curPos.col);
            if(target.size()!=0) {
                Weight lastWeight=target.get(target.size()-1);
                if(curWeight.getDocId()==lastWeight.getDocId()) {
                    // 合并weight的权重
                    lastWeight.setWeight(lastWeight.getWeight()+ curWeight.getWeight());
                } else {
                    // 不合并，直接插入
                    target.add(curWeight);
                }
            }else {
                target.add(curWeight);
            }
            Pos newPos=new Pos(curPos.row,curPos.col+1);
            if(newPos.col>=tokenResults.get(newPos.row).size()) {
                // 当前行已经到达末尾了
                continue;
            }
            queue.offer(newPos);
        }
        return target;
    }

    // 正文摘要实现
    private String GenDesc(String content, List<Term> terms) {
        // 用分词结果中的第一个在描述中找到的词，作为位置的中心
        int firstPos=-1;
        for (Term term:terms) {
            String firstWord=term.getName();
            content=content.toLowerCase().replaceAll("\\b"+firstWord+"\\b"," "+firstWord+" ");
            firstPos=content.toLowerCase().indexOf(" "+firstWord+" ");// 保证该单词必须是独立的单词
            if(firstPos>=0) {
                break;
            }
        }
        // 如果所有的分词结果在正文中都不存在，则直接返回空的描述
        if(firstPos==-1) {
            if (content.length() > 160) {
                return content.substring(0, 160) + "...";
            }
            return content;
        }
        /* 直接截取firstPos周围的文本
        从firstPos往前找60个字符作为描述开始
        然后从描述开始位置往后找160个字符作为整个描述
        （60与160都是自定义的）
         */
        String desc="";
        int descBeg=firstPos<60 ? 0:firstPos-60;
        if(descBeg+160>content.length()) {
            desc=content.substring(descBeg);
        }else {
            // 正文长度充足，在最后加上...
            desc=content.substring(descBeg,descBeg+160)+"...";
        }

        // 实现标红逻辑
        for (Term term:terms) {
            String word=term.getName();
            desc=desc.replaceAll("(?i)"+" "+word+" "," <i>"+word+"</i> ");
        }
        return desc;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        DocSearcher docSearcher=new DocSearcher();
        while (true) {
            System.out.println("-> ");
            String query=scanner.next();
            List<Result> results=docSearcher.search(query);
            for (Result result:results) {
                System.out.println("=========================");
                System.out.println(result);
            }
        }
    }
}
