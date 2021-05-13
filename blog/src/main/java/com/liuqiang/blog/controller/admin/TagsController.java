package com.liuqiang.blog.controller.admin;

import com.liuqiang.blog.logutils.CheckUtils;
import com.liuqiang.blog.pojo.Tag;
import com.liuqiang.blog.pojo.Type;
import com.liuqiang.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("/tags")
    public String Tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable,
                       Model model, HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        Page<Tag> typePage = tagsService.tagList(pageable);
        model.addAttribute("page",typePage);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String addTags(HttpSession session,Model model){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        model.addAttribute("tag",new Tag());
        return "admin/tagspublish";

    }

    @PostMapping("/tags_post")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes, HttpSession session,
                       Model model){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        Tag name = tagsService.getByName(tag.getName());
        if(name != null){
            result.rejectValue("name","nameError","该分类名称已存在！");
        }

        if(result.hasErrors()){
            return "admin/tagspublish";
        }
        Tag tag1 = tagsService.saveTags(tag);
        if(tag1 == null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("tags/{id}/input")
    public String editInput(@PathVariable Long id, HttpSession session, Model model ){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        model.addAttribute("tag",tagsService.getTag(id));
        return "admin/tagspublish";
    }

    @PostMapping("/tags_post/{id}")
    public String editpost(@Valid Tag tag, BindingResult result,@PathVariable Long id,
                           RedirectAttributes attributes, Model model,HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        Tag name = tagsService.getByName(tag.getName());
        if(name != null){
            result.rejectValue("name","nameError","该分类名称已存在！");
        }

        if(result.hasErrors()){
            return "admin/tagspublish";
        }
        Tag tag1 = tagsService.updateTags(id,tag);
        if(tag1 == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }



    @GetMapping("tags/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes attributes, Model model, HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        tagsService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
