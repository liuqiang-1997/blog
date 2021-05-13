package com.liuqiang.blog.controller;

import com.liuqiang.blog.pojo.Blog;
import com.liuqiang.blog.pojo.BlogQuery;
import com.liuqiang.blog.pojo.Tag;
import com.liuqiang.blog.pojo.Type;
import com.liuqiang.blog.service.BlogService;
import com.liuqiang.blog.service.TagsService;
import com.liuqiang.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagsService tagsService;

    @GetMapping("/types/{id}")
    public String show(@PageableDefault(size=5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable("id") Long id, Model model){
        List<Type> types1 = typeService.listType();

        if(id == -1){
            id= types1.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        Page<Blog> blogs = blogService.listBlog(pageable, blogQuery);
        System.out.println(blogs);
        for (Blog b:blogs
             ) {
            System.err.println(b);
        }
        model.addAttribute("types",types1);
        model.addAttribute("page", blogs);
        model.addAttribute("activetypeId",id);
        return "types";
    }


    @GetMapping("/tags/{id}")
    public String shows(@PageableDefault(size=5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable("id") Long id, Model model){
        List<Tag> tags = tagsService.listTag();
        if(id == -1){
            id= tags.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activetagId",id);
        return "tags";
    }

    @GetMapping("/archives")
    public String achives(Model model){
        model.addAttribute("archivesmap",blogService.archivesBlog());
        model.addAttribute("count",blogService.countBlog());
        return "archives";
    }
}
