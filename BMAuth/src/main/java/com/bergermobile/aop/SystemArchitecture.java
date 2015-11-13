package com.bergermobile.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Base AOP class, that decides which packages to intercept
 * @author fabioberger
 */
@Aspect
public class SystemArchitecture {

    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.bergermobile.web package or any sub-package
     * under that.
     */
    @Pointcut("within(com.bergermobile.web..*)")
    public void inWebLayer() {}

    /**
     * A join point is in the rest service layer if the method is defined
     * in a type in the com.bergermobile.rest.services package or any sub-package
     * under that.
     */
    @Pointcut("within(com.bergermobile.rest.services..*)")
    public void inRestServiceLayer() {}

    /**
     * A join point is in the persistence layer if the method is defined
     * in a type in the com.bergermobile.persitence.repository package or any sub-package
     * under that.
     */
    @Pointcut("execution(* org.springframework.data.repository.*.save*(..))")
    public void saveRepositoryOperation() {}
}