package com.liuqiang.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 标题，内容，首图，标记，浏览次数，赞赏开启，版权开启，评论开启，发布，是否推荐，创建时间，更新时间
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;  // 博客ID
    private String title;  // 博客标题
    private String content;  // 博客内容
    private String firstPicture;  // 博客首图
    private String flag;  // 博客标记
    private Integer views ;  // 浏览次数
    private boolean appreciation;  // 是否赞赏
    private boolean shareStatement;  // 是否版权
    private boolean commentabled;  // 是否评论
    private boolean Published;  // 是否发布
    private boolean recommend;  // 是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;  // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;  // 更新时间
    private String description;  // 博客描述
    @ManyToOne
    private Type type;  // 博客分类

    @ManyToMany(cascade = {CascadeType.PERSIST})  // 级联新增
    private List<Tag> tagList = new ArrayList<>();  // 博客标签

    @ManyToOne
    private Blogger blogger;

    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList = new ArrayList<>();

    @Transient
    private String tagIds;

    public void init(){
        this.tagIds = tagsToIds(this.getTagList());
    }

    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag :tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", Published=" + Published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ",description"+description+
                ", commentList=" + commentList +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }
}
