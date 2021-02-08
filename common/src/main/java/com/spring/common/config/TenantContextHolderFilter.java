package com.spring.common.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 租户上下文过滤器
 * @author zwb
 * @date 2020-11-7
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            //优先取请求参数中的tenantId值
            String tenantId = request.getParameter(TenantConstant.MATE_TENANT_ID_PARAM);
            if (StringUtils.isEmpty(tenantId)) {
                tenantId = request.getHeader(TenantConstant.MATE_TENANT_ID);
            }
            log.debug("获取到的租户ID为:{}", tenantId);
            if (StringUtils.isNotBlank(tenantId)) {
                TenantContextHolder.setTenantId(tenantId);
            } else {
                if (StringUtils.isBlank(TenantContextHolder.getTenantId())) {
                    TenantContextHolder.setTenantId(TenantConstant.TENANT_ADMIN);
                }
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }
}
