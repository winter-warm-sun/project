package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.common.SessionUtil;
import com.example.demo.model.NewsInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.service.NewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    @RequestMapping("/list")
    public List<NewsInfo> getList(HttpServletRequest request) {
        UserInfo userInfo=SessionUtil.getLoginUser(request);
        if(userInfo!=null) {
            return newsService.getList();
        }
        return null;
    }

    @RequestMapping("/detailbyid")
    public Object getDetailById(HttpServletRequest request,Integer nid) {
        if(nid!=null&&nid>0) {
            //  根据新闻id查询新闻详情
            NewsInfo newsInfo=newsService.getDetail(nid);
            UserInfo userInfo=SessionUtil.getLoginUser(request);
            if(userInfo!=null) {
                return Result.success(newsInfo);
            }
        }
        return Result.fail(-1,"查询失败");
    }
    @RequestMapping("/add")
    public int add(HttpServletRequest request,String title,String content) {
        UserInfo userInfo=SessionUtil.getLoginUser(request);
        if(userInfo!=null&&userInfo.getId()>0) {
            return newsService.add(title,content);
        }else {
            return 0;
        }
    }

    @RequestMapping("/delete")
    public int delete(HttpServletRequest request,Integer nid) {
        if(nid!=null&&nid>0) {
            // 根据新闻id查询新闻详情
            NewsInfo newsInfo=newsService.getDetail(nid);
            // 校验登录
            UserInfo userInfo=SessionUtil.getLoginUser(request);
            if(userInfo!=null&&newsInfo!=null) {
                return newsService.delete(nid);
            }
        }
        return 0;
    }

    @RequestMapping("/update")
    public int update(HttpServletRequest request,Integer nid,String title,String content) {
        if(nid!=null&&nid>0) {
            UserInfo userInfo=SessionUtil.getLoginUser(request);
            if(userInfo!=null&&userInfo.getId()>0) {
                return newsService.update(nid,title,content);
            }
        }
        return 0;
    }
}
