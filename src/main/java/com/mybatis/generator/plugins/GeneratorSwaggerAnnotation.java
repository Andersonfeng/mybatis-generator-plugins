package com.mybatis.generator.plugins;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author fengky
 * @date 2018/08/27 10:41
 */
public class GeneratorSwaggerAnnotation extends PluginAdapter {

    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        StringBuilder classAnnotation = new StringBuilder();
        StringBuilder propertyAnnotation = new StringBuilder();
        String trueString = "true";

        //判断是否有apiModel字段,如有添加注解
        String apiModel = properties.getProperty("apiModel");
        if (trueString.equals(apiModel)) {
            classAnnotation.append(topLevelClass.getType());
        }

        //判断注解字段是否需要java注解
        String addJavaProperty = properties.getProperty("addJavaProperty");
        if (trueString.equals(addJavaProperty)) {
            propertyAnnotation.append(introspectedColumn.getJavaProperty());
        }

        //判断注解字段是否需要添加注释字段
        String addRemarks = properties.getProperty("addRemarks");
        if (trueString.equals(addRemarks)) {
            //过滤掉特殊字符,如换行
            String regex = "\\n";
            propertyAnnotation.append(StringUtils.replaceAll(introspectedColumn.getRemarks(),regex," "));
        }


        if (!StringUtils.isEmpty(classAnnotation)) {
            topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
            String classAnnotationStr = "@ApiModel(value=\"" + classAnnotation + "\")";
            List<String> annotationList = topLevelClass.getAnnotations();
            //防止重复注解
            if (!annotationList.contains(classAnnotationStr)) {
                topLevelClass.addAnnotation(classAnnotationStr);
            }
        }

        if (!StringUtils.isEmpty(propertyAnnotation)) {
            topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
            field.addAnnotation("@ApiModelProperty(value=\"" + propertyAnnotation + "\")");
        }


        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
