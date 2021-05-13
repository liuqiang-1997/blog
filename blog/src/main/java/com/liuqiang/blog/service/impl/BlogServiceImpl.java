package com.liuqiang.blog.service.impl;

import com.liuqiang.blog.dao.BlogRepesitory;
import com.liuqiang.blog.exception.NotFoundException;
import com.liuqiang.blog.logutils.MarkDownUtils;
import com.liuqiang.blog.logutils.MyBeanUtils;
import com.liuqiang.blog.pojo.Blog;
import com.liuqiang.blog.pojo.BlogQuery;
import com.liuqiang.blog.pojo.Blogger;
import com.liuqiang.blog.pojo.Type;
import com.liuqiang.blog.service.BlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepesitory blogRepesitory;

    @Override
    @Transactional
    public Blog getBlog(Long id) {
        return blogRepesitory.findById(id).get();
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepesitory.findById(id).get();
        if(blog ==null){
            throw new NotFoundException("Sorry ! ResourcesNotFount!!!");
        }
        Blog blog1 = new Blog();
        BeanUtils.copyProperties(blog,blog1);  // 赋值给新的blog对象，防止修改数据库中数据
        String content = blog1.getContent();
        String s = MarkDownUtils.markdownToHtmlExtensions(content);
        blog1.setContent(s);

        blogRepesitory.updateViews(id);
        return blog1;
    }

    @Override
    @Transactional
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepesitory.findAll(new Specification<Blog>() {
            /**
             *
             * @param root 查询对象（可获取字段信息）
             * @param criteriaQuery 放置查询条件的容器
             * @param criteriaBuilder 设置查询条件表达式
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
//                Blogger blogger = (Blogger)session.getAttribute("blogger");
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
//                if(!"".equals(blog.getBlogger())&& blog.getBlogger()!=null &&
//                        blog.getBlogger().getId() ==blogger.getId() ){
//                    predicates.add(criteriaBuilder.like(root.<String>get("blogger").get("id"),"%"+blog.getBlogger()+"%"));
//                }
                if(blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepesitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tagList");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepesitory.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepesitory.findByQuery(query,pageable);
    }



    @Override
    public List<Blog> listTopBlog(Integer top) {
        Sort sort =Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,top,sort);
        return blogRepesitory.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archivesBlog() {
        List<String> years = blogRepesitory.findGroupYear();
        // LinkedHashMap<>保证读写顺序一致
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year, blogRepesitory.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepesitory.count();
    }


    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepesitory.save(blog);
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepesitory.findById(id).get();
        if(blog1 == null){
            throw new NotFoundException("BlogNotFound!");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepesitory.save(blog1);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
       blogRepesitory.deleteById(id);
    }
}
