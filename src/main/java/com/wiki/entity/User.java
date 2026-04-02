package com.wiki.entity;

import lombok.Data;
import java.util.Date;

@Data // 只要加了这个注解，Lombok 会在编译时自动帮你生成所有的 get/set 方法和 toString()
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Integer role;       // 0-普通玩家, 1-管理员
    private Date createTime;
}