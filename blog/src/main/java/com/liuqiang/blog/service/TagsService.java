package com.liuqiang.blog.service;

import com.liuqiang.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagsService {

    Tag saveTags(Tag tag);

    Tag getTag(Long id);

    Page<Tag> tagList(Pageable pageable);

    Tag updateTags(Long id,Tag tag);

    void deleteTag(Long id);

    Tag getByName(String name);

    List<Tag> listTag();
    List<Tag> listTop(Integer top);

    List<Tag> listTag(String ids);
}
