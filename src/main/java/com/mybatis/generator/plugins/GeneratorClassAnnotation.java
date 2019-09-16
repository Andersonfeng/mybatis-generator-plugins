package com.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author fengky
 * <p>
 * generator class annotation & solve dependency
 */
public class GeneratorClassAnnotation extends PluginAdapter {
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        for (Object objectKey : properties.keySet()) {
            String key = objectKey.toString();
            //key为class上需要加的注解
            String classAnnotationStr = "@" + key;
            List<String> annotationList = topLevelClass.getAnnotations();
            //value为需要import的内容
            String value = properties.getProperty(key);

            //防止重复添加注解和依赖
            if (!annotationList.contains(classAnnotationStr)) {
                topLevelClass.addAnnotation(classAnnotationStr);
                topLevelClass.addImportedType(value);
            }


        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
