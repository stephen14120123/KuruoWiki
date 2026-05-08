package com.wiki.controller;

import com.wiki.common.RequiresRole;
import com.wiki.common.Result;
import com.wiki.entity.CharacterInfo;
import com.wiki.entity.EchoInfo;
import com.wiki.entity.StrategyGuide;
import com.wiki.entity.WeaponInfo;
import com.wiki.service.CharacterService;
import com.wiki.service.EchoService;
import com.wiki.service.StrategyService;
import com.wiki.service.UserService;
import com.wiki.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员专用接口
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private EchoService echoService;

    // ==========================================
    // 用户管理
    // ==========================================
    @GetMapping("/users")
    @RequiresRole(1)
    public Result<List<?>> getAllUsers() {
        return Result.success(userService.getAllUsers());
    }

    @PostMapping("/user/delete")
    @RequiresRole(1)
    public Result<String> deleteUser(@RequestParam Integer userId) {
        boolean success = userService.deleteUser(userId);
        return success ? Result.success("用户已删除") : Result.error("删除失败");
    }

    // ==========================================
    // 攻略管理
    // ==========================================
    @GetMapping("/strategies")
    @RequiresRole(1)
    public Result<List<StrategyGuide>> getAllStrategies() {
        // 获取所有攻略（不分角色）
        return Result.success(strategyService.getAllStrategies());
    }

    @PostMapping("/strategy/delete")
    @RequiresRole(1)
    public Result<String> deleteStrategy(@RequestParam Integer strategyId) {
        boolean success = strategyService.removeStrategy(strategyId);
        return success ? Result.success("攻略已删除") : Result.error("删除失败");
    }

    // ==========================================
    // 角色管理
    // ==========================================
    @GetMapping("/characters")
    @RequiresRole(1)
    public List<CharacterInfo> getCharacters() {
        return characterService.getAllCharacters();
    }

    @PostMapping("/character")
    @RequiresRole(1)
    public Result<String> saveOrUpdateCharacter(
            @RequestParam(required = false) Integer id,
            @RequestParam String name,
            @RequestParam Integer rarity,
            @RequestParam String element,
            @RequestParam String weaponType,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) Integer hp,
            @RequestParam(required = false) Integer atk,
            @RequestParam(required = false) Integer def,
            @RequestParam(required = false) Integer crit,
            @RequestParam(required = false) Integer energy,
            @RequestParam String action) {

        CharacterInfo character = new CharacterInfo();
        character.setId(id);
        character.setName(name);
        character.setRarity(rarity);
        character.setElement(element);
        character.setWeaponType(weaponType);
        character.setDescription(description);
        character.setImageUrl(imageUrl);
        character.setHp(hp != null ? hp : 0);
        character.setAtk(atk != null ? atk : 0);
        character.setDef(def != null ? def : 0);
        character.setCrit(crit != null ? crit : 0);
        character.setEnergy(energy != null ? energy : 0);

        if ("add".equals(action)) {
            boolean success = characterService.saveCharacter(character);
            return success ? Result.success("角色已添加") : Result.error("添加失败");
        } else if ("update".equals(action)) {
            boolean success = characterService.updateCharacter(character);
            return success ? Result.success("角色已更新") : Result.error("更新失败");
        } else if ("delete".equals(action)) {
            boolean success = characterService.deleteCharacter(id);
            return success ? Result.success("角色已删除") : Result.error("删除失败");
        }
        return Result.error("操作类型错误");
    }

    // ==========================================
    // 武器管理
    // ==========================================
    @GetMapping("/weapons")
    @RequiresRole(1)
    public List<WeaponInfo> getWeapons() {
        return weaponService.getAllWeapons();
    }

    @PostMapping("/weapon")
    @RequiresRole(1)
    public Result<String> saveOrUpdateWeapon(
            @RequestParam(required = false) Integer id,
            @RequestParam String name,
            @RequestParam Integer rarity,
            @RequestParam String weaponType,
            @RequestParam Integer baseAtk,
            @RequestParam(required = false) String subStatType,
            @RequestParam(required = false) String subStatValue,
            @RequestParam(required = false) String skillName,
            @RequestParam(required = false) String skillDesc,
            @RequestParam(required = false) String imageUrl,
            @RequestParam String action) {

        WeaponInfo weapon = new WeaponInfo();
        weapon.setId(id);
        weapon.setName(name);
        weapon.setRarity(rarity);
        weapon.setWeaponType(weaponType);
        weapon.setBaseAtk(baseAtk);
        weapon.setSubStatType(subStatType);
        weapon.setSubStatValue(subStatValue);
        weapon.setSkillName(skillName);
        weapon.setSkillDesc(skillDesc);
        weapon.setImageUrl(imageUrl);

        if ("add".equals(action)) {
            boolean success = weaponService.saveWeapon(weapon);
            return success ? Result.success("武器已添加") : Result.error("添加失败");
        } else if ("update".equals(action)) {
            boolean success = weaponService.updateWeapon(weapon);
            return success ? Result.success("武器已更新") : Result.error("更新失败");
        } else if ("delete".equals(action)) {
            boolean success = weaponService.deleteWeapon(id);
            return success ? Result.success("武器已删除") : Result.error("删除失败");
        }
        return Result.error("操作类型错误");
    }

    // ==========================================
    // 声骸管理
    // ==========================================
    @GetMapping("/echoes")
    @RequiresRole(1)
    public List<EchoInfo> getEchoes() {
        return echoService.getAllEchoes();
    }

    @PostMapping("/echo")
    @RequiresRole(1)
    public Result<String> saveOrUpdateEcho(
            @RequestParam(required = false) Integer id,
            @RequestParam String name,
            @RequestParam Integer cost,
            @RequestParam(required = false) String sonataEffect,
            @RequestParam(required = false) String skillDesc,
            @RequestParam(required = false) String imageUrl,
            @RequestParam String action) {

        EchoInfo echo = new EchoInfo();
        echo.setId(id);
        echo.setName(name);
        echo.setCost(cost);
        echo.setSonataEffect(sonataEffect);
        echo.setSkillDesc(skillDesc);
        echo.setImageUrl(imageUrl);

        if ("add".equals(action)) {
            boolean success = echoService.saveEcho(echo);
            return success ? Result.success("声骸已添加") : Result.error("添加失败");
        } else if ("update".equals(action)) {
            boolean success = echoService.updateEcho(echo);
            return success ? Result.success("声骸已更新") : Result.error("更新失败");
        } else if ("delete".equals(action)) {
            boolean success = echoService.deleteEcho(id);
            return success ? Result.success("声骸已删除") : Result.error("删除失败");
        }
        return Result.error("操作类型错误");
    }
}
