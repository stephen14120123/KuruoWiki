package com.wiki.controller;

import com.wiki.common.RequiresRole;
import com.wiki.common.Result;
import com.wiki.entity.EchoInfo;
import com.wiki.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/echoes")
public class EchoController {

    @Autowired
    private EchoService echoService;

    // 获取声骸列表
    @GetMapping
    public Result<List<EchoInfo>> getAll() {
        return Result.success(echoService.getAllEchoes());
    }

    // 后台新增声骸 - 需要管理员权限
    @PostMapping
    @RequiresRole(1)
    public Result<String> addEcho(@RequestBody EchoInfo echo) {
        return echoService.saveEcho(echo) ? Result.success("声骸添加成功") : Result.error("声骸添加失败");
    }

    // 后台修改声骸 - 需要管理员权限
    @PutMapping
    @RequiresRole(1)
    public Result<String> updateEcho(@RequestBody EchoInfo echo) {
        return echoService.updateEcho(echo) ? Result.success("声骸修改成功") : Result.error("声骸修改失败");
    }

    // 后台删除声骸 - 需要管理员权限
    @DeleteMapping("/{id}")
    @RequiresRole(1)
    public Result<String> deleteEcho(@PathVariable Integer id) {
        return echoService.deleteEcho(id) ? Result.success("声骸删除成功") : Result.error("声骸删除失败");
    }
}
