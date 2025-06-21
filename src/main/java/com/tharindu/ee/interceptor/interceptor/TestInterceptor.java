package com.tharindu.ee.interceptor.interceptor;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.interceptor.AroundConstruct;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
//import java.util.Arrays;

public class TestInterceptor {

    @AroundConstruct
    public void aroundConstruct(InvocationContext context) throws Exception {
        System.out.println("TestInterceptor aroundConstruct triggered!" + context.getConstructor().getName());
    }

    @PostConstruct
    public void init(InvocationContext context) {
        // Initialization logic if needed
        System.out.println("TestInterceptor initialized!");
        Constructor<?> constructor = context.getConstructor();
        if (constructor != null) {
            System.out.println("Constructor being invoked: " + constructor.getName());
        } else {
            System.out.println("No constructor being invoked.");
        }
    }

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        // Interception logic goes here
        System.out.println("Interceptor triggered!");

        Method method = context.getMethod();
        System.out.println("Method being invoked: " + method.getName());

//        Object[] parameters = context.getParameters();
//        System.out.println("Parameters passed: " + parameters.length + " - " + (parameters.length > 0 ? parameters[0] : "No parameters"));
//        Arrays.stream(parameters).forEach(System.out::println);
//        context.setParameters(new Object[]{"Steve", 25});

        Object target = context.getTarget();
        System.out.println("Target object: " + target);

//        Map<String, Object> contextData = context.getContextData();
//        System.out.println("Context data: " + contextData.size() + " - " + (contextData.isEmpty() ? "No context data" : contextData));

        Object proceed = context.proceed();
        System.out.println("Method execution result: " + proceed);

        System.out.println("Interceptor Process End!");
        return proceed;
    }

    @PreDestroy
    public void destroy(InvocationContext context) {
        // Cleanup logic if needed
        System.out.println("TestInterceptor destroyed!" + context);
    }
}
