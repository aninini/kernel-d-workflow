package cn.stylefeng.roses.kernel.workflow.starter;

import cn.hutool.core.convert.Convert;
import cn.stylefeng.roses.kernel.workflow.business.core.listener.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventDispatcher;
import org.flowable.common.engine.impl.event.FlowableEventDispatcherImpl;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.spring.boot.FlowableProperties;
import org.flowable.ui.common.service.idm.RemoteIdmService;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.flowable.ui.modeler.servlet.ApiDispatcherServletConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 覆盖ApplicationConfiguration，移除flowable的idm认证
 *
 * @author fengshuonan
 * @date 2020/4/14 10:07
 */
@Configuration
@EnableConfigurationProperties({FlowableModelerAppProperties.class})
@ComponentScan(basePackages = {
        "org.flowable.ui.modeler.repository",
        "org.flowable.ui.modeler.service",
        "org.flowable.ui.common.service",
        "org.flowable.ui.common.repository",
        "org.flowable.ui.common.tenant"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RemoteIdmService.class)
        })
public class GunsWorkflowAutoConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    /**
     * Flowable的servlet注册
     *
     * @author fengshuonan
     * @date 2021/6/19 17:50
     */
    @Bean
    @SuppressWarnings("ALL")
    public ServletRegistrationBean apiServlet(ApplicationContext applicationContext) {
        AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
        dispatcherServletConfiguration.setParent(applicationContext);
        dispatcherServletConfiguration.register(ApiDispatcherServletConfiguration.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherServletConfiguration);
        ServletRegistrationBean<DispatcherServlet> registrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        registrationBean.addUrlMappings("/api/*");
        registrationBean.setName("Flowable IDM App API Servlet");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

    /**
     * FlowableProperties配置，关闭定时任务
     *
     * @author fengshuonan
     * @date 2020/4/14 8:54
     */
    @Bean
    public FlowableProperties flowableProperties() {
        FlowableProperties flowableProperties = new FlowableProperties();
        //关闭自动任务
        flowableProperties.setAsyncExecutorActivate(false);
        //关闭自动创建表，第一次创建完表之后可以关闭
        flowableProperties.setDatabaseSchemaUpdate(Convert.toStr(false));
        //关闭自动部署resource/processes中的流程文件
        flowableProperties.setCheckProcessDefinitions(false);
        return flowableProperties;
    }

    /**
     * 自定义Flowable主键生成格式
     *
     * @author fengshuonan
     * @date 2020/4/21 17:27
     */
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        //使用mybatis-plus的主键生成器，注意：不会影响act_de开头的表主键生成，因为这是流程设计器的，不是工作流引擎的
        springProcessEngineConfiguration.setIdGenerator(() -> IdWorker.getIdStr(new Object()));
        //创建EventDispatcher
        FlowableEventDispatcher eventDispatcher = new FlowableEventDispatcherImpl();
        //添加全局流程启动监听器
        eventDispatcher.addEventListener(new GlobalProcessStartedListener(), FlowableEngineEventType.PROCESS_STARTED);
        //添加全局流程完成监听器
        eventDispatcher.addEventListener(new GlobalProcessCompletedListener(), FlowableEngineEventType.PROCESS_COMPLETED);
        //添加全局流程取消监听器
        eventDispatcher.addEventListener(new GlobalProcessCancelledListener(), FlowableEngineEventType.PROCESS_CANCELLED);
        //添加全局活动开始监听器
        eventDispatcher.addEventListener(new GlobalActStartedListener(), FlowableEngineEventType.ACTIVITY_STARTED);
        //添加全局活动完成监听器
        eventDispatcher.addEventListener(new GlobalActCompletedListener(), FlowableEngineEventType.ACTIVITY_COMPLETED);
        //添加全局活动取消监听器
        eventDispatcher.addEventListener(new GlobalActCancelledListener(), FlowableEngineEventType.ACTIVITY_CANCELLED);
        //添加全局任务分配监听器
        eventDispatcher.addEventListener(new GlobalTaskAssignedListener(), FlowableEngineEventType.TASK_ASSIGNED);
        //添加全局任务创建监听器
        eventDispatcher.addEventListener(new GlobalTaskCreatedListener(), FlowableEngineEventType.TASK_CREATED);
        //添加全局任务完成监听器
        eventDispatcher.addEventListener(new GlobalTaskCompletedListener(), FlowableEngineEventType.TASK_COMPLETED);
        //添加全局连接线监听器
        eventDispatcher.addEventListener(new GlobalSequenceflowTakenListener(), FlowableEngineEventType.SEQUENCEFLOW_TAKEN);
        //设置EventDispatcher
        springProcessEngineConfiguration.setEventDispatcher(eventDispatcher);
    }

}