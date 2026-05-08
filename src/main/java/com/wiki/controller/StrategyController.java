package com.wiki.controller;

import com.wiki.common.Result;
import com.wiki.entity.StrategyGuide;
import com.wiki.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/strategies")
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    // 1. 获取指定角色的攻略列表 (GET /api/strategies?characterId=1)
    @GetMapping
    public Result<List<StrategyGuide>> getList(@RequestParam Integer characterId) {
        return Result.success(strategyService.getByCharacterId(characterId));
    }

    // 2. 发布新攻略 (POST /api/strategies)
    @PostMapping
    public Result<String> add(@RequestBody StrategyGuide strategy, HttpServletRequest request) {
        // 从拦截器存放的 Request 属性中提取当前登录人的 userId
        Integer currentUserId = (Integer) request.getAttribute("currentUserId");
        strategy.setUserId(currentUserId);

        return strategyService.addStrategy(strategy) ?
                Result.success("攻略已同步至终端") : Result.error("同步失败");
    }

    // 3. 编辑攻略 (PUT /api/strategies) - 只能编辑自己的攻略
    @PutMapping
    public Result<String> update(@RequestBody StrategyGuide strategy, HttpServletRequest request) {
        Integer currentUserId = (Integer) request.getAttribute("currentUserId");
        StrategyGuide oldStrategy = strategyService.getById(strategy.getId());

        if (oldStrategy == null) return Result.error("记录不存在");

        // 安全防线：只有发帖人自己才能编辑
        if (!oldStrategy.getUserId().equals(currentUserId)) {
            return Result.error("越权操作：只能编辑自己的攻略");
        }

        return strategyService.updateStrategy(strategy) ?
                Result.success("攻略已更新") : Result.error("更新失败");
    }

    // 4. 删除攻略 (DELETE /api/strategies/1)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id, HttpServletRequest request) {
        Integer currentUserId = (Integer) request.getAttribute("currentUserId");
        StrategyGuide oldStrategy = strategyService.getById(id);

        if (oldStrategy == null) return Result.error("记录不存在");

        // 安全防线：只有发帖人自己才能删除
        if (!oldStrategy.getUserId().equals(currentUserId)) {
            return Result.error("越权操作：终端拒绝抹除他人记录");
        }

        return strategyService.removeStrategy(id) ?
                Result.success("记录已永久抹除") : Result.error("操作失败");
    }
}
