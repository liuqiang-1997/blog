package com.liuqiang.blog.dao;

import com.liuqiang.blog.pojo.Comment;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepesitory extends JpaRepository<Comment,Long> {

    /**
     *
     * @param blogId 博客id
     * @param sort 排序
     * @return
     */
    List<Comment> findByBlogIdAndAndParentCommentNull(Long blogId, Sort sort);
}
