package com.wiki.controller;

import com.wiki.common.Result;
import com.wiki.entity.WeaponInfo;
import com.wiki.entity.EchoInfo;
import com.wiki.service.WeaponService;
import com.wiki.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公开接口（不需要登录）
 */
@RestController
public class PublicApiController {

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private EchoService echoService;

    // 获取武器列表
    @GetMapping("/api/weapon/list")
    public List<WeaponInfo> getWeaponList() {
        return weaponService.getAllWeapons();
    }

    // 获取声骸列表
    @GetMapping("/api/echo/list")
    public List<EchoInfo> getEchoList() {
        return echoService.getAllEchoes();
    }
}
