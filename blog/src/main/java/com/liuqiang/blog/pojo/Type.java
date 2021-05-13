package com.liuqiang.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客分类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "t_table")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message="分类名称不能为空！")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogList = new ArrayList<>();

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +

                '}';
    }
}
