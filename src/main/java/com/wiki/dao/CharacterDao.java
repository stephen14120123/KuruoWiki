package com.wiki.dao;

import com.wiki.entity.CharacterInfo;
import com.wiki.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CharacterDao {

    /**
     * 查询所有游戏角色数据
     * @return 包含多个角色的 List 集合
     */
    public List<CharacterInfo> getAllCharacters() {
        List<CharacterInfo> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. 获取数据库连接
            conn = DBUtil.getConnection();

            // 2. 编写 SQL 语句 (查询角色表的所有数据)
            String sql = "SELECT * FROM character_info";

            // 3. 预编译 SQL 语句
            pstmt = conn.prepareStatement(sql);

            // 4. 执行查询，得到结果集
            rs = pstmt.executeQuery();

            // 5. 循环解析结果集，把数据库里的每一行变成 Java 里的 CharacterInfo 对象
            while (rs.next()) {
                CharacterInfo character = new CharacterInfo();
                character.setId(rs.getInt("id"));
                character.setName(rs.getString("name"));
                character.setRarity(rs.getInt("rarity"));
                character.setElement(rs.getString("element"));
                character.setWeaponType(rs.getString("weapon_type"));
                character.setDescription(rs.getString("description"));
                character.setImageUrl(rs.getString("image_url"));
                // 在 getAllCharacters 方法的 while 循环里加上：
                character.setHp(rs.getInt("hp"));
                character.setAtk(rs.getInt("atk"));
                character.setDef(rs.getInt("def"));
                character.setCrit(rs.getInt("crit"));
                character.setEnergy(rs.getInt("energy"));
                // 把装好数据的对象塞进集合里
                list.add(character);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. 释放资源 (用完一定要关门)
            DBUtil.close(conn, pstmt, rs);
        }

        return list;
    }
    /**
     * 根据 ID 查询单个角色的详细档案
     */
    public CharacterInfo getCharacterById(int id) {
        CharacterInfo charInfo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            // 这里的 SQL 已经包含了我们刚才新增的 5 个数值字段
            String sql = "SELECT * FROM character_info WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id); // 把传进来的 ID 塞进占位符
            rs = pstmt.executeQuery();

            if (rs.next()) {
                charInfo = new CharacterInfo();
                charInfo.setId(rs.getInt("id"));
                charInfo.setName(rs.getString("name"));
                charInfo.setRarity(rs.getInt("rarity"));
                charInfo.setElement(rs.getString("element"));
                charInfo.setWeaponType(rs.getString("weapon_type"));
                charInfo.setDescription(rs.getString("description"));
                charInfo.setImageUrl(rs.getString("image_url"));
                // 填入新增的战斗数值
                charInfo.setHp(rs.getInt("hp"));
                charInfo.setAtk(rs.getInt("atk"));
                charInfo.setDef(rs.getInt("def"));
                charInfo.setCrit(rs.getInt("crit"));
                charInfo.setEnergy(rs.getInt("energy"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return charInfo;
    }


    /**
     * 根据属性和星级筛选角色
     */
    public List<CharacterInfo> getCharactersByFilter(String element, Integer rarity) {
        List<CharacterInfo> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            // 1. 基础 SQL，用 1=1 是为了后面方便动态拼接 AND 条件
            StringBuilder sql = new StringBuilder("SELECT * FROM character_info WHERE 1=1");

            // 2. 动态判断参数
            if (element != null && !element.isEmpty() && !"全部".equals(element)) {
                sql.append(" AND element = ?");
            }
            if (rarity != null && rarity != 0) {
                sql.append(" AND rarity = ?");
            }

            pstmt = conn.prepareStatement(sql.toString());

            // 3. 按顺序给占位符 ? 赋值
            int paramIndex = 1;
            if (element != null && !element.isEmpty() && !"全部".equals(element)) {
                pstmt.setString(paramIndex++, element);
            }
            if (rarity != null && rarity != 0) {
                pstmt.setInt(paramIndex++, rarity);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                CharacterInfo c = new CharacterInfo();
                // ... 复制你之前的解析逻辑（id, name, element, rarity, 以及那5个战斗数值）
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setElement(rs.getString("element"));
                c.setRarity(rs.getInt("rarity"));
                c.setImageUrl(rs.getString("image_url"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // ==========================================
    // 测试方法：看看能不能把数据查出来
    // ==========================================
    public static void main(String[] args) {
        CharacterDao dao = new CharacterDao();
        List<CharacterInfo> characters = dao.getAllCharacters();

        System.out.println("====== 从数据库查询到的角色图鉴 ======");
        for (CharacterInfo c : characters) {
            System.out.println("角色名: " + c.getName() +
                    " | 星级: " + c.getRarity() + "星" +
                    " | 属性: " + c.getElement() +
                    " | 武器: " + c.getWeaponType());
            System.out.println("技能简介: " + c.getDescription());
            System.out.println("------------------------------------");
        }
    }
}