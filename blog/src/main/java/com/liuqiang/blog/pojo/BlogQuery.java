package com.liuqiang.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客查询的条件下拉框
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;
//    private Blogger blogger;
}
