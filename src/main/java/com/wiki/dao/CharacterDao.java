package com.wiki.dao;

import com.wiki.entity.CharacterInfo;
import com.wiki.utils.DBUtil;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
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
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM character_info";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CharacterInfo character = new CharacterInfo();
                character.setId(rs.getInt("id"));
                character.setName(rs.getString("name"));
                character.setRarity(rs.getInt("rarity"));
                character.setElement(rs.getString("element"));
                character.setWeaponType(rs.getString("weapon_type"));
                character.setDescription(rs.getString("description"));
                character.setImageUrl(rs.getString("image_url"));
                character.setHp(rs.getInt("hp"));
                character.setAtk(rs.getInt("atk"));
                character.setDef(rs.getInt("def"));
                character.setCrit(rs.getInt("crit"));
                character.setEnergy(rs.getInt("energy"));
                list.add(character);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            String sql = "SELECT * FROM character_info WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
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
            StringBuilder sql = new StringBuilder("SELECT * FROM character_info WHERE 1=1");

            if (element != null && !element.isEmpty() && !"全部".equals(element)) {
                sql.append(" AND element = ?");
            }
            if (rarity != null && rarity != 0) {
                sql.append(" AND rarity = ?");
            }

            pstmt = conn.prepareStatement(sql.toString());

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
    // 新增：后台管理员增删改查方法
    // ==========================================

    /**
     * 【后台管理】添加新角色
     */
    public boolean addCharacter(CharacterInfo c) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO character_info (name, rarity, element, weapon_type, description, image_url, hp, atk, def, crit, energy) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, c.getName());
            pstmt.setInt(2, c.getRarity());
            pstmt.setString(3, c.getElement());
            pstmt.setString(4, c.getWeaponType());
            pstmt.setString(5, c.getDescription());
            pstmt.setString(6, c.getImageUrl());
            pstmt.setInt(7, c.getHp() != null ? c.getHp() : 0);
            pstmt.setInt(8, c.getAtk() != null ? c.getAtk() : 0);
            pstmt.setInt(9, c.getDef() != null ? c.getDef() : 0);
            pstmt.setInt(10, c.getCrit() != null ? c.getCrit() : 0);
            pstmt.setInt(11, c.getEnergy() != null ? c.getEnergy() : 0);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    /**
     * 【后台管理】更新角色信息
     */
    public boolean updateCharacter(CharacterInfo c) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE character_info SET name=?, rarity=?, element=?, weapon_type=?, description=?, image_url=?, hp=?, atk=?, def=?, crit=?, energy=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, c.getName());
            pstmt.setInt(2, c.getRarity());
            pstmt.setString(3, c.getElement());
            pstmt.setString(4, c.getWeaponType());
            pstmt.setString(5, c.getDescription());
            pstmt.setString(6, c.getImageUrl());
            pstmt.setInt(7, c.getHp() != null ? c.getHp() : 0);
            pstmt.setInt(8, c.getAtk() != null ? c.getAtk() : 0);
            pstmt.setInt(9, c.getDef() != null ? c.getDef() : 0);
            pstmt.setInt(10, c.getCrit() != null ? c.getCrit() : 0);
            pstmt.setInt(11, c.getEnergy() != null ? c.getEnergy() : 0);
            pstmt.setInt(12, c.getId());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    /**
     * 【后台管理】删除角色
     */
    public boolean deleteCharacter(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM character_info WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }
}