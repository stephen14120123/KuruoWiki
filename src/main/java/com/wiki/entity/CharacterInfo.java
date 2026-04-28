package com.wiki.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CharacterInfo {
    @ExcelProperty("角色ID")
    private Integer id;

    @ExcelProperty("角色名")
    private String name;

    @ExcelProperty("稀有度")
    private Integer rarity;

    @ExcelProperty("属性")
    private String element;

    @ExcelProperty("武器类型")
    private String weaponType;

    @ExcelProperty("技能描述")
    private String description;

    @ExcelProperty("立绘路径")
    private String imageUrl;

    @ExcelProperty("生命值")
    private Integer hp;

    @ExcelProperty("攻击力")
    private Integer atk;

    @ExcelProperty("防御力")
    private Integer def;

    @ExcelProperty("暴击率")
    private Integer crit;

    @ExcelProperty("能量")
    private Integer energy;
}