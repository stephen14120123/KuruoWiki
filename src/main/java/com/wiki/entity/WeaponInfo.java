package com.wiki.entity;

import lombok.Data;

@Data
public class WeaponInfo {
    private Integer id;
    private String name;
    private Integer rarity;
    private String weaponType;
    private Integer baseAtk;
    private String subStatType;
    private String subStatValue;
    private String skillName;
    private String skillDesc;
    private String imageUrl;
}