package com.liuqiang.blog.service.impl;

import com.liuqiang.blog.dao.BloggerRepesitory;
import com.liuqiang.blog.pojo.Blogger;
import com.liuqiang.blog.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class BloggerServiceImpl implements BloggerService {

    @Autowired
    private BloggerRepesitory bloggerRepesitory;

    @Override
    public Blogger checkBlogger(String username, String password) {
        Blogger blogger = bloggerRepesitory.findByUsernameAndPassword(username, password);
        return blogger;
    }

    @Override
    public Blogger saveBlogger(Blogger blogger) {

        return bloggerRepesitory.save(blogger);
    }


}

