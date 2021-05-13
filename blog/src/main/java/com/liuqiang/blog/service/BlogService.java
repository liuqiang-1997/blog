package com.liuqiang.blog.service;

import com.liuqiang.blog.pojo.Blog;
import com.liuqiang.blog.pojo.BlogQuery;
import com.oracle.tools.packager.mac.MacAppBundler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    /**
     * 处理博客详情将markdown转换为html
     * @param id
     * @return
     */
    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog );

    Page<Blog> listBlog(Pageable pageable );

    Page<Blog> listBlog(String query,Pageable pageable );

    Page<Blog> listBlog(Long tagId,Pageable pageable );

    List<Blog> listTopBlog(Integer top);

    Map<String,List<Blog>> archivesBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
