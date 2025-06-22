# 🔄 J2EE Built-in Interceptors Introduction

> A primer on using JEE container-managed interceptors for cross-cutting concerns without custom bindings, leveraging standard EJB lifecycle and method-level interception.

---

## 📑 Table of Contents

1. [✨ Overview](#-overview)
2. [📂 Project Structure](#-project-structure)
3. [🎯 Interceptor Types](#-interceptor-types)
4. [⚙️ Defining an Interceptor Class](#-defining-an-interceptor-class)
5. [🚀 Applying with `@Interceptors`](#-applying-with-interceptors)
6. [📦 Usage Examples](#-usage-examples)
7. [🔍 How It Works](#-how-it-works)
8. [📜 License](#-license)

---

## ✨ Overview

This project demonstrates **out-of-the-box JEE interceptors**:

* **EJB Method Interceptors** via `@Interceptors` annotation.
* **Lifecycle Callback Interceptors** (`@PostConstruct`, `@PreDestroy`).
* No custom interceptor bindings—uses standard API.
* Ideal for quick enabling of logging, security checks, or transaction delimiting.

---

## 📂 Project Structure

```
J2EE-Introduction-of-Interceptors/
├── src/main/java/com/example/interceptors/
│   ├── interceptor/
        ├── MyInterceptor.java
│   │   └── TestInterceptor.java   # Implements logging around methods
│   ├── ejb/
│   │   └── UserSessionBean.java              # Bean with interceptor applied
│   └── servlet/
│       └── Test.java                     # Sample entity
└── src/main/resources/META-INF/beans.xml   # Enables CDI discovery (optional)
```

---

## 🎯 Interceptor Types

1. **Method Interceptors**: Wrap business methods (`@AroundInvoke`).
2. **Lifecycle Interceptors**: Hook into bean lifecycle events like `@PostConstruct` and `@PreDestroy`.

---

## ⚙️ Defining an Interceptor Class

```java
package com.tharindu.ee.interceptor.interceptor;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.interceptor.AroundConstruct;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

@Interceptor
public class TestInterceptor {

  @AroundInvoke
  public Object log(InvocationContext ctx) throws Exception {
    System.out.println("[Intercept] Entering: " + ctx.getMethod().getName());
    Object result = ctx.proceed();
    System.out.println("[Intercept] Exiting: " + ctx.getMethod().getName());
    return result;
  }

  @PostConstruct
  private void onInit(InvocationContext ctx) {
    System.out.println("[Lifecycle] Bean initialized: " + ctx.getTarget().getClass().getSimpleName());
  }

  @PreDestroy
  private void onDestroy(InvocationContext ctx) {
    System.out.println("[Lifecycle] Bean about to destroy: " + ctx.getTarget().getClass().getSimpleName());
  }
}
```

---

## 🚀 Applying with `@Interceptors`

Place the standard annotation on a bean class or method:

```java
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
```

---

## 📦 Usage Examples

```java
// In a client
OrderService service = lookup(OrderService.class);
service.placeOrder(new Order("123", 2, 49.99));
service.cancelOrder("123");
```

> **Console Output:**
>
> ```
> [Intercept] Entering: placeOrder
> [Intercept] Exiting: placeOrder
> [Intercept] Entering: cancelOrder
> [Intercept] Exiting: cancelOrder
> ```

---

## 🔍 How It Works

1. The container recognizes `@Interceptors` and binds the specified interceptor class.
2. For each intercepted invocation, it creates an `InvocationContext`.
3. Calls `@PostConstruct` after bean creation, then wraps business methods with `@AroundInvoke`.
4. Executes `@PreDestroy` before bean removal.

---

## 📜 License

MIT © 2025 Tharindu Chanaka

---

> Empowering JEE beans with standard interception! 🚀

