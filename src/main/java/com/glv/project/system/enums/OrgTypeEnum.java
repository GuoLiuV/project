package com.glv.project.system.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织类型
 * @author ZHOUXIANG
 */
public enum OrgTypeEnum {

    /**
     * 顶层组织，集团/公司
     * 该租户下的所有操作数据记录都需要与租户ID关联。
     */
    COMPANY("总公司"),

    /**
     * 集团下的分公司，
     * 只能建在租户下面，即二级组织
     */
    BRANCH("分公司"),

    /**
     * 分公司下的部门，只能建在分公司或部门（子部门）组织下，
     * 属于三级组织。
     */
    DEPARTMENT("部门"),

    /**
     * 部门下的小组，只能建在部门组织下，
     * 属于四级组织
     */
    GROUP("小组"),

    /**
     * 权限组，该类型组织用户可以与以
     * 上类型组织共享，以授于特殊权限，
     * 只能建在租户下面。
     */
    AUTH_GROUP("特别小组"),

    /**
     * 未分组用户，只能在租户下，
     * 自动创建，不能删除。
     * 一个租户下有且仅有一个
     */
    NON_GROUP("独立用户");

    /**
     * 组织类型别名
     */
    private String alias;

    OrgTypeEnum(String alias) {
        this.alias = alias;
    }

    /**
     * 获取alias
     * @param name 代码
     * @return 名称
     */
    public static String getAlias(String name) {
        return OrgTypeEnum.valueOf(name).alias;
    }

    /**
     * 返回列表
     * @return 列表
     */
    public static List<Map<String, String>> orgTypes() {
        List<Map<String, String>> list = new ArrayList<>();
        for (OrgTypeEnum value : OrgTypeEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("code", value.name());
            map.put("alias", value.alias);
            list.add(map);
        }
        return list;
    }
}
