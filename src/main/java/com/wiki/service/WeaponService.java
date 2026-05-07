package com.wiki.service;

import com.wiki.dao.WeaponMapper;
import com.wiki.entity.WeaponInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WeaponService {

    @Autowired
    private WeaponMapper weaponMapper;

    public List<WeaponInfo> getAllWeapons() {
        return weaponMapper.getAllWeapons();
    }

    public boolean saveWeapon(WeaponInfo weapon) {
        return weaponMapper.insertWeapon(weapon) > 0;
    }

    public boolean updateWeapon(WeaponInfo weapon) {
        return weaponMapper.updateWeapon(weapon) > 0;
    }

    public boolean deleteWeapon(Integer id) {
        return weaponMapper.deleteWeapon(id) > 0;
    }
}