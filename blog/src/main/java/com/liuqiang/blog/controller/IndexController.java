package com.liuqiang.blog.controller;

import com.liuqiang.blog.exception.NotFoundException;
import com.liuqiang.blog.logutils.CheckUtils;
import com.liuqiang.blog.pojo.BlogQuery;
import com.liuqiang.blog.pojo.Blogger;
import com.liuqiang.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private BloggerService bloggerService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size=5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTopType(6));
        model.addAttribute("tags",tagsService.listTop(10));
        model.addAttribute("recommendBlog",blogService.listTopBlog(8));

        return "index";
    }

    @GetMapping("/adminindex")
    public String adminIndex(HttpSession session,Model model){
        Object user = session.getAttribute("blogger");
        if (user == null) {
            return CheckUtils.checkLogin(session, model);
        }
        return "admin/success";
    }




    @RequestMapping("/enroll")
    public String bloggerEnroll(Blogger blogger, RedirectAttributes attributes, HttpSession session){

        System.out.println(blogger.getNickname()+blogger.getUsername()+blogger.getPassword()
                +blogger.getEmail()+blogger.getAvatar());
        Blogger blogger1 = bloggerService.saveBlogger(blogger);
        if(blogger1==null){
            attributes.addFlashAttribute("errormessage","注册失败");
            return "redirect:/admin/enroll";
        }else {
            attributes.addFlashAttribute("errormessage","注册成功");
            session.setAttribute("blogger",blogger1);
           return  "admin/success";
        }

    }

    @GetMapping("/enroll")
    public String enroll(){

        return "admin/enroll";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                         Pageable pageable, @RequestParam("query") String query,Model model){

        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }



    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id,Model model, HttpSession session ){
       model.addAttribute("blog",blogService.getAndConvert(id));
//        Object user = session.getAttribute("blogger");
//        if(user == null){
//            model.addAttribute("message","评论区权限只为注册用户开放");
//        }
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog";
    }

//    @GetMapping("/archives")
//    public String archives(){
//
//        return "archives";
//    }

    @GetMapping("/tags")
    public String tags(){

        return "tags";
    }

    @GetMapping("/myself")
    public String myself(){
        return "myself";
    }


    @GetMapping("/footer/newblog")
    public String bewblogs(Model model){
        model.addAttribute("newblogs",blogService.listTopBlog(5));

        return "common :: newbloglist";
    }
}
