package com.gxh.shop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 解决大量Generating unique operation named: updateUsingPOST_7日志问题
 * @author gxh
 */
@Component
@Aspect
@Slf4j
public class CachingOperationNameGeneratorAspect {

    private final Map<String, Integer> generated = new HashMap<String, Integer>();
    @Pointcut("execution(* springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator.startingWith(String))")
    public void c() {
    }


    @Around("c()")
    public Object a(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        return startingWith(String.valueOf(args[0]));
    }

    private String startingWith(String prefix) {
        if (generated.containsKey(prefix)) {
            generated.put(prefix, generated.get(prefix) + 1);
            String nextUniqueOperationName = String.format("%s_%s", prefix, generated.get(prefix));
            if (log.isDebugEnabled()){
                log.warn("组件中存在相同的方法名称，自动生成组件方法唯一名称进行替换: {}", nextUniqueOperationName);
            }
            return nextUniqueOperationName;
        } else {
            generated.put(prefix, 0);
            return prefix;
        }
    }

    
}
