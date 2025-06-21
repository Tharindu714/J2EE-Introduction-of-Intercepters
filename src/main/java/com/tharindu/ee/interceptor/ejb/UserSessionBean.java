package com.tharindu.ee.interceptor.ejb;

import com.tharindu.ee.interceptor.interceptor.MyInterceptor;
import com.tharindu.ee.interceptor.interceptor.TestInterceptor;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.interceptor.InvocationContext;

@Stateless
@Interceptors({TestInterceptor.class, MyInterceptor.class})
public class UserSessionBean {

//    @Inject
//    InvocationContext invocationContext;

    public String doAction() {
        return "Executing action without parameters";
    }

    public void doAction(String name, int age) {
        // Business logic for the user session bean
        System.out.println("Executing action with name: " + name + " and age: " + age);
    }
}
