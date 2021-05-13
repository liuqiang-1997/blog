package com.liuqiang.blog.service.impl;

import com.liuqiang.blog.dao.TagsRepesitory;
import com.liuqiang.blog.exception.NotFoundException;
import com.liuqiang.blog.pojo.Tag;
import com.liuqiang.blog.pojo.Type;
import com.liuqiang.blog.service.TagsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service

public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepesitory tagsRepesitory;


    @Transactional
    @Override
    public Tag saveTags(Tag tag) {
        return tagsRepesitory.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagsRepesitory.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Tag> tagList(Pageable pageable) {
        return tagsRepesitory.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTags(Long id, Tag tag) {
        Tag tag1 = tagsRepesitory.findById(id).get();
        if(tag1 == null){
            throw new NotFoundException("TypeNotFound！！！");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagsRepesitory.save(tag1);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
       tagsRepesitory.deleteById(id);
    }

    @Transactional
    @Override
    public Tag getByName(String name) {
        return tagsRepesitory.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagsRepesitory.findAll();
    }

    @Override
    public List<Tag> listTop(Integer top) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogList.size");
        Pageable pageable = PageRequest.of(0,top,sort);
        return tagsRepesitory.findTop(pageable);

    }

//    private List<Long> convertToList(String ids){
//        List<Long> list = new ArrayList<>();
//        if("".equals(ids) && ids != null){
//            String[] idarray = ids.split(",");
//            for (int i = 0; i < idarray.length; i++) {
//                list.add(new Long(idarray[i]));
//            }
//        }
//        return list;
//    }

    @Override
    @Transactional
    public List<Tag> listTag(String ids) {
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
         return tagsRepesitory.findAllById(list);
    }

}
