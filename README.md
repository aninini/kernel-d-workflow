# 工作流模块

## 工作流集成流程

1. 首先`SpringMvcConfiguration`类中，加入模型设计器的静态资源映射

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");

    // 流程设计器
    registry.addResourceHandler("/designer/**").addResourceLocations("classpath:/designer/");
}
```

2. spring启动类中，加入排除 `FlowableSecurityAutoConfiguration.class`

```java
@Slf4j
@SpringBootApplication(scanBasePackages = {"cn.stylefeng"},
        exclude = {FlywayAutoConfiguration.class, GunsDataSourceAutoConfiguration.class, FlowableSecurityAutoConfiguration.class})
public class GunsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsApplication.class, args);
        log.info(GunsApplication.class.getSimpleName() + " is success!");
    }

}
```

3. mybatis-plus配置，增加如下配置，否则启动报错

```yml
mybatis-plus:
  # 加入对流程设计器的mapper扫描
  mapper-locations: classpath*:cn/stylefeng/**/mapping/*.xml, classpath:/META-INF/modeler-mybatis-mappings/*.xml
  # 流程设计器mapper修复 https://blog.csdn.net/xljx_1/article/details/105320252
  configuration-properties:
    prefix:
    blobType: BLOB
    boolValue: TRUE
```

4. jackson配置的时间格式化增加末尾的SSS，否则模型设计器查询会报错

```yml
spring:
  jackson:
    time-zone: GMT+8
    date-format: yyyy-MM-dd HH:mm:ss.SSS
```

5. pom中增加对工作流的依赖

```xml
<!--工作流-->
<dependency>
    <groupId>cn.stylefeng.roses</groupId>
    <artifactId>workflow-spring-boot-starter</artifactId>
    <version>${roses.kernel.version}</version>
    <exclusions>
        <exclusion>
            <artifactId>mybatis-spring</artifactId>
            <groupId>org.mybatis</groupId>
        </exclusion>
        <exclusion>
            <artifactId>mybatis</artifactId>
            <groupId>org.mybatis</groupId>
        </exclusion>
    </exclusions>
</dependency>
```

## 部署注意事项

上线时候，前端工作流设计器的配置，注意设置这里为/api开头
![](.README_images/cf511bf8.png)

并且nginx的配置如下：
![](.README_images/25ec22b5.png)