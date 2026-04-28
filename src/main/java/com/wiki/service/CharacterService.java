package com.wiki.service;

import com.github.pagehelper.PageHelper; // 分页插件
import com.github.pagehelper.PageInfo;   // 分页信息封装类
import com.wiki.dao.CharacterMapper;
import com.wiki.entity.CharacterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CharacterService {

    @Autowired
    private CharacterMapper characterMapper;

    // 原来的全量查询
    public List<CharacterInfo> getAllCharacters() {
        return characterMapper.getAllCharacters();
    }

    // ==========================================
    // 💡 实验十核心：分页查询角色数据
    // ==========================================
    public PageInfo<CharacterInfo> getCharactersByPage(int pageNum, int pageSize) {
        // 1. 告诉 PageHelper 开始分页：查第 pageNum 页，每页 pageSize 条
        PageHelper.startPage(pageNum, pageSize);
        // 2. 紧跟着的第一个 select 查询会被自动拦截并加上 Limit 分页
        List<CharacterInfo> list = characterMapper.getAllCharacters();
        // 3. 把查出来的数据包装成 PageInfo 对象返回（里面包含了总页数、总条数等前端需要的数据）
        return new PageInfo<>(list);
    }
}