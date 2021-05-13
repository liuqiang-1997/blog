package com.liuqiang.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客评论： 评论者昵称，评论者头像，评论内容，评论时间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id  // 主键
    @GeneratedValue  // 自增
    private Long id;
    private String email;  // 评论者邮箱
    private String nickname;  // 评论者昵称
    private String avatar;  // 评论者头像
    private String content;  // 评论内容
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;  // 评论时间

    @ManyToOne
    private Blog blog;

    private boolean admin;

//    评论与回复内部关联
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    @ManyToOne
    private Comment parentComment;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
//                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                '}';
    }
}
