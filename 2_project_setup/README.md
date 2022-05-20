# 2. Настройка проекта

[![Build project setup](https://github.com/rmuhamedgaliev/treasurer/actions/workflows/2_project_setup_build.yml/badge.svg?branch=master)](https://github.com/rmuhamedgaliev/treasurer/actions/workflows/2_project_setup_build.yml)

Разрабатывать проект мы будем при помощи [мульти-модульной структуре](https://www.baeldung.com/maven-multi-module). [Maven поддерживает создание проекта с разными модулями](https://www.baeldung.com/maven-multi-module). Для создания проекта необходимо проделать следующие шаги:

Везде где вы видите `dev.rmuhamedgaliev`, заменяйте на свой пакет. Правила именования доступны по [ссылке.](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html)

- создаем проект на github c названием **treasurer**
- клонируем на локальный компьютер
- переходим в директорию с проектом
- создаем ветку для 1 первого задания
- выполняем команду `mvn archetype:generate -DgroupId=dev.rmuhamedgaliev -DartifactId=core`. Этой командой мы инициализируем maven проект с именем **core**
- затем нам необходимо создать проект для API `mvn archetype:generate -DgroupId=dev.rmuhamedgaliev -DartifactId=api`
- в каждом **pom.xml** необходимо прописать

```xml
    <properties>
        <maven.compiler.source>18</maven.compiler.source>
        <maven.compiler.target>18</maven.compiler.target>
    </properties>
```

Это конфигурирует Maven на использование Java 18.

- теперь в корне директории создаем файл [pom.xml](./treasurer/pom.xml) со следующим содержимым:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>dev.rmuhamedgaliev</groupId>
    <artifactId>treasurer</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>treasurer</name>
    <packaging>pom</packaging>

    <properties>
        <maven.compiler.source>18</maven.compiler.source>
        <maven.compiler.target>18</maven.compiler.target>
    </properties>

    <modules>
        <module>core</module>
        <module>api</module>
    </modules>

</project>
```

Таким образом мы говорим maven, что собираемся использовать модули проекта. Теперь проект можно открыть в Idea.

Для тестирования откроем проект `core` и создадим пакет `models`, а в нем уже файл **Category.java**

```java
package dev.rmuhamedgaliev.model;

public record Category(String id) {
}
```

Теперь можем добавить в API зависимость от модуля CORE. Переходим в [pom.xml](./treasurer/api/pom.xml) из API модуля и добавляем зависимость на CORE

```xml
    <dependency>
      <groupId>dev.rmuhamedgaliev</groupId>
      <artifactId>core</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
```

Остается сделать реимпорт проекта в IDE и обновить тест [AppTest](./treasurer/api/src/test/java/dev/rmuhamedgaliev/AppTest.java).

```java
package dev.rmuhamedgaliev;

import dev.rmuhamedgaliev.model.Category;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        Category category = new Category("1");
        Assert.assertNotNull(category);
    }
}
```

Запустив тесты например через `mvn test`, компиляция и тесты должны пройти успешно. Теперь можно запушить все на GitHub и создать PR.
Поздравляю, теперь мы можем приступить к работе над проектом. Пример готовой структуры можно посмотреть по [ссылке](./treasurer/).
