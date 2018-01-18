package com.lark.middleware.mybatisgen;


/**
 * mybatisgen generate enums.
 *
 * @author xizhe.
 */
public class MyBatisGenConst {
    // jdbc result set metadata collumn name
    public static final String RSMD_COLUMN_NAME = "rsmdColumnName";
    public static final String RSMD_COLUMN_CLASS_NAME = "columnClassName";
    public static final String RSMD_COLUMN_TYPE_NAME = "columnTypeName";
    public static final String RSMD_COLUMN_PRECISION = "precision";
    public static final String RSMD_COLUMN_SCALE = "scale";
    public static final String RSMD_COLUMN_COMMENT = "comment";
    public static final String RSMD_IS_NULLABLE = "isNullable";

    // velocity param
    public static final String VP_TABLE_INFO = "tableInfo";
    public static final String VP_CLASS_NAME = "className";
    public static final String VP_PROPERTY_NAME="propertyName";


    public static final String VP_COLUMN_NAME = "columnName";
    public static final String VP_TABLE_NAME = "tableName";

    public static final String VP_TABLE_SCHEMA = "tableSchema";

    public static final String VP_MYBATIS_GEN_DIR="mybatisGenDir";

    //分库分表 表后缀regex
    public static final String SHARDING_SUFFIX_REG = "_[\\d]{4}";
}
