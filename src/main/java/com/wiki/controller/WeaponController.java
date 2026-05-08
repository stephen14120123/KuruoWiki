package com.wiki.controller;

import com.wiki.common.RequiresRole;
import com.wiki.common.Result;
import com.wiki.entity.WeaponInfo;
import com.wiki.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weapons")
public class WeaponController {

    @Autowired
    private WeaponService weaponService;

    // 获取所有武器列表 (GET /api/weapons)
    @GetMapping
    public Result<List<WeaponInfo>> getAll() {
        return Result.success(weaponService.getAllWeapons());
    }

    // 后台：新增武器 (POST /api/weapons) - 需要管理员权限
    @PostMapping
    @RequiresRole(1)
    public Result<String> addWeapon(@RequestBody WeaponInfo weapon) {
        boolean success = weaponService.saveWeapon(weapon);
        return success ? Result.success("武器添加成功") : Result.error("武器添加失败");
    }

    // 后台：修改武器 (PUT /api/weapons) - 需要管理员权限
    @PutMapping
    @RequiresRole(1)
    public Result<String> updateWeapon(@RequestBody WeaponInfo weapon) {
        boolean success = weaponService.updateWeapon(weapon);
        return success ? Result.success("武器修改成功") : Result.error("武器修改失败");
    }

    // 后台：删除武器 (DELETE /api/weapons/1) - 需要管理员权限
    @DeleteMapping("/{id}")
    @RequiresRole(1)
    public Result<String> deleteWeapon(@PathVariable Integer id) {
        boolean success = weaponService.deleteWeapon(id);
        return success ? Result.success("武器删除成功") : Result.error("武器删除失败");
    }
}
