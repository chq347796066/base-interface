//package com.spring.common.aop;
//
//import com.spring.common.redis.RedisCacheUtils;
//import io.seata.core.context.RootContext;
//import io.seata.core.exception.TransactionException;
//import io.seata.tm.api.GlobalTransaction;
//import io.seata.tm.api.GlobalTransactionContext;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * 类描述： 用于处理程序调用发生异常的时候由于异常被处理以后无法触发事务，而进行的处理，使之可以正常的触发事务。
// */
//@Aspect
//@Component
//@Slf4j
//public class WorkAspect {
//
//    @Autowired
//    private RedisCacheUtils redisUtils;
//
//
////    @Before("execution(* com.spring.*.service.*.*(..))")
////    public void before(JoinPoint joinPoint) throws TransactionException {
////        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
////        Method method = signature.getMethod();
////        log.info("拦截到需要分布式事务的方法," + method.getName());
////         // 此处可用redis或者定时任务来获取一个key判断是否需要关闭分布式事务
////        GlobalTransaction tx = GlobalTransactionContext.getCurrentOrCreate();
////        if(tx.getXid()!=null ){
////
////            tx.begin(300000, "test-client");
////
//////            String xid = redisUtils.get(tx.getXid(),String.class);
//////            if(StringUtils.isEmpty(xid)){
//////                redisUtils.set(tx.getXid(),"xid");
//////                tx.begin(300000, "test-client");
//////            }
////        }
////
////        log.info("创建分布式事务完毕" + tx.getXid());
////    }
//
//    @AfterThrowing(throwing = "e", pointcut = "execution(* com.spring.*.service.*.*(..))")
//    public void doRecoveryActions(Throwable e) throws TransactionException {
//        log.info("方法执行异常:{}", e.getMessage());
//        if (!StringUtils.isBlank(RootContext.getXID())) {
//            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
//        }
//    }
//
////    @AfterReturning(value = "execution(* com.spring.*.service.*.*(..))", returning = "result")
////    public void afterReturning(JoinPoint point, Object result) throws TransactionException {
////        log.info("方法执行结束:{}", result);
////        if ((Boolean)result) {
////            if (!StringUtils.isBlank(RootContext.getXID())) {
////                log.info("分布式事务Id:{}", RootContext.getXID());
////                GlobalTransactionContext.reload(RootContext.getXID()).commit();
////            }
////        }
////    }
//
//}
