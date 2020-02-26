package com.why.code.codegenerate;

import com.why.code.codegenerate.model.ColumnClass;
import com.why.code.codegenerate.model.DatasourceInfo;
import com.why.code.codegenerate.utils.DateUtils;
import com.why.code.codegenerate.utils.FreeMarkerTemplateUtils;
import com.why.code.codegenerate.utils.GeneUtils;
import freemarker.template.Template;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

@Service
public class CodeGenerateUtils {
    //@Value("${spring.datasource.primary.driver-class-name}")
    private String driver;
    //@Value("${spring.datasource.primary.url}")
    private String url;
    //@Value("${spring.datasource.primary.username}")
    private String user;
    //@Value("${spring.datasource.primary.password}")
    private String password;
    private final String AUTHOR = "why";
    private final String CURRENT_DATE = DateUtils.convertDateToStr(new Date(),DateUtils.HHMMSS_FORMAT);
    private String tableName;
    private String packageName;
    private String tableAnnotation;
    private String URL;
    private String USER;
    private String PASSWORD;
    private String DRIVER;
    private String diskPath;
    private String changeTableName;

    /**
     * 说明：该方法适用于spring boot项目方法是在项目启动情况下调用，会调用application里面的数据库配置的相关信息
     *
     * @param tableName   表名 如user表
     * @param basePath    需要生成包的路径 如user包需要生成在下面的包中
     *                    "D:\testproject\back\src\main\java\com\why\greenhouse\back";
     * @param basePackage 基础报名 如user包在
     *                    com.why.greenhouse.back下
     * @return
     */
    public CodeGenerateUtils buildCodeGenerateUtils(String tableName, String basePath, String basePackage) {
        this.tableName = tableName;
        this.tableAnnotation = tableName;
        this.changeTableName = GeneUtils.replaceUnderLineAndUpperCase(tableName);
        this.URL = url;
        this.USER = user;
        this.PASSWORD = password;
        this.DRIVER = driver;
        this.diskPath = basePath + "\\" + changeTableName.toLowerCase() + "\\";
        this.packageName = basePackage + "." + changeTableName.toLowerCase();
        return this;
    }

    /**
     * 说明：该方法可以在不用启动项目的情况调用，需要传送数据库的配置信息
     *
     * @param tableName   表名 如user表
     * @param basePath    需要生成包的路径 如user包需要生成在下面的包中
     *                    "D:\testproject\back\src\main\java\com\why\greenhouse\back";
     * @param basePackage 基础报名 如user包在
     *                    com.why.greenhouse.back下
     * @param dtInfo      基础报名 如user包在
     *                    com.why.greenhouse.back下
     * @return 返回CodeGenerateUtils对象
     */
    public CodeGenerateUtils buildGene(String tableName, String basePath, String basePackage, DatasourceInfo dtInfo) {
        this.tableName = tableName;
        this.tableAnnotation = tableName;
        this.changeTableName = GeneUtils.replaceUnderLineAndUpperCase(tableName);
        this.URL = dtInfo.getURL();
        this.USER = dtInfo.getUSER();
        this.PASSWORD = dtInfo.getPASSWORD();
        this.DRIVER = dtInfo.getDRIVER();
        this.diskPath = basePath + File.separator + changeTableName.toLowerCase() + File.separator;
        this.packageName = basePackage + "." + changeTableName.toLowerCase();
        return this;
    }

    public Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }

    public void generate() throws Exception {
        try {
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null, "%", tableName, "%");
            //生成服务层接口文件
            generateServiceInterfaceFile();
            //生成服务实现层文件
            generateServiceImplFile();
            //生成Controller层文件
            generateControllerFile();
            //生成与数据库对应的实体类
            generateEntityFile(resultSet);
            //生成前端需要传送的request和response
            generateRequestFile();
            generateResponseFile();
            generateDaoFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    /**
     * 根据数据库对象创建java实体类对象
     *
     * @param resultSet 查询数据库返回对象
     * @throws Exception 抛出异常
     */
    private void generateEntityFile(ResultSet resultSet) throws Exception {

        final String suffix = ".java";
        final String pagePath = diskPath + "\\entity\\";
        File pageFile = new File(pagePath);
        if (!pageFile.exists()) {
            pageFile.mkdirs();
        }
        final String path = diskPath + "\\entity\\" + changeTableName + suffix;
        final String templateName = "Entity.ftl";
        File mapperFile = new File(path);
        List<ColumnClass> columnClassList = new ArrayList<>();
        ColumnClass columnClass = null;
        while (resultSet.next()) {
            //id字段略过
//            if(resultSet.getString("COLUMN_NAME").equals("id")){
//                continue;
//            }
            columnClass = new ColumnClass();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            //转换字段名称，如 sys_name 变成 SysName
            columnClass.setChangeColumnName(GeneUtils.replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnClass);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("model_column", columnClassList);
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    /**
     * 生成request对象
     *
     * @throws Exception
     */
    private void generateRequestFile() throws Exception {
        final String suffix = ".java";
        final String pagePath = diskPath + "\\model\\request\\";
        File pageFile = new File(pagePath);
        if (!pageFile.exists()) {
            pageFile.mkdirs();
        }
        final String path = pagePath + changeTableName + "Request" + suffix;
        final String templateName = "Request.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    /**
     * 生成response对象
     *
     * @throws Exception
     */
    private void generateResponseFile() throws Exception {
        final String suffix = ".java";
        final String pagePath = diskPath + "\\model\\response\\";
        File pageFile = new File(pagePath);
        if (!pageFile.exists()) {
            pageFile.mkdirs();
        }
        final String path = pagePath + changeTableName + "Response" + suffix;
        final String templateName = "Response.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    /**
     * 生成response对象
     *
     * @throws Exception
     */
    private void generateDaoFile() throws Exception {
        final String suffix = ".java";
        final String pagePath = diskPath + "\\dao\\";
        File pageFile = new File(pagePath);
        if (!pageFile.exists()) {
            pageFile.mkdirs();
        }
        final String path = diskPath + "\\dao\\" + changeTableName + "Repository" + suffix;
        final String templateName = "dao.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private void generateControllerFile() throws Exception {
        final String pagePath = diskPath + "\\controller\\";
        File pageFile = new File(pagePath);
        if (!pageFile.exists()) {
            pageFile.mkdirs();
        }
        final String suffix = "Controller.java";
        final String path = pagePath + changeTableName + suffix;
        final String templateName = "Controller.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private void generateServiceImplFile() throws Exception {
        final String pagePath = diskPath + "\\service\\";
        File pageFile = new File(pagePath);
        if (!pageFile.exists()) {
            pageFile.mkdirs();
        }
        final String suffix = "ServiceImpl.java";
        final String path = pagePath + changeTableName + suffix;
        final String templateName = "ServiceImpl.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private void generateServiceInterfaceFile() throws Exception {
        final String pagePath = diskPath + "\\service\\";
        File pageFile = new File(pagePath);
        if (!pageFile.exists()) {
            pageFile.mkdirs();
        }
        final String suffix = "Service.java";
        final String path = pagePath + changeTableName + suffix;
        final String templateName = "Service.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }


    public void generateFileByTemplate(final String templateName, File file, Map<String, Object> dataMap) throws Exception {
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("table_name_small", tableName);
        dataMap.put("table_name", changeTableName);
        dataMap.put("author", AUTHOR);
        dataMap.put("date", CURRENT_DATE);
        dataMap.put("package_name", packageName);
        dataMap.put("table_annotation", tableAnnotation);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
    }
}
