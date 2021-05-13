package com.liuqiang.blog.dao;

import com.liuqiang.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepesitory extends JpaRepository<Type,Long> {

    Type findByName(String name);

    @Query("select t from Type t")  // 分页方式查询type对象
    List<Type> findTop(Pageable pageable);
}
