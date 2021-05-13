package com.liuqiang.blog.service;

import com.liuqiang.blog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 博客分类
 */
public interface TypeService {
    /**
     * 新增分类
     * @param type 分类
     * @return type对象
     */
    Type saveType(Type type);

    /**
     * 查询分类
     * @param id 分类的id
     * @return type对象
     */
    Type getType(Long id);

    /**
     * 分页
     * @param pageable 分页对象
     * @return 列表
     */
    Page<Type> listType(Pageable pageable);

    /**
     *
     * @param id 分类id
     * @param type 新的type对象
     * @return type对象
     */
    Type updateType(Long id, Type type);

    /**
     * 删除分类
     * @param id typeid
     */
    void deleteType(Long id);

    /**
     * 根据名称查询分类
     * @param name 分类名
     * @return 分类对象
     */
    Type getByName(String name);

    List<Type> listType();

    List<Type> listTopType(Integer top);

}
