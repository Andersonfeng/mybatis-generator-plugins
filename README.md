## 该项目是mybatis 逆向工程 generator的自定义插件
- swagger: https://swagger.io/
- mybatis generator: http://www.mybatis.org/generator/index.html

#插件
- GeneratorSwaggerAnnotation
    - 该插件用于在使用generator同时生成swagger注解
    - 使用方法
        - 在maven build-plugins-plugin中加入依赖
            ```xml
                <dependency>
                    <groupId>io.github.andersonfeng</groupId>
                    <artifactId>com.github.andersonfeng.mybatis-generator-plugins</artifactId>
                    <version>1.1</version>
                </dependency>
            ```
        - 在generatorConfig.xml中配置swagger插件
            ```xml
            <!--自动为entity生成swagger2文档-->
            <plugin type="com.mybatis.generator.plugins.GeneratorSwaggerAnnotation">
                <!--是否生成@apiModel注解-->
                <property name="apiModel" value="true"/>
                <!--@ApiModelProperty注解中是否需要带java属性-->
                <property name="addJavaProperty" value="true"/>
                <!--@ApiModelProperty注解中是否要带数据库字段注释-->
                <property name="addRemarks" value="true"/>
            </plugin>
            ```
        - 生成结果
            ```java
            import io.swagger.annotations.ApiModel;
            import io.swagger.annotations.ApiModelProperty;
            ...
  
            @ApiModel(value="com.generator.Vote")
            @Table(name = "vt_vote")
            public class Vote {
                /**
                 * 自增id
                 */
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                @ApiModelProperty(value="id自增id")
                private Integer id;
            ```            
- GeneratorClassAnnotation
    - 在generatorConfig.xml中配置类注解插件
        ```xml    
            <!--添加类注解和依赖-->
            <plugin type="com.mybatis.generator.plugins.GeneratorClassAnnotation">
                <property name="Data" value="lombok.Data"/>
                <property name="ToString" value="lombok.ToString"/>
            </plugin>
        ```
    - 生成结果
        ```java            
        import java.io.Serializable;
        import java.util.Date;
        import javax.persistence.*;
        import lombok.Data;
        import lombok.ToString;
        ...            
        
        @Data
        @ToString
        @Table(name = "news")
        public class TouTiaoNews implements Serializable {...}
        ```
- GeneratorIgnoreGetter/GeneratorIgnoreSetter
    - 在generatorConfig.xml中配置类注解插件
        ```xml
        <!--去掉getter方法-->
        <plugin type="com.mybatis.generator.plugins.GeneratorIgnoreGetter"/>

        <!--去掉setter方法-->
        <plugin type="com.mybatis.generator.plugins.GeneratorIgnoreSetter"/>
        ```
    - 生成结果
        ```java
        没有getter setter方法
        ```