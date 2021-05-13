package com.liuqiang.blog.service;

import com.liuqiang.blog.pojo.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 处理用户相关业务逻辑
 */
public interface BloggerService  {

    Blogger checkBlogger(String username,String password);

    Blogger saveBlogger(Blogger blogger);

}
