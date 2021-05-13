package com.liuqiang.blog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlers {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异常拦截
     * @param request 有异常的请求
     * @param e 异常的类型
     * @return 视图
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL:{},Exception:{}",request.getRequestURI(),e);
//        判断异常状态是否由springboot处理
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        ModelAndView view = new ModelAndView();
        view.addObject("url",request.getRequestURI());
        view.addObject("exception",e);
        view.setViewName("error/error");
        return view;
    }
}
