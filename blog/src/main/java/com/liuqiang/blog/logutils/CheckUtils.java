package com.liuqiang.blog.logutils;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
public class CheckUtils {

    public static String checkLogin(HttpSession session, Model model){
        // 拦截器判断是否登录
        Object loginUser = session.getAttribute("blogger");
        if (loginUser != null){
            return "admin/blogs";
        }else {
            System.out.println("session失效了");
            model.addAttribute("msg","请您先登录！");
            return "admin/login";
        }
    }
}
