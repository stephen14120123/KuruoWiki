package com.wiki.dao;

import com.wiki.entity.CharacterInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 这是一个 MyBatis 的 Mapper 接口。
 * 里面不需要写任何具体的实现代码，MyBatis 会自动帮我们生成。
 */
@Mapper
public interface CharacterMapper {

    // 查询所有角色的方法声明
    List<CharacterInfo> getAllCharacters();
}