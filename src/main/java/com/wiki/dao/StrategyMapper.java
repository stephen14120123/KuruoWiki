package com.wiki.dao;

import com.wiki.entity.StrategyGuide;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StrategyMapper {
    // 根据角色ID查询攻略列表（级联查出作者昵称）
    List<StrategyGuide> getStrategiesByCharId(Integer charId);

    // 查询所有攻略（管理员用）
    List<StrategyGuide> getAllStrategies();

    // 新增攻略
    int insertStrategy(StrategyGuide strategy);

    // 根据ID查询单条攻略（用于校验权限）
    StrategyGuide getById(Integer id);

    // 更新攻略
    int updateStrategy(StrategyGuide strategy);

    // 删除攻略
    int deleteStrategy(Integer id);
}
