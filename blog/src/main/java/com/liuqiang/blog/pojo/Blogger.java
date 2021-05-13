package com.liuqiang.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户，昵称,用户名，密码，邮箱，类型，头像，创建时间，更新时间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "t_blogger")
public class Blogger {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type = 0;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

    @OneToMany(mappedBy = "blogger")
    private List<Blog> blogList = new ArrayList<>();

    @Override
    public String toString() {
        return "Blogger{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", blogList=" + blogList +
                '}';
    }
}
