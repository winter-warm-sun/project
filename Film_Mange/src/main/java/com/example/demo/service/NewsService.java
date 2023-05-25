package com.example.demo.service;

import com.example.demo.mapper.NewsMapper;
import com.example.demo.model.NewsInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsService {
    @Resource
    private NewsMapper newsMapper;

    public List<NewsInfo> getList() {
        return newsMapper.getList();
    }

    public int add(String title, String content) {
        return newsMapper.add(title,content);
    }

    public NewsInfo getDetail(Integer nid) {
        return newsMapper.getDetail(nid);
    }

    public int delete(Integer nid) {
        return newsMapper.delete(nid);
    }

    public int update(Integer nid, String title, String content) {
        return newsMapper.update(nid,title,content);
    }
}
