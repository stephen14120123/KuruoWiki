package com.wiki.dao;

import com.wiki.entity.CharacterInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CharacterMapper {
    // 查询所有
    List<CharacterInfo> getAllCharacters();

    // 根据ID查询单个
    CharacterInfo getCharacterById(Integer id);

    // 新增角色
    int insertCharacter(CharacterInfo character);

    // 更新角色
    int updateCharacter(CharacterInfo character);

    // 删除角色
    int deleteCharacter(Integer id);
}