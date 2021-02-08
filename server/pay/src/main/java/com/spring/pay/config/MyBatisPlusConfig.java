package com.spring.pay.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.spring.common.config.MateMetaObjectHandler;
import com.spring.common.config.TenantConstant;
import com.spring.common.config.TenantContextHolder;
import com.spring.common.config.TenantProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;

/**
 * mybatis plus配置中心
 * @author zwb
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableTransactionManagement
@EnableConfigurationProperties(TenantProperties.class)
public class MyBatisPlusConfig {



    private final TenantProperties tenantProperties;

    /**
     * 单页分页条数限制(默认无限制,参见 插件#handlerLimit 方法)
     */
    private static final Long MAX_LIMIT = 1000L;

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginatioInterceptor = new PaginationInterceptor();
        ArrayList<ISqlParser> iSqlParserArrayList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId() {
                String tenant= TenantContextHolder.getTenantId() ;
                if (tenant != null) {
                    return new StringValue(tenant);
                }else{
                    return new StringValue(TenantConstant.TENANT_ADMIN);

                }

            }

            @Override
            public String getTenantIdColumn() {
                return tenantProperties.getColumn();
            }

            @Override
            public boolean doTableFilter(String tableName) {
                String tenant=TenantContextHolder.getTenantId();

                if(!TenantConstant.TENANT_ADMIN.equals(tenant)){
                    return tenantProperties.getIgnoreTables().stream().anyMatch(
                            (t) -> t.equalsIgnoreCase(tableName)
                    );
                }else{
                    log.info("系统管理员登入。");
                    return true;
                }
            }

        });
        iSqlParserArrayList.add(tenantSqlParser);
        paginatioInterceptor.setSqlParserList(iSqlParserArrayList);
                return paginatioInterceptor;
    }


   /**
     *
     * 自动填充数据
     */
    @Bean
    @ConditionalOnMissingBean(MateMetaObjectHandler.class)
    public MateMetaObjectHandler mateMetaObjectHandler(){
        MateMetaObjectHandler metaObjectHandler = new MateMetaObjectHandler();
        log.info("MateMetaObjectHandler [{}]", metaObjectHandler);
        return metaObjectHandler;
    }


}