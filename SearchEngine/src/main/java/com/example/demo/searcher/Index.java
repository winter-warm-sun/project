package com.example.demo.searcher;

import com.example.demo.mapper.IndexMapper;
import com.example.demo.util.ThreadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
@Slf4j
@Component
public class Index {
    public static final String INPUT_PATH="D:/project/doc_searcher_index/";

    @Autowired
    private IndexMapper indexMapper;

    public static Index index;

    @PostConstruct
    public void init() {
        index=this;
        index.indexMapper=this.indexMapper;
    }
    // 用于完成分批次插入任务的线程池
    private static ExecutorService service= ThreadPool.executorService();

    // 正排索引,下标对应docId
    private ArrayList<DocInfo> forwardIndex=new ArrayList<>();

    // 倒排索引，key是分词结果，value是这个分词term对应的倒排拉链（包含一堆docId）
    private HashMap<String,ArrayList<Weight>> invertedIndex=new HashMap<>();


    // 新创建两个锁对象
    private Object locker1=new Object();
    private Object locker2=new Object();

    // 1.根据docId查正排
    public DocInfo getDocInfo(int docid) {
        return index.indexMapper.searchForwardIndex(docid);
    }

    // 2.根据分词结果查倒排
    public ArrayList<Weight> getInverted(String term) {
        return index.indexMapper.searchInvertedIndex(term);
    }

    // 3.向索引中新增一条文档
    public void addDoc(String title,String url,String content) {
        // 新增文档操作，需要同时给正排索引和倒排索引新增信息
        // 构建正排索引
        DocInfo docInfo=buildForward(title,url,content);
        // 构建倒排索引
        buildInverted(docInfo);
    }

    private void buildInverted(DocInfo docInfo) {
        class WordCnt {
            // 表示这个词在标题中出现的次数
            public int titleCount;
            // 表示这个词在正文中出现的次数
            public int contentCount;
        }
        // 这个数据结构用来统计词频
        HashMap<String,WordCnt> wordCntHashMap=new HashMap<>();

        // 1.针对文档标题进行分词
        List<Term> terms= ToAnalysis.parse(docInfo.getTitle()).getTerms();
        // 2.遍历分词结果，统计每个词出现的次数
        for (Term term:terms) {
            // 先判定一下term是否存在
            String word=term.getName();
            WordCnt wordCnt=wordCntHashMap.get(word);
            if(wordCnt==null) {
                // 如果不存在,就创建一个新的键值对，插入进去,titleCount设为1
                WordCnt newWordCnt=new WordCnt();
                newWordCnt.titleCount=1;
                newWordCnt.contentCount=0;
                wordCntHashMap.put(word,newWordCnt);
            }else {
                // 如果存在,就找到之前的值，然后把对应的titleCount+1
                wordCnt.titleCount+=1;
            }
        }
        // 3.针对正文进行分词
        terms=ToAnalysis.parse(docInfo.getContent()).getTerms();
        // 4.遍历分词结果，统计每个词出现的次数
        for (Term term:terms) {
            // 先判定一下term是否存在
            String word=term.getName();
            WordCnt wordCnt=wordCntHashMap.get(word);
            if(wordCnt==null) {
                // 如果不存在,就创建一个新的键值对，插入进去,titleCount设为1
                WordCnt newWordCnt=new WordCnt();
                newWordCnt.titleCount=0;
                newWordCnt.contentCount=1;
                wordCntHashMap.put(word,newWordCnt);
            }else {
                // 如果存在,就找到之前的值，然后把对应的titleCount+1
                wordCnt.contentCount+=1;
            }
        }
        // 5.把上面的结果汇总到HashMap中
        /*
         6.遍历这个HashMap,依次来更新倒排索引中的结果
         最终文档的权重，设定成标题中出现的次数*10+正文中出现的次数
         */
        for (Map.Entry<String,WordCnt> entry: wordCntHashMap.entrySet()) {
            // 先根据这里的词，去倒排索引中查找一下
            synchronized (locker2) {
                List<Weight> invertedList=invertedIndex.get(entry.getKey());
                if(invertedList==null) {
                    // 如果为空，就插入一个新的键值对
                    ArrayList<Weight> newInvertedList=new ArrayList<>();
                    // 把新的文档构造成weight对象，插入进来
                    Weight weight=new Weight();
                    weight.setDocId(docInfo.getDocid());
                    weight.setWeight(entry.getValue().titleCount*10+entry.getValue().contentCount);
                    newInvertedList.add(weight);
                    invertedIndex.put(entry.getKey(),newInvertedList);
                }else {
                    // 如果非空，就把当前这个文档，构造出一个weight对象，插入到倒排拉链的后面
                    Weight weight=new Weight();
                    weight.setDocId(docInfo.getDocid());
                    weight.setWeight(entry.getValue().titleCount*10+entry.getValue().contentCount);
                    invertedList.add(weight);
                }
            }
        }
    }

