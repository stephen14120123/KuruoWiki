package com.wiki.entity;

import lombok.Data;

@Data
public class CharacterInfo {
    private Integer id;
    private String name;        // 角色名 (如：忌炎、散华)
    private Integer rarity;     // 稀有度 (4 或 5)
    private String element;     // 属性 (衍射、气动等)
    private String weaponType;  // 武器类型
    private String description; // 技能描述
    private String imageUrl;    // 立绘图片路径
    private Integer hp;
    private Integer atk;
    private Integer def;
    private Integer crit;   // 👈 从 critRate 改成 crit
    private Integer energy; // 👈 从 energyRegen 改成 energy
}
