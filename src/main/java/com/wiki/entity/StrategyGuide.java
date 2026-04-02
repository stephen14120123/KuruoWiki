package com.wiki.entity;

import lombok.Data;
import java.util.Date;

@Data
public class StrategyGuide {
    private Integer id;
    private Integer userId;         // 发帖人ID
    private Integer characterId;    // 关联的角色ID
    private String title;           // 帖子标题
    private String content;         // 帖子正文
    private Integer views;          // 浏览量
    private Date createTime;

    // 下面这两个是扩展字段，数据库表里没有，但在前端显示时特别有用
    private String authorName;      // 作者昵称
    private String characterName;   // 关联的角色名
}