    private DocInfo buildForward(String title, String url, String content) {
        DocInfo docInfo=new DocInfo();
        docInfo.setTitle(title);
        docInfo.setUrl(url);
        docInfo.setContent(content);
        synchronized (locker1) {
            docInfo.setDocid(forwardIndex.size());
            forwardIndex.add(docInfo);
        }
        return docInfo;
    }

    // 4.向数据库中保存索引
    public void save() {
        long beg = System.currentTimeMillis();
        log.info("保存索引开始!");
        saveForwardIndex();
        saveInvertedIndex();
        long end = System.currentTimeMillis();
        log.info("保存索引结束! 消耗时间: " + (end - beg));
    }

    // 通过动态SQL，将倒排索引数据保存进数据库中
    private void saveInvertedIndex() {
        // 倒排索引信息存储顺序表
        ArrayList<InvertedInfo> invertedList=new ArrayList<>();

        // 遍历Map，将map中的信息保存到list中去
        for (Map.Entry<String,ArrayList<Weight>> entry:invertedIndex.entrySet()) {
            String word=entry.getKey();
            for(Weight high:entry.getValue()) {
                int docid=high.getDocId();
                int weight=high.getWeight();
                InvertedInfo invertedInfo=new InvertedInfo();
                invertedInfo.setWord(word);
                invertedInfo.setDocid(docid);
                invertedInfo.setWeight(weight);
                invertedInfo.setId(invertedList.size());
                invertedList.add(invertedInfo);
            }
        }

        // 通过多线程的方式，将数据插入数据库

        // 1.批量插入时，每次插入多少条记录（由于每条记录比较大，所以此处规定一次插入10条）
        int batchSize=10000;
        // 2.一共需要执行多少次SQL (向上取整)
        int listSize=invertedList.size();
        int times=(int) Math.ceil(1.0*listSize/batchSize);
        log.debug("一共需要 {} 批任务。", times);

        CountDownLatch latch=new CountDownLatch(times);

        // 3.开始分批次插入
        for (int i=0;i<listSize;i+=batchSize) {
            // 从invertedList中分批次截取这批要插入的文档列表
            int beg=i;
            int end=Integer.min(beg+batchSize,listSize);

            Runnable task=()-> {
                List<InvertedInfo> subList= invertedList.subList(beg,end);

                // 针对subList做批量插入
                index.indexMapper.saveInvertedIndex(subList);

                latch.countDown(); // 每次完成任务后，countDown()，让 latch 的个数减一
            };
            service.submit(task); // 主线程只负责把一批批的任务提交给线程池，具体的插入工作，由线程池来完成
        }

        // 4.循环结束，意味着主线程把任务提交完成了
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 通过动态SQL，将正排索引数据保存进数据库中
    private void saveForwardIndex() {
        // 通过多线程的方式，将数据插入数据库

        // 1.批量插入时，每次插入多少条记录（由于每条记录比较大，所以此处规定一次插入10条）
        int batchSize=10;
        // 2.一共需要执行多少次SQL (向上取整)
        int listSize=forwardIndex.size();
        int times=(int) Math.ceil(1.0*listSize/batchSize);
        log.debug("一共需要 {} 批任务。", times);

        CountDownLatch latch=new CountDownLatch(times);

        // 3.开始分批次插入
        for (int i=0;i<listSize;i+=batchSize) {
            // 从invertedList中分批次截取这批要插入的文档列表
            int beg=i;
            int end=Integer.min(beg+batchSize,listSize);

            Runnable task=()-> {
                List<DocInfo> subList= forwardIndex.subList(beg,end);

                // 针对subList做批量插入
                index.indexMapper.saveForwardIndex(subList);

                latch.countDown(); // 每次完成任务后，countDown()，让 latch 的个数减一
            };
            service.submit(task); // 主线程只负责把一批批的任务提交给线程池，具体的插入工作，由线程池来完成
        }

        // 4.循环结束，意味着主线程把任务提交完成了
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
