package com.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiki.dao.CharacterMapper;
import com.wiki.entity.CharacterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 开启事务保护
public class CharacterService {

    @Autowired
    private CharacterMapper characterMapper;

    public List<CharacterInfo> getAllCharacters() {
        return characterMapper.getAllCharacters();
    }

    public CharacterInfo getCharacterById(Integer id) {
        return characterMapper.getCharacterById(id);
    }

    public PageInfo<CharacterInfo> getCharactersByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CharacterInfo> list = characterMapper.getAllCharacters();
        return new PageInfo<>(list);
    }

    public boolean saveCharacter(CharacterInfo character) {
        return characterMapper.insertCharacter(character) > 0;
    }

    public boolean updateCharacter(CharacterInfo character) {
        return characterMapper.updateCharacter(character) > 0;
    }

    public boolean deleteCharacter(Integer id) {
        return characterMapper.deleteCharacter(id) > 0;
    }
}