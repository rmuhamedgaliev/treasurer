# 4. Spring. Введение

Давайте приступим к знакомству со Spring и попробуем реализовать простейший REST сервис. Я не буду в деталях рассказывать как устроен Spring, до меня это сделано во многих местах:

- [Spring](https://spring.io/learn)
- [Евгений Борисов & Spring](https://www.youtube.com/playlist?list=PL4QyVSQW_ZfzzvTe6YSlGZiVP2AfjzFHf)

И множество других статей о внутреннем устройстве Spring. Для нашего необходимо сделать простой CRUD REST Api для работы по HTTP. Для этого нам необходимо в проект **api** добавить зависимости в [pom.xml](./treasurer/api/pom.xml)

```xml
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>dev.rmuhamedgaliev</groupId>
    <artifactId>api</artifactId>
    <packaging>jar</packaging>
    <version>1.0-SNAPSHOT</version>
    <name>api</name>
    <url>http://maven.apache.org</url>

    <properties>
        <maven.compiler.source>18</maven.compiler.source>
        <maven.compiler.target>18</maven.compiler.target>
    </properties>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0</version>
        <relativePath/>
    </parent>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.junit</groupId>
                <artifactId>junit-bom</artifactId>
                <version>5.8.2</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>dev.rmuhamedgaliev</groupId>
            <artifactId>core</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>

    </dependencies>
</project>
```

Затем удалите содержимое тестов [AppTest.java](./treasurer/api/src/test/java/dev/rmuhamedgaliev/AppTest.java). Не забудьте сделать реимпорт [проекта maven](https://www.jetbrains.com/help/idea/work-with-maven-dependencies.html#maven_dependency_multi_module).

Так как SpringBoot имеет механизм [autoconfiguration](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-auto-configuration.html#using-boot-auto-configuration), то для простого приложения нет необходимости делать сложную конфигурацию. Давайте сделаем простой REST сервис. 

В файл [App.java](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/App.java) добавьте SpringBoot приложение.

```java
package dev.rmuhamedgaliev.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"dev.rmuhamedgaliev.api"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

Аннотация [SpringBootApplication](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-using-springbootapplication-annotation.html#using-boot-using-springbootapplication-annotation) является композитной аннотацией и частью *auto-configuration* для того что бы наше Spring приложение могло знать где искать бины, флагом для автоконфигурации и самой конфигурацией. Затем просто вызвав в нашем приложении *SpringApplication* и передав аргументы нашего приложения мы получаем начальную конфигурацию для нашего сервиса.

Для обработки HTTP запросов нам необходимо создать [контролер](https://www.baeldung.com/spring-controller-vs-restcontroller). В этих классах, мы пишем логику обработки HTTP запросов, но никакой бизнес логики в них не должно быть.

Давайте создадим свой контроллер:

- добавляем пакет **web**
- создаем класс [HelloController](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/web/HelloController.java)

```java
package dev.rmuhamedgaliev.api.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HelloController {

    private final String greeting = "Hello ";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Message sayHello(@RequestParam("name") Optional<String> name) {
        return new Message(greeting + name.orElse("user"));
    }

    public record Message(String message) {
    }
}
```

Таким образом мы имеем код который будет работать как сервер, просто запустите *main* метод и в консоли вы увидите 

```bash
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2022-05-23 23:09:42.741  INFO 11844 --- [           main] dev.rmuhamedgaliev.api.App               : Starting App using Java 18.0.1 on Rinats-MacBook-Pro.local with PID 11844 (/Users/rinatmuhamedgaliev/Projects/treasurer-manager/4_simple_spring/treasurer/api/target/classes started by rinatmuhamedgaliev in /Users/rinatmuhamedgaliev/Projects/treasurer-manager/4_simple_spring/treasurer)
2022-05-23 23:09:42.744  INFO 11844 --- [           main] dev.rmuhamedgaliev.api.App               : No active profile set, falling back to 1 default profile: "default"
2022-05-23 23:09:43.433  INFO 11844 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-05-23 23:09:43.440  INFO 11844 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-05-23 23:09:43.440  INFO 11844 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.63]
2022-05-23 23:09:43.516  INFO 11844 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-05-23 23:09:43.516  INFO 11844 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 726 ms
2022-05-23 23:09:43.824  INFO 11844 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-05-23 23:09:43.835  INFO 11844 --- [           main] dev.rmuhamedgaliev.api.App               : Started App in 1.427 seconds (JVM running for 2.097)
2022-05-23 23:10:16.308  INFO 11844 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-05-23 23:10:16.308  INFO 11844 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-05-23 23:10:16.309  INFO 11844 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
```

Открываем браузер на ссылке [http://localhost:8080](http://localhost:8080) и получаем:

```json
{"message":"Hello user"}
```

А если добавим параметр name=Name - [http://localhost:8080?name=Name](http://localhost:8080?name=Name) - то получим именное приветствие.

```json
{"message":"Hello Name"}
```

Поздравляю, теперь у вас простейший WEB сервис. Давайте разберем, как он это делает?

```java
@RestController // Говорим Spring, что это контроллер и сюда следует направлять входящие HTTP запросы 
public class HelloController {

    private final String greeting = "Hello ";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Message sayHello(@RequestParam("name") Optional<String> name) {
        return new Message(greeting + name.orElse("user"));
    }

    public record Message(String message) {
    }
}
```

- [@RestController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html) - это расширенная аннотация которая помогает Spring найти все места которые могу обрабатывать HTTP запросы и помогает сконвертировать Java объекты в HTTP ответ, включая в работу аннотацию [@ResponseBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html)
- [@GetMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html) - эта аннотация помогает нам подсказать Spring как обработать HTTP запрос. В Spring для обработки базовых HTTP методов есть соответствующие аннотации:
    - [@GetMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html)
    - [@PostMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PostMapping.html)
    - [@DeleteMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/DeleteMapping.html)
    - [@PutMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PutMapping.html)
- [@RequestParam](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html) - позволяет нам обрабатывать параметр в URL которые стоят за вопросительным знаком **?name=Name** или если параметр не первый то после знака амперсанд **&name=Name**

Теперь давайте попробуем добавить наши категории в SpringBoot приложение.

