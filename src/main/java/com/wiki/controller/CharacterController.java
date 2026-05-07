package com.wiki.controller;

import com.wiki.common.Result;
import com.wiki.entity.CharacterInfo;
import com.wiki.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色模块的现代 RESTful 接口
 */
@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    // 1. 获取所有角色 (GET /api/characters)
    @GetMapping
    public Result<List<CharacterInfo>> getAll() {
        return Result.success(characterService.getAllCharacters());
    }

    // 2. 获取单个角色详情 (GET /api/characters/1)
    @GetMapping("/{id}")
    public Result<CharacterInfo> getDetail(@PathVariable Integer id) {
        CharacterInfo character = characterService.getCharacterById(id);
        return character != null ? Result.success(character) : Result.error("未找到该角色");
    }

    // 3. 后台：新增角色 (POST /api/characters)
    @PostMapping
    public Result<String> addCharacter(@RequestBody CharacterInfo character) {
        boolean success = characterService.saveCharacter(character);
        return success ? Result.success("添加成功") : Result.error("添加失败");
    }

    // 4. 后台：修改角色 (PUT /api/characters)
    @PutMapping
    public Result<String> updateCharacter(@RequestBody CharacterInfo character) {
        boolean success = characterService.updateCharacter(character);
        return success ? Result.success("修改成功") : Result.error("修改失败");
    }

    // 5. 后台：删除角色 (DELETE /api/characters/1)
    @DeleteMapping("/{id}")
    public Result<String> deleteCharacter(@PathVariable Integer id) {
        boolean success = characterService.deleteCharacter(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}