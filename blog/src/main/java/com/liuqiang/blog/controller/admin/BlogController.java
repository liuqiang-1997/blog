package com.liuqiang.blog.controller.admin;

import com.liuqiang.blog.logutils.CheckUtils;
import com.liuqiang.blog.pojo.Blog;
import com.liuqiang.blog.pojo.BlogQuery;
import com.liuqiang.blog.pojo.Blogger;
import com.liuqiang.blog.service.BlogService;
import com.liuqiang.blog.service.TagsService;
import com.liuqiang.blog.service.TypeService;
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
@RequestMapping("admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagsService tagsService;


    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                       HttpSession session,
                       Model model,
                       BlogQuery blog) {
        Object user = session.getAttribute("blogger");
        if (user == null) {
            return CheckUtils.checkLogin(session, model);
        }
        model.addAttribute("types", typeService.listType());

        model.addAttribute("page", blogService.listBlog(pageable, blog));

        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, HttpSession session,
                         Model model, BlogQuery blog) {
        Object user = session.getAttribute("blogger");
        if (user == null) {
            return CheckUtils.checkLogin(session, model);
        }
        model.addAttribute("page", blogService.listBlog(pageable, blog));

        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String publish_blog(HttpSession session, Model model) {
        Object user = session.getAttribute("blogger");
        if (user == null) {
            return CheckUtils.checkLogin(session, model);
        }
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagsService.listTag());
        model.addAttribute("blog", new Blog());
        return "admin/publish";
    }
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable("id")Long id, HttpSession session, Model model) {
        Object user = session.getAttribute("blogger");
        if (user == null) {
            return CheckUtils.checkLogin(session, model);
        }
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagsService.listTag());
        model.addAttribute("blog",blog);
        return "admin/publish";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes,HttpSession session,Model model){
        Object user = session.getAttribute("blogger");
        if (user == null) {
            return CheckUtils.checkLogin(session, model);
        }
        blog.setBlogger((Blogger) session.getAttribute("blogger"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTagList(tagsService.listTag(blog.getTagIds()));
        blog.setViews(0);

        Blog blog1 ;
        if(blog.getId() ==null){
            blog1 = blogService.saveBlog(blog);
        }else {
            blog1=blogService.updateBlog(blog.getId(),blog);
        }
        if(blog1==null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id ,RedirectAttributes attributes, Model model,HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }
}
