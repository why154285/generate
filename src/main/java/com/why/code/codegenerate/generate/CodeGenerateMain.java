package com.why.code.codegenerate.generate;

import com.why.code.codegenerate.CodeGenFactory;
import com.why.code.codegenerate.CodeGenerateUtils;
import com.why.code.codegenerate.model.DatasourceInfo;
import com.why.code.codegenerate.model.PagePathInfo;

public class CodeGenerateMain {


    public static void main(String[] args) {
        try {
            test4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test4() throws Exception {
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        DatasourceInfo dtInfo = new DatasourceInfo();
        dtInfo.setURL("jdbc:mysql://192.168.1.171:3305/greencredit");
        dtInfo.setUSER("root");
        dtInfo.setPASSWORD("root");
        dtInfo.setDRIVER("com.mysql.jdbc.Driver");
        String tableName = "goods";
        String basePath = "/Users/tlfu/nlzhProject/greenCredit/src/main/java/com/energy/greencredit";
        String basePackage = "com.energy.greencredit";
//        String businessBasePath =  "D:/testproject/common/src/main/java/com/why/greenhouse/common";
//        String businessBasePackage = "com.why.greenhouse.common";
        String author = "tlfu";
        //是否覆盖原有同名包，默认不覆盖
        boolean flag = false;
//        codeGenerateUtils.buildGene(tableName,basePath,basePackage,dtInfo).generate();
        PagePathInfo pathInfo = new PagePathInfo();
//        pathInfo = pathInfo.build(tableName,basePath,basePackage,businessBasePath,businessBasePackage,author);
        pathInfo = pathInfo.build(tableName, basePath, basePackage, null, null, author);
        CodeGenFactory genFactory = new CodeGenFactory();
        genFactory.buildData(dtInfo).genCodes(flag, pathInfo);
    }
}
