package com.task.crud.aspects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


/**
 * Аспект для логирования операций, выполняемых в сервисах.
 * Обеспечивает логирование успешных выполнений методов, а также обработку исключений.
 *
 * @author Zabnev Konstantin
 * @version 1.0
 * @since 2024-11-27
 */
@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    /**
     * Определяет точку среза для всех методов, начинающихся с add
     * в классе {@link com.task.crud.service.implementation.PhoneServiceImpl}.
     */
    @Pointcut("execution(* com.task.crud.service.implementation.PhoneServiceImpl.add*(..))")
    private void allAddPhoneMethods() {}

    /**
     * Определяет точку среза для всех методов, начинающихся с update
     * в классе {@link com.task.crud.service.implementation.PhoneServiceImpl}.
     */
    @Pointcut("execution(* com.task.crud.service.implementation.PhoneServiceImpl.update*(..))")
    private void allUpdatePhoneMethods() {}

    /**
     * Определяет точку среза для всех методов, начинающихся с delete
     * в классе {@link com.task.crud.service.implementation.PhoneServiceImpl}.
     */
    @Pointcut("execution(* com.task.crud.service.implementation.PhoneServiceImpl.delete*(..))")
    private void allDeletePhoneMethods() {}

    /**
     * Возвращает параметры метода, вызванного в точке среза, в формате строки.
     *
     * @param joinPoint информация о точке среза метода
     * @return параметры метода в формате {@code paramName=paramValue, ...}
     */
    private String getMethodParams(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            params.append(parameterNames[i])
                    .append("=")
                    .append(args[i])
                    .append(i < parameterNames.length - 1 ? ", " : "");
        }

        return params.toString();
    }

    /**
     * Логирует исключения, возникшие в любом методе класса {@link com.task.crud.service.implementation.PhoneServiceImpl}.
     *
     * @param joinPoint информация о точке среза метода
     * @param exception исключение, выброшенное методом
     */
    @AfterThrowing(pointcut = "execution(* com.task.crud.service.implementation.PhoneServiceImpl.*(..))", throwing = "exception")
    public void afterThrowingAllPhoneMethodsAdvice(JoinPoint joinPoint, Throwable exception) {
        String params = getMethodParams(joinPoint);

        logger.log(Level.ERROR, String.format("Exception %s in method %s, parameters: [%s]",
                exception.getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                params));
    }

    /**
     * Логирует параметры и результат всех методов класса {@link com.task.crud.service.implementation.PhoneServiceImpl},
     * которые начинаются с {@code get}.
     *
     * @param joinPoint информация о точке среза метода
     * @param result возвращаемое значение метода
     */
    @AfterReturning(pointcut="execution(* com.task.crud.service.implementation.PhoneServiceImpl.get*(..))", returning = "result")
    public void afterReturningAllGetMethodsAdvice(JoinPoint joinPoint, Object result) {
        String params = getMethodParams(joinPoint);

        logger.log(Level.INFO, String.format("Method %s with parameters: [%s]; result: [%s]",
                joinPoint.getSignature().getName(),
                params,
                result));
    }

    /**
     * Логирует результат всех методов класса {@link com.task.crud.service.implementation.PhoneServiceImpl}, связанных с
     * добавлением, обновлением и удалением записей (методы, начинающиеся с {@code add}, {@code update} или {@code delete}).
     *
     * @param joinPoint информация о точке среза метода
     * @param result возвращаемое значение метода
     */
    @AfterReturning(pointcut = "allAddPhoneMethods() || allUpdatePhoneMethods() || allDeletePhoneMethods()", returning = "result")
    public void afterReturningAllExcludeGetPhoneMethodsAdvice(JoinPoint joinPoint, Object result) {
        logger.log(Level.INFO, String.format("Method %s, result: [%s]",
                joinPoint.getSignature().getName(),
                result));
    }
}
