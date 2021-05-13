package com.liuqiang.blog.controller.admin;

import com.liuqiang.blog.logutils.CheckUtils;
import com.liuqiang.blog.pojo.Type;
import com.liuqiang.blog.service.TypeService;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 查询所有
     * @param pageable
     * @param model
     * @return 列表
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        HttpSession session,Model model){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        Page<Type> typePage = typeService.listType(pageable);
        model.addAttribute("page",typePage);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String addTypes(Model model,HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        model.addAttribute("type",new Type());
        return "admin/typespublish";
    }

    /**
     * 新增
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/type_post")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes,HttpSession session,
                       Model model){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        Type name = typeService.getByName(type.getName());
        if(name != null){
            result.rejectValue("name","nameError","该分类名称已存在！");
        }

        if(result.hasErrors()){
            return "admin/typespublish";
        }
        Type type1 = typeService.saveType(type);
        if(type1 == null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model,HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        model.addAttribute("type",typeService.getType(id));
        return "admin/typespublish";
    }
    @PostMapping("/type_post/{id}")
    public String editpost(@Valid Type type, BindingResult result,@PathVariable Long id,
                           RedirectAttributes attributes, Model model,HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        Type name = typeService.getByName(type.getName());
        if(name != null){
            result.rejectValue("name","nameError","该分类名称已存在！");
        }

        if(result.hasErrors()){
            return "admin/typespublish";
        }
        Type type1 = typeService.updateType(id,type);
        if(type1 == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id ,RedirectAttributes attributes, Model model,HttpSession session){
        Object user = session.getAttribute("blogger");
        if(user ==null){
            return CheckUtils.checkLogin(session, model);
        }
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
