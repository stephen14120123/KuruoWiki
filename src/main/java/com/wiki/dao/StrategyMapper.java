package com.wiki.dao;

import com.wiki.entity.StrategyGuide;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StrategyMapper {
    // 实验七测试接口：获取攻略列表，并级联查出对应的作者信息
    List<StrategyGuide> getStrategyWithCascade();
}