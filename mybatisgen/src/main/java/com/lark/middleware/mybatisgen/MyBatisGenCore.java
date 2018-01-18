package com.lark.middleware.mybatisgen;

import com.lark.middleware.mybatisgen.config.Config;
import com.lark.middleware.mybatisgen.config.DbConfig;
import com.lark.middleware.mybatisgen.config.Generate;
import com.lark.middleware.mybatisgen.domain.ColumnInfo;
import com.lark.middleware.mybatisgen.domain.TableInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * mybatisgen generate core.
 *
 * @author xizhe.
 */
public class MyBatisGenCore {

    /**
     * 根据表名获取字段信息
     *
     * @param cn
     * @param table
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getColInfoList1(Connection cn, String table) throws Exception {
        String sql = "select * from " + table + " where 1>2";

        try (Statement stmt = cn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // 获取结果集元数据信息
            ResultSetMetaData rsmd = rs.getMetaData();
            int num = rsmd.getColumnCount();
            Map<String, String> map;
            List<Map<String, String>> list = new ArrayList<>();
            for (int i = 1; i <= num; i++) {
                map = new HashMap<>();
                map.put(MyBatisGenConst.RSMD_COLUMN_NAME, rsmd.getColumnName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_CLASS_NAME, rsmd.getColumnClassName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_TYPE_NAME, rsmd.getColumnTypeName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_PRECISION, rsmd.getPrecision(i) + "");
                map.put(MyBatisGenConst.RSMD_COLUMN_SCALE, rsmd.getScale(i) + "");
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            throw new Exception(e + ",table=" + table, e);
        }
    }


    /**
     * 根据表名获取字段信息
     *
     * @param cn
     * @param table
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getColInfoList(Connection cn, String schema, String table) throws Exception {

        String sql1 = "select * from " + schema + "." + table + " where 1>2";
        String sql2 = "select * from information_schema.columns where TABLE_NAME  = '" + table + "' and TABLE_SCHEMA='" + schema + "' order by ORDINAL_POSITION ";

        //Statement stmt = null;
        try (Statement stmt1 = cn.createStatement();
             ResultSet rs1 = stmt1.executeQuery(sql1);
             Statement stmt2 = cn.createStatement();
             ResultSet rs2 = stmt2.executeQuery(sql2);
        ) {

            // 获取结果集元数据信息
            ResultSetMetaData rsmd = rs1.getMetaData();
            List<Map<String, String>> list = new ArrayList<>();
            int i = 1;
            while (rs2.next()) {
                Map<String, String> map = new HashMap<>();
                map.put(MyBatisGenConst.RSMD_COLUMN_NAME, rsmd.getColumnName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_CLASS_NAME, rsmd.getColumnClassName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_TYPE_NAME, rsmd.getColumnTypeName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_PRECISION, rsmd.getPrecision(i) + "");
                map.put(MyBatisGenConst.RSMD_COLUMN_SCALE, rsmd.getScale(i) + "");
                map.put(MyBatisGenConst.RSMD_COLUMN_COMMENT, rs2.getString("COLUMN_COMMENT"));
                map.put(MyBatisGenConst.RSMD_IS_NULLABLE, rs2.getString("IS_NULLABLE"));
                list.add(map);
                i++;
            }

            return list;
        } catch (Exception e) {
            throw new Exception(e + ",table=" + table, e);
        }
    }

    /**
     * 获取列信息。
     *
     * @param table
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getColInfoList(Config config, String table) throws Exception {
        Connection cn = getConnection(config.getDbConfig());
        try {
            return getColInfoList(cn, config.getDbConfig().getSchema(), table);
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 获取参数列表
     *
     * @param colInfoList
     * @return
     * @throws Exception
     */
    public static List<ColumnInfo> makeColunmInfoList(List<Map<String, String>> colInfoList) throws Exception {
        List<ColumnInfo> list = new ArrayList<>();
        for (Map<String, String> map : colInfoList) {
            String columnName = map.get(MyBatisGenConst.RSMD_COLUMN_NAME);

            String columnClassName = map.get(MyBatisGenConst.RSMD_COLUMN_CLASS_NAME);
            String columnTypeName = map.get(MyBatisGenConst.RSMD_COLUMN_TYPE_NAME);


            String scaleStr = map.get(MyBatisGenConst.RSMD_COLUMN_SCALE);
            int scale = NumberUtils.toInt(scaleStr);
            String precisionStr = map.get(MyBatisGenConst.RSMD_COLUMN_PRECISION);
            int precision = NumberUtils.toInt(precisionStr);
            String javaType = getJavaType(columnClassName, columnTypeName, scale, precision);
            String jdbcType = getJdbcType(columnClassName, columnTypeName);
            String propName = getPropName(columnName);
            String setMethod = getSetMethod(propName);
            String getMethod = getGetMethod(propName);

            String nullAbleStr = map.get(MyBatisGenConst.RSMD_IS_NULLABLE);
            Boolean isNullable = false;
            if (null != nullAbleStr) {
                if (nullAbleStr.equalsIgnoreCase("YES") || nullAbleStr.equalsIgnoreCase("true") || nullAbleStr.equalsIgnoreCase("是")) {
                    isNullable = true;
                }
            }
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(columnName.toLowerCase());
            columnInfo.setPropName(propName);
            columnInfo.setJavaType(javaType);
            columnInfo.setJdbcType(jdbcType);
            columnInfo.setGetMethod(getMethod);
            columnInfo.setSetMethod(setMethod);
            columnInfo.setComment(map.get(MyBatisGenConst.RSMD_COLUMN_COMMENT));
            columnInfo.setNullable(isNullable);
            columnInfo.setLength(precision);


            list.add(columnInfo);
        }
        return list;
    }

    /**
     * 获取字段的java类型
     *
     * @param columnClassName 字段类名
     * @param columnTypeName  字段类型名称
     * @param scale           精度 小数位数
     * @return
     */
    public static String getJavaType(String columnClassName, String columnTypeName, int scale, int precision) {
        if (columnClassName.equals("java.sql.Timestamp")) {
            return "Date";
        }
        if (columnClassName.equals("java.lang.String")) {
            return "String";
        }
        if (columnTypeName.equals("DECIMAL") && scale < 1) {
            return "Long";
        }
        if (columnTypeName.equals("DECIMAL") && scale > 0) {
            return "java.math.BigDecimal";
        }
        if (columnTypeName.startsWith("BIGINT")) {
            return "Long";
        }
        if (columnTypeName.startsWith("INT")) {
            return "Integer";
        }
        if (columnTypeName.startsWith("TINYINT") && precision == 1) {
            return "Boolean";
        }
        if (columnTypeName.startsWith("TINYINT") && precision != 1) {
            return "Integer";
        }
        if (columnTypeName.startsWith("SMALLINT")) {
            return "Integer";
        }
        return columnClassName;
    }

    /**
     * 获取jdbc类型
     *
     * @param columnClassName 字段类名
     * @param columnTypeName  字段类型名称
     * @return
     */
    public static String getJdbcType(String columnClassName, String columnTypeName) {
        if (columnClassName.equals("java.lang.String")) {
            return "VARCHAR";
        }
        if (columnClassName.startsWith("java.sql.")) {
            return "TIMESTAMP";
        }
        if (columnTypeName.startsWith("NUMBER")) {
            return "DECIMAL";
        }
        if (columnTypeName.startsWith("INT")) {
            return "INTEGER";
        }
        return columnTypeName;
    }

