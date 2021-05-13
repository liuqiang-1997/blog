package com.liuqiang.blog.dao;

import com.liuqiang.blog.pojo.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloggerRepesitory extends JpaRepository<Blogger,Long> {

    Blogger findByUsernameAndPassword(String userName,String password);
}
