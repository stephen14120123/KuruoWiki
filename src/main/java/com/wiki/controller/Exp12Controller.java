package com.wiki.controller;

import com.alibaba.excel.EasyExcel;
import com.wiki.entity.CharacterInfo;
import com.wiki.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 实验十二：文件上传下载、数据导出与外部编辑器支持
 */
@RestController
@RequestMapping("/api/exp12")
public class Exp12Controller {

    @Autowired
    private CharacterService characterService;

    // ==========================================
    // 1. 文件上传操作 (兼顾外部编辑器图片上传)
    // ==========================================
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) return "上传失败：文件为空";
        try {
            // 将文件保存到项目根目录下的 uploads 文件夹中
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            return "文件上传成功！保存在：" + filePath;
        } catch (IOException e) {
            return "上传异常：" + e.getMessage();
        }
    }

    // ==========================================
    // 2. 文件下载操作
    // ==========================================
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        String filePath = System.getProperty("user.dir") + "/uploads/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) return ResponseEntity.notFound().build();

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // ==========================================
    // 3. 数据导出操作 (一键导出角色图鉴为 Excel)
    // ==========================================
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 设置响应头，告诉浏览器这是一个 Excel 文件下载
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("KuruoWiki_角色图鉴数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 查询数据库中的所有角色，并写入 Excel
        List<CharacterInfo> list = characterService.getAllCharacters();
        EasyExcel.write(response.getOutputStream(), CharacterInfo.class).sheet("角色列表").doWrite(list);
    }
}