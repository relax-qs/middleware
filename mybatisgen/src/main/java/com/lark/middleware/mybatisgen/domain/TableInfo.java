package com.lark.middleware.mybatisgen.domain;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houenxun on 16/4/15.
 * 表信息
 */
public class TableInfo {
    private String name;
    private String schema;
    private List<ColumnInfo> columns;

    private Boolean isSharding;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public Boolean getSharding() {
        return isSharding;
    }

    public void setSharding(Boolean sharding) {
        isSharding = sharding;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * 返回主键列
     *
     * @return
     */
    public ColumnInfo getIdColumn() {
        return this.getColumnByTypeEnum(ColumnTypeEnum.id);
    }

    /**
     * 返回乐观锁
     *
     * @return
     */
    public ColumnInfo getVersionColumn() {
        return getColumnByTypeEnum(ColumnTypeEnum.version);
    }

    /**
     * 返回创建时间
     *
     * @return
     */
    public ColumnInfo getGmtCreateColumn() {
        return getColumnByTypeEnum(ColumnTypeEnum.gmtCreate);
    }

    /**
     * 返回创建时间
     *
     * @return
     */
    public ColumnInfo getGmtModifiedColumn() {
        return getColumnByTypeEnum(ColumnTypeEnum.gmtModified);
    }

    private ColumnInfo getColumnByTypeEnum(ColumnTypeEnum typeEnum) {
        if (CollectionUtils.isNotEmpty(columns)) {
            for (ColumnInfo columnInfo : columns) {
                if (typeEnum.getCode().equalsIgnoreCase(columnInfo.getType())) {
                    return columnInfo;
                }
            }
        }
        return null;
    }

    public void initColumnType() {
        for (ColumnInfo columnInfo : columns) {
            columnInfo.initColumnType();
        }

        ColumnInfo idColumn = this.getIdColumn();
        if (null == idColumn && CollectionUtils.isNotEmpty(columns)) {
            columns.get(0).setType(ColumnTypeEnum.id.getCode());
        }

    }

    /**
     * 获取根据类型过滤后的列
     *
     * @param types
     * @return
     */
    public List<ColumnInfo> getColumnFilterByType(String... types) {
        if (null == types || types.length == 0) {
            return this.columns;
        }
        List<ColumnInfo> list = new ArrayList<>();
        for (ColumnInfo columnInfo : columns) {
            boolean flag = false;
            for (String type : types) {
                if (type.equalsIgnoreCase(columnInfo.getType())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list.add(columnInfo);
            }
        }
        return list;
    }

}
