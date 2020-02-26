package com.why.code.codegenerate.model;

import java.io.File;
import java.util.Map;

public class TemplateGenModel {
    private String templateName;
    private File mapperFile;
    Map<String, Object> dataMap;

    public TemplateGenModel(String templateName, File mapperFile, Map<String, Object> dataMap) {
        this.templateName = templateName;
        this.mapperFile = mapperFile;
        this.dataMap = dataMap;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public File getMapperFile() {
        return mapperFile;
    }

    public void setMapperFile(File mapperFile) {
        this.mapperFile = mapperFile;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
