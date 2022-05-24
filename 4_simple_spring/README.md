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

Теперь давайте попробуем добавить наши категории в SpringBoot приложение. В модуле **api** создаем пакет **category**.

Создаем файл [CategoryContextConfiguration](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/category/CategoryContextConfiguration.java)

```java
package dev.rmuhamedgaliev.api.category;

import dev.rmuhamedgaliev.core.domain.category.CategoryRepository;
import dev.rmuhamedgaliev.core.domain.category.InMemoryCategory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryContextConfiguration {

    @Bean
    public CategoryRepository categoryRepository() {
        return new InMemoryCategoryRepository();
    }

    @Bean
    public CategoryManager categoryManager(CategoryRepository categoryRepository) {
        return new CategoryManager(categoryRepository);
    }
}
```

Тут мы указываем Spring, что в качестве репозитория мы будем использовать [InMemoryCategoryRepository](./treasurer/core/src/main/java/dev/rmuhamedgaliev/core/domain/category/InMemoryCategoryRepository.java). Таким образом мы получаем возможность использовать DI контейнер для своих целей. Так же мы объявляем менеджер для бизнес логики [CategoryManager](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/category/CategoryManager.java).

```java
package dev.rmuhamedgaliev.api.category;

import dev.rmuhamedgaliev.core.domain.category.Category;
import dev.rmuhamedgaliev.core.domain.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class CategoryManager {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryManager(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> create(String name, Optional<String> description) {
        return Optional.ofNullable(
                categoryRepository.save(
                        new Category(
                                UUID.randomUUID().toString(),
                                name,
                                description
                        )
                )
        );
    }

    public Optional<Category> findById(String categoryId) {
        return categoryRepository.findCategoryById(categoryId);
    }

    public Optional<Category> update(String categoryId, String name, Optional<String> description) {
        return categoryRepository
                .findCategoryById(categoryId)
                .map(c -> {
                    c.setName(name);
                    description.ifPresent(c::setDescription);
                    return c;
                });
    }
}
```

Для исключительных ситуации мы можем создать свое исключение и при его пробросе наш сервер будет отвечать кодом **500** [CategoryException](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/category/exception/CategoryException.java).

```java
package dev.rmuhamedgaliev.api.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CategoryException extends RuntimeException {

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

Теперь давайте создадим контроллер для обработки запросов, в пакете [web](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/web/) создаем файл [CategoryController](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/web/CategoryController.java).

```java
package dev.rmuhamedgaliev.api.web;

import dev.rmuhamedgaliev.api.category.CategoryManager;
import dev.rmuhamedgaliev.api.category.exception.CategoryException;
import dev.rmuhamedgaliev.api.web.dto.CategoryDto;
import dev.rmuhamedgaliev.api.web.dto.CreateCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryManager manager;

    @Autowired
    public CategoryController(CategoryManager categoryManager) {
        this.manager = categoryManager;
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return manager.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @PostMapping
    public CategoryDto create(@RequestBody CreateCategoryDTO dto) {
        return manager.create(dto.name(), dto.description())
                      .map(CategoryDto::new)
                      .orElseThrow(() -> new CategoryException("Error on create category"));
    }

    @GetMapping(path = "/{categoryId}")
    public CategoryDto findBydId(@PathVariable("categoryId") String categoryId) {
        return manager.findById(categoryId)
                      .map(CategoryDto::new)
                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    @PutMapping(path = "/{categoryId}")
    public CategoryDto updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody CreateCategoryDTO dto) {
        return manager.update(categoryId, dto.name(), dto.description())
                      .map(CategoryDto::new)
                      .orElseThrow(() -> new CategoryException("Error on update category"));
    }
}
```

Важно обратить внимание на следующие моменты:

- `@RequestBody CreateCategoryDTO dto` - [@RequestBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestBody.html) говорит Spring о том, в какой объект будет преобразован JSON из запроса
- `@PathVariable("categoryId") String categoryId` - [@PathVariable](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PathVariable.html) конфигурирует параметр который Spring возьмет из URL как идентификатор категории.

И для того что бы не передавать объекты из ядра приложения и иметь возможность трансформировать ответ в необходимый формат, рекомендуется использовать паттерн [DTO](https://www.baeldung.com/java-dto-pattern). Давайте создадим [CategoryDto](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/web/dto/CategoryDto.java)

```java
package dev.rmuhamedgaliev.api.web.dto;

import dev.rmuhamedgaliev.core.domain.category.Category;

import java.util.Collection;
import java.util.Optional;

public record CategoryDto(String id, String name, Collection<Category> subcategories, Optional<String> description) {

    public CategoryDto(Category category) {
        this(category.getId(), category.getName(), category.getSubcategories(), category.getDescription());
    }
}
```

А для создания новой категории нам необходимо создать другой [CreateCategoryDTO](./treasurer/api/src/main/java/dev/rmuhamedgaliev/api/web/dto/CreateCategoryDTO.java)

```java
package dev.rmuhamedgaliev.api.web.dto;

import org.springframework.lang.NonNull;

import java.util.Optional;

public record CreateCategoryDTO(
        @NonNull String name,
        Optional<String> description
) {
}
```

Для тестирования вы можете воспользоваться любым HTTP клиентом из семейства Postman или можно работать с [HTTP клиентом из IDEA](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html#creating-http-request-files).

Пример может быть таким:

```http
GET http://localhost:8080/?name=Rinat

###

POST http://localhost:8080/category
Content-Type: application/json

{
  "name": "test category1"
}

###

GET http://localhost:8080/category

<> 2022-05-25T004502.200.json
<> 2022-05-24T234743.200.json

###

GET http://localhost:8080/category/67b2b183-30d5-4298-a7db-1a1665019c07

###
PUT http://localhost:8080/category/67b2b183-30d5-4298-a7db-1a1665019c07
Content-Type: application/json

{
  "name": "test update",
  "description": "Update description"
}

```

Таким образом мы получили самый простой CRUD для категорий.

## Задание

- Разберитесь с именованием REST ресурсов и как обычно строятся эндпоинты для запросов
- Реализуйте базовый CRUD для категорий как в примере
- Реализуйте недостающие методы для CRUD
- Доделайте CRUD, что бы там была работа с подкатегориями. (Можете создать новый контроллер).
