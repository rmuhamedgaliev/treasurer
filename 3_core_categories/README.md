# 3. Категории

[![Build core categories](https://github.com/rmuhamedgaliev/treasurer/actions/workflows/3_core_categories_build.yml/badge.svg)](https://github.com/rmuhamedgaliev/treasurer/actions/workflows/3_core_categories_build.yml)

Для работы нашего приложения нам необходимо как-то категоризировать денежные транзакции. Для этого давайте в ядре проекта сделаем работу с категориями. Это задание не будет содержать подробных гайдов, а будет содержать задания. Которые необходимо реализовать. 

В первую очередь давайте для тестирования нашего проекта будем использовать [Junit 5](https://junit.org/junit5/). Для этого в файле [pom.xml](./treasurer/core/pom.xml) замените зависимость проекта на 

```xml
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
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

И исправив файл [AppTest](./treasurer/core/src/test/java/dev/rmuhamedgaliev/AppTest.java) в следующий вид:

```java
package dev.rmuhamedgaliev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testApp() {
        Assertions.assertTrue(true);
    }
}
```

Исправляем работу тестов. Если запустить `mvn test`, тесты должны успешно запустится и пройти. Давайте перейдем непосредственно к проекту. 

## Задания

Нам необходимо сделать следующие задания:

- в модель категории добавить поля такие как:
    - идентификатор
    - имя
    - вложенная категория
    - опциональное описание категории
- сделать CRUD сервис для работы с категорией
    - создание категории
        - валидация
        - дедупликация по имени
        - категория не может сама быть дочерней нодой себя
    - поиск категории по имени
    - удаление категории из списка
    - обновление категории по ID
- написать тесты на реализацию сервиса для категорий

Интерфейс для репозитория вы можете взять [этот](./treasurer/core/src/main/java/dev/rmuhamedgaliev/domain/category/CategoryRepository.java). Он вам поможет описать все необходимые методы.

Выполнив это задание обязательно надо проверить, что логика работает правильно. Для реализации этого задания сторонние библиотеки нам не нужны. Пример реализации можно найти [тут](./treasurer/core/src/main/java/dev/rmuhamedgaliev/domain/category/InMemoryCategory.java). Это пример самой простой реализации "в лоб" она может не учитывать крайних случаев. Но может быть полезной опорой для размышлений. Не стоит 1 в 1 дублировать эту логику. Буду ждать ваших оригинальных решений. Обязательно проверяйте свое решение тестами.
