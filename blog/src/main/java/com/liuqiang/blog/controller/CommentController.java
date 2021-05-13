package com.liuqiang.blog.controller;

import com.liuqiang.blog.pojo.Blog;
import com.liuqiang.blog.pojo.Blogger;
import com.liuqiang.blog.pojo.Comment;
import com.liuqiang.blog.service.BlogService;
import com.liuqiang.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * 处理评论及回复
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comment/{blogId}")
    public String comments(@PathVariable("blogId")Long blogId, Model model){
        System.err.println("调用了");
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment , HttpSession session){
        Long blogId = comment.getBlog().getId();
        Blog blog = blogService.getBlog(blogId);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        if(blogger != null){
            Long id = blogger.getId();
            Long id1 = blog.getBlogger().getId();

            if(id.equals(id1)){
                comment.setAdmin(true);
            }
        }
        comment.setBlog(blog);
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comment/"+blogId;
    }
}
