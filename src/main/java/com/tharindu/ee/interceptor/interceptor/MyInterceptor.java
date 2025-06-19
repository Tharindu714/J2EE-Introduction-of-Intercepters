package com.tharindu.ee.interceptor.interceptor;

import jakarta.interceptor.InvocationContext;

import java.util.Map;

public class MyInterceptor {
    public Object intercept(InvocationContext context) throws Exception {
        System.out.println("MyInterceptor triggered!");
        Map<String, Object> contextData = context.getContextData();
        System.out.println("Context data: " + contextData.size() + " - " + (contextData.isEmpty() ? "No context data" : contextData));
        context.proceed();
        return null;
    }
}
