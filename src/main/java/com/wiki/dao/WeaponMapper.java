package com.wiki.dao;

import com.wiki.entity.WeaponInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WeaponMapper {
    // 获取所有武器
    List<WeaponInfo> getAllWeapons();

    // 新增武器
    int insertWeapon(WeaponInfo weapon);

    // 修改武器
    int updateWeapon(WeaponInfo weapon);

    // 删除武器
    int deleteWeapon(Integer id);
}