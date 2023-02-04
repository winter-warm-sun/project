package com.example.demo.searcher;

import com.example.demo.util.ThreadPool;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;

public class DocSearcher {
    private static final String STOP_WORD_PATH="D:/project/doc_searcher_index/stop_word.txt";
    private HashSet<String> stopWord=new HashSet<>();

    private Index index=new Index();

    // 用于处理最后结果的线程
    private static ExecutorService service= ThreadPool.executorService();

    // 在构造该类时，完成索引的加载
    public DocSearcher() {
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
        ArrayList<DocWeight> termResult = new ArrayList<>();
        for (Term term : terms) {
            String word = term.getName();
            // 虽然倒排索引中, 有很多的词. 但是这里的词一定都是之前的文档中存在的.
            ArrayList<DocWeight> docWeights = index.getDocWeights(word);

            termResult.addAll(docWeights);
        }


        // 3. [合并] 针对多个分词结果触发出的相同文档, 进行权重合并
        List<DocWeight> allTermResult = mergeResult(termResult);


        // 4. [排序] 针对触发的结果按照权重降序排序
        allTermResult.sort(new Comparator<DocWeight>() {
            @Override
            public int compare(DocWeight o1, DocWeight o2) {
                // 如果是升序排序: return o1.getWeight() - o2.getWeight();
                // 如果是降序排序: return o2.getWeight() - o1.getWeight();
                return o2.getWeight() - o1.getWeight();
            }
        });

        // 5. [包装结果] 针对排序的结果, 去查正排, 构造出要返回的数据.
        List<Result> results = new ArrayList<>();

        for (DocWeight docWeight:allTermResult) {
            Result result = new Result();
            result.setTitle(docWeight.getTitle());
            result.setUrl(docWeight.getUrl());
            result.setDesc(GenDesc(docWeight.getContent(), terms));
            results.add(result);
        }

        return results;
    }

    // 利用map实现权重合并
    private ArrayList<DocWeight> mergeResult(ArrayList<DocWeight> tokenResults) {
        HashMap<Integer,DocWeight> map=new HashMap<>();
        for (DocWeight docWeight:tokenResults) {
            int docid=docWeight.getDocid();
            if(map.get(docid)==null) {
                map.put(docid,docWeight);
            }else {
                int weight1=map.get(docid).getWeight();
                int weight2=docWeight.getWeight();
                docWeight.setWeight(weight1+weight2);
            }
        }
        Collection<DocWeight> values=map.values();
        ArrayList<DocWeight> result=new ArrayList<>(values);
        return result;
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
