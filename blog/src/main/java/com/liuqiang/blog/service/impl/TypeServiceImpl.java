package com.liuqiang.blog.service.impl;

import com.liuqiang.blog.dao.TypeRepesitory;
import com.liuqiang.blog.exception.NotFoundException;
import com.liuqiang.blog.pojo.Type;
import com.liuqiang.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeRepesitory typeRepesitory;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepesitory.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepesitory.findById(id).get();
    }

    @Transactional
    @Override
    public Type getByName(String name) {
        return typeRepesitory.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeRepesitory.findAll();
    }

    @Override
    public List<Type> listTopType(Integer top) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogList.size");
        Pageable pageable = PageRequest.of(0,top,sort);
        return typeRepesitory.findTop(pageable);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepesitory.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeRepesitory.findById(id).get();
        if(type1 == null){
            throw new NotFoundException("TypeNotFound！！！");
        }
        BeanUtils.copyProperties(type,type1);
        return typeRepesitory.save(type1);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepesitory.deleteById(id);
    }
}
