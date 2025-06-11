package com.backend.recipeManagement;

import static org.jooq.impl.DSL.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
  @Around("execution(* com.backend.recipeManagement..*(..))")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
    String entryLayer = determineEntryLayer(declaringType);
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getSignature().getDeclaringTypeName();
    // Entering
    log.info(entryLayer, String.format("Method: %s, File: %s", methodName, className));

    Object result = joinPoint.proceed();
    String exitLayer = determineExitLayer(declaringType);
    // Exiting
    log.info(exitLayer, String.format("Method: %s, File: %s", methodName, className));
    return result;
  }

  private String determineEntryLayer(Class<?> className) {
    if (AnnotationUtils.findAnnotation(className, RestController.class) != null
        || AnnotationUtils.findAnnotation(className, Controller.class) != null) {
      return LogUtil.ENTRY_CONTROLLER;
    } else if (AnnotationUtils.findAnnotation(className, Service.class) != null) {
      return LogUtil.ENTRY_SERVICE;
    } else if (AnnotationUtils.findAnnotation(className, Repository.class) != null) {
      return LogUtil.ENTRY_REPOSITORY;
    } else {
      return LogUtil.ENTRY;
    }
  }

  private String determineExitLayer(Class<?> className) {
    if (AnnotationUtils.findAnnotation(className, RestController.class) != null
        || AnnotationUtils.findAnnotation(className, Controller.class) != null) {
      return LogUtil.EXIT_CONTROLLER;
    } else if (AnnotationUtils.findAnnotation(className, Service.class) != null) {
      return LogUtil.EXIT_SERVICE;
    } else if (AnnotationUtils.findAnnotation(className, Repository.class) != null) {
      return LogUtil.EXIT_REPOSITORY;
    } else {
      return LogUtil.EXIT;
    }
  }
}