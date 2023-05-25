package com.example.demo.controller;

import com.example.demo.common.SessionUtil;
import com.example.demo.model.NewsInfo;
import com.example.demo.model.TypeInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.service.TypesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/type")
public class TypesController {
    @Resource
    private TypesService typesService;

    // 新增类别
    @RequestMapping("/add")
    public int add(HttpServletRequest request,String type) {
        UserInfo userInfo= SessionUtil.getLoginUser(request);
        if(userInfo!=null&&userInfo.getId()>0) {
            return typesService.add(type);
        }else {
            return 0;
        }
    }
    // 删除类别
    @RequestMapping("/delete")
    public int delete(HttpServletRequest request,Integer tid) {
        UserInfo userInfo=SessionUtil.getLoginUser(request);
        if(userInfo!=null&&userInfo.getId()>0) {
            return typesService.delete(tid);
        }else {
            return 0;
        }
    }
    // 修改类别
    @RequestMapping("/update")
    public int update(HttpServletRequest request,String type,Integer tid) {
        UserInfo userInfo=SessionUtil.getLoginUser(request);
        if(userInfo!=null&&userInfo.getId()>0) {
            return typesService.update(type,tid);
        }else {
            return 0;
        }
    }

    // 查找类别
    @RequestMapping("/list")
    public List<TypeInfo> getList(HttpServletRequest request) {
        UserInfo userInfo=SessionUtil.getLoginUser(request);
        if(userInfo!=null) {
            return typesService.getList();
        }
        return null;
    }
}