    /**
     * 根据表名获取java类型
     *
     * @param tableName 表名
     * @return
     */
    public static String getClassName(String tablePrefix, String tableName) {
        String t = tableName.toLowerCase();
        if (StringUtils.isNotBlank(tablePrefix))
            t = t.replace(tablePrefix.trim(), "");
        String[] arr = t.split("_");
        int num = arr.length;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            s.append(StringUtils.capitalize(arr[i]));
        }
        return s.toString();
    }

    /**
     * 根据字段名获取java数据对象属性名
     *
     * @param columnName 字段名
     * @return
     */
    public static String getPropName(String columnName) {
        String t = columnName.toLowerCase();
        String[] arr = t.split("_");
        int num = arr.length;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            if (i > 0) {
                s.append(StringUtils.capitalize(arr[i]));
            } else {
                s.append(arr[i]);
            }
        }
        return s.toString();
    }

    public static String getSetMethod(String propName) {
        return "set" + StringUtils.capitalize(propName);
    }

    public static String getGetMethod(String propName) {
        return "get" + StringUtils.capitalize(propName);
    }

    public static String getColsStr(List<Map<String, String>> list) {
        int num = list.size();
        Map<String, String> map;
        String colName;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            map = list.get(i);
            colName = map.get(MyBatisGenConst.VP_COLUMN_NAME);
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(colName);
        }
        return stringBuilder.toString();
    }

    /**
     * velocity模板合并
     *
     * @param template 模板字符串 如 hello,${name}
     * @param paramMap 参数
     * @return
     * @throws Exception
     */
    public static String merge(String template, Map<String, Object> paramMap) throws Exception {
        VelocityContext vc = new VelocityContext(paramMap);
        StringWriter writer = new StringWriter();
        Velocity.evaluate(vc, writer, "mybatis_code_gen", template);
        return writer.getBuffer().toString();
    }


    /**
     * 根据表名生成java数据对象类文件和sqlmap文件
     *
     * @param table 表名
     * @throws Exception
     */
    public static void gen(Config config, String table) throws Exception {
        List<Map<String, String>> colInfoList = getColInfoList(config, table);
        List<ColumnInfo> columnInfos = makeColunmInfoList(colInfoList);

        boolean isSharding = Pattern.compile(MyBatisGenConst.SHARDING_SUFFIX_REG).matcher(table).find();

        if (isSharding) {
            // 去掉分库分表后面的表后缀，如_0001
            table = table.replaceAll(MyBatisGenConst.SHARDING_SUFFIX_REG, "");
        }


        String className = getClassName(config.getDbConfig().getTablePrefix(), table);

        String propertyName = className.substring(0, 1).toLowerCase() + className.substring(1);

        String vpTableName = table.toLowerCase();
        if (isSharding) {
            vpTableName += "_$tabNum$";
        }


        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(vpTableName);
        tableInfo.setSchema(config.getDbConfig().getSchema());
        tableInfo.setColumns(columnInfos);
        tableInfo.setSharding(isSharding);
        tableInfo.initColumnType();

        config.getParams().put(MyBatisGenConst.VP_CLASS_NAME, className);
        config.getParams().put(MyBatisGenConst.VP_PROPERTY_NAME, propertyName);
        config.getParams().put(MyBatisGenConst.VP_TABLE_NAME, vpTableName);

        config.getParams().put(MyBatisGenConst.VP_TABLE_SCHEMA, tableInfo.getSchema());
        config.getParams().put(MyBatisGenConst.VP_TABLE_INFO, tableInfo);

        for (Generate generate : config.getGenerates()) {
            gen(config, generate);
        }


    }

    public static void gen(Config config, Generate generate) throws Exception {
        // 创建策略
        CreateStrategyEnum strategy = CreateStrategyEnum.valueOf(generate.getCreateStrategy());
        // 不需要创建直接忽略
        if (strategy == CreateStrategyEnum.never) {
            return;
        }
        URL url = ResourceUtils.getURL(generate.getTemplate());
        String template = IOUtils.toString(url);

        //String template = FileUtils.readFileToString(new File(ClassLoader.getSystemResource(generate.getTemplate()).getPath()));

        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(config.getParams());
        param.putAll(generate.getParams());
        String result = merge(template, param);
        /**
         * 生成目标文件目录
         */
        String targetFileName = merge(generate.getTarget(), param);

        String mybatisGenDir = (String) param.get(MyBatisGenConst.VP_MYBATIS_GEN_DIR);
        String baseDir = System.getProperty("user.dir") + File.separator;
        if (StringUtils.isNotBlank(mybatisGenDir)) {
            baseDir = baseDir.replace(mybatisGenDir, "");
        }

        File targetFile = new File(baseDir + targetFileName);
        /**
         * 检测父目录并创建
         */
        File dirFile = targetFile.getParentFile();
        if (!dirFile.exists()) {
            if (dirFile.mkdirs()) {
                System.out.println(String.format("createDir %s success", dirFile.getPath()));
            } else {
                System.out.println(String.format("createDir %s error", dirFile.getPath()));
                return;
            }
        }
        /**
         * 创建目标文件
         */
        if (!targetFile.exists()) {
            boolean success = targetFile.createNewFile();
            if (!success) {
                System.out.println(String.format("createNewFile %s error", targetFileName));
            } else {
                System.out.println(String.format("createNewFile %s success", targetFileName));
            }
            FileUtils.writeStringToFile(targetFile, result);
        } else {
            if (strategy == CreateStrategyEnum.always) {
                FileUtils.writeStringToFile(targetFile, result);
                System.out.println(String.format("overwrite %s success", targetFileName));
            } else {
                System.out.println(String.format("overwrite %s ignore", targetFileName));
            }
        }


    }

    /**
     * 清理目标文件
     *
     * @param config
     * @param generate
     * @throws Exception
     */
    public static void clear(Config config, Generate generate) throws Exception {

        //String template = FileUtils.readFileToString(new File(ClassLoader.getSystemResource(generate.getTemplate()).getPath()));

        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(config.getParams());
        param.putAll(generate.getParams());
        /**
         * 生成目标文件文件路径
         */
        String targetFileName = merge(generate.getTarget(), param);

        String mybatisGenDir = (String) param.get(MyBatisGenConst.VP_MYBATIS_GEN_DIR);
        String baseDir = System.getProperty("user.dir") + File.separator;
        if (StringUtils.isNotBlank(mybatisGenDir)) {
            baseDir = baseDir.replace(mybatisGenDir, "");
        }

        File targetFile = new File(baseDir + targetFileName);

        /**
         * 删除目标文件
         */
        if (targetFile.exists()) {

            boolean success = targetFile.delete();
            if (!success) {
                System.out.println(String.format("delete %s error", targetFileName));
            } else {
                System.out.println(String.format("delete %s success", targetFileName));
            }
        } else {
            System.out.println(String.format("file %s not exist", targetFileName));
        }


    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static Connection getConnection(DbConfig config) throws ClassNotFoundException, SQLException, FileNotFoundException,
            IOException {

        Class.forName(config.getDriver());
        String url = config.getUrl();
        String user = config.getUser();
        String psw = config.getPwd();
        return DriverManager.getConnection(url, user, psw);
    }

    /**
     * 批量生成java数据对象类文件和sqlmap文件
     *
     * @param config
     * @throws Exception
     */
    public static void batchGen(Config config) throws Exception {
        System.out.println("table num " + config.getTables().size());
        Connection cn = getConnection(config.getDbConfig());
        try {
            for (String table : config.getTables()) {
                MyBatisGenCore.gen(config, table.trim());
                System.out.println(table.trim() + " done");
            }
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
            }
        }
    }


    /**
     * 批量生成java数据对象类文件和sqlmap文件
     *
     * @param config
     * @throws Exception
     */
    public static void batchClear(Config config) throws Exception {
        System.out.println("table num " + config.getTables().size());
        Connection cn = getConnection(config.getDbConfig());
        try {
            for (String table : config.getTables()) {
                MyBatisGenCore.clear(config, table.trim());
                System.out.println(table.trim() + " done");
            }
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 清理文件
     *
     * @param config
     * @param table
     * @throws Exception
     */
    public static void clear(Config config, String table) throws Exception {
        List<Map<String, String>> colInfoList = getColInfoList(config, table);
        List<ColumnInfo> columnInfos = makeColunmInfoList(colInfoList);

        boolean isSharding = Pattern.compile(MyBatisGenConst.SHARDING_SUFFIX_REG).matcher(table).find();

        if (isSharding) {
            // 去掉分库分表后面的表后缀，如_0001
            table = table.replaceAll(MyBatisGenConst.SHARDING_SUFFIX_REG, "");
        }


        String className = getClassName(config.getDbConfig().getTablePrefix(), table);

        String propertyName = className.substring(0, 1).toLowerCase() + className.substring(1);

        String vpTableName = table.toLowerCase();
        if (isSharding) {
            vpTableName += "_$tabNum$";
        }


        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(vpTableName);
        tableInfo.setSchema(config.getDbConfig().getSchema());
        tableInfo.setColumns(columnInfos);
        tableInfo.setSharding(isSharding);
        tableInfo.initColumnType();

        config.getParams().put(MyBatisGenConst.VP_CLASS_NAME, className);
        config.getParams().put(MyBatisGenConst.VP_PROPERTY_NAME, propertyName);
        config.getParams().put(MyBatisGenConst.VP_TABLE_NAME, vpTableName);

        config.getParams().put(MyBatisGenConst.VP_TABLE_SCHEMA, tableInfo.getSchema());
        config.getParams().put(MyBatisGenConst.VP_TABLE_INFO, tableInfo);

        for (Generate generate : config.getGenerates()) {
            clear(config, generate);
        }


    }
}
