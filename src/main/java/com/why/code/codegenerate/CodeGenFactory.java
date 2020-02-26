package com.why.code.codegenerate;

import com.why.code.codegenerate.model.DatasourceInfo;
import com.why.code.codegenerate.model.PagePathInfo;

public class CodeGenFactory {
    private DatasourceInfo datasourceInfo;
    private PagePathInfo pagePathInfo;

    /**
     * 输入数据库信息
     *
     * @param datasourceInfo 数据库信息
     * @return 当前对象
     */
    public CodeGenFactory buildData(DatasourceInfo datasourceInfo) {
        this.datasourceInfo = datasourceInfo;
        return this;
    }

    public void genCodes(boolean flag, PagePathInfo... pagePathInfos) {
        CodeGenManager codeGenManager = new CodeGenManager();
        try {
            if (datasourceInfo != null) {
                for (int index = 0, max = pagePathInfos.length; index < max; index++) {
                    codeGenManager.buildGene(pagePathInfos[index], datasourceInfo);
                    //生成dao和entity
                    codeGenManager.generateDao(codeGenManager.getBusinessBasePath(), codeGenManager.getChangeTableName(), flag);
                    //生成业务逻辑代码
                    //codeGenManager.generate(codeGenManager.getDiskPath(), codeGenManager.getChangeTableName());
                }
            } else {
                for (int index = 0, max = pagePathInfos.length; index < max; index++) {
                    codeGenManager.buildCodeGenerateUtils(pagePathInfos[index]);
                    codeGenManager.generateDao(codeGenManager.getBusinessBasePath(), codeGenManager.getChangeTableName(), flag);
                    // codeGenManager.generate(codeGenManager.getDiskPath(),  codeGenManager.getChangeTableName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成代码失败");
        }
    }
}
