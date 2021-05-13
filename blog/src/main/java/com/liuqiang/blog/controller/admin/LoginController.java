package com.liuqiang.blog.controller.admin;

import com.liuqiang.blog.logutils.CheckUtils;
import com.liuqiang.blog.pojo.Blogger;
import com.liuqiang.blog.service.BloggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    BloggerService bloggerService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @RequestMapping("/login")
    public String bloggerLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session, RedirectAttributes attributes){
        System.err.println("用户名"+username+"密码"+password);
        Blogger blogger = bloggerService.checkBlogger(username, password);
        System.err.println("blogger==>"+blogger);
        if(blogger != null){
            blogger.setPassword(null);
            session.setAttribute("blogger",blogger);
            return "admin/success";
        }else {
            System.out.println("执行");
            attributes.addFlashAttribute("errormessage","用户名或密码错误");
            return "redirect:/admin";
        }

    }



    @GetMapping("/login")
    public String loginIndex(HttpSession session,Model model){
        Object user = session.getAttribute("blogger");
        if (user == null) {
            return CheckUtils.checkLogin(session, model);
        }
        return "admin/success";
    }
    @GetMapping("/logout")
    public String bloggerLogout(HttpSession session,RedirectAttributes attributes){
        session.removeAttribute("blogger");
        attributes.addFlashAttribute("msg","您已退出当前用户，请重新登录！");
        return "redirect:/admin";
    }



}
