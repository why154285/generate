package com.why.code.codegenerate.model;

public class PagePathInfo {
    private String tableName;
    private String basePath;
    private String basePackage;
    //controller,service 和model位置
    private String businessBasePath;
    private String businessBasePackage;
    private String author;

    public PagePathInfo build(String tableName,String basePath,String basePackage){
        this.tableName = tableName;
        this.basePath = basePath;
        this.basePackage = basePackage;
        return this;
    }
    public PagePathInfo build(String tableName,String basePath,String basePackage,String businessBasePath,String businessBasePackage,String author){
        this.tableName = tableName;
        this.basePath = basePath;
        this.basePackage = basePackage;
        this.businessBasePath = businessBasePath!=null?businessBasePath:basePath;
        this.businessBasePackage = businessBasePackage!=null?businessBasePackage:basePackage;
        this.author = author;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getBusinessBasePath() {
        return businessBasePath;
    }

    public void setBusinessBasePath(String businessBasePath) {
        this.businessBasePath = businessBasePath;
    }

    public String getBusinessBasePackage() {
        return businessBasePackage;
    }

    public void setBusinessBasePackage(String businessBasePackage) {
        this.businessBasePackage = businessBasePackage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
