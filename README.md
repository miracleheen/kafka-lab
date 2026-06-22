# kafka-lab

Учебный multi-module Maven проект для работы с Apache Kafka.

Проект состоит из двух Spring Boot сервисов:

- `order-service` — принимает HTTP-запрос и публикует `Order` в Kafka
- `warehouse-service` — читает `Order` из Kafka и обрабатывает его

## Стек

- Java 21
- Maven multi-module
- Spring Boot
- Apache Kafka
- Docker Compose

## Структура проекта

```text
kafka-lab/
├── order-service/
├── warehouse-service/
└── pom.xml
```

## Требования

- JDK 21
- Maven
- Docker
- Docker Compose
- Apache Bench (`ab`) — только для нагрузочного теста

### Установка `ab`

**Windows (Chocolatey)**
```powershell
choco install apache-httpd
```

**Ubuntu / Debian**
```bash
sudo apt update
sudo apt install apache2-utils
```

## Запуск Kafka

Из корня проекта:

```bash
docker compose up -d
```

Kafka будет доступна на:

```text
localhost:9092
```

## Сборка проекта

Из корня проекта:

```bash
mvn clean install
```

## Запуск сервисов

### `order-service`

```bash
mvn -pl order-service spring-boot:run
```

### `warehouse-service`

```bash
mvn -pl warehouse-service spring-boot:run
```

## Проверка работы

### Отправка одного заказа

```http
POST http://localhost:8080/orders
Content-Type: application/json

{
  "product": "product-name",
  "quantity": 10
}
```

### Нагрузочный тест

Файл `request.json`:

```json
{
  "product": "product-name",
  "quantity": 10
}
```

Команда:

```bash
ab -n 1000 -c 10 -T "application/json" -p request.json http://localhost:8080/orders
```

Для этой команды нужен установленный `ab`.

## Поток данных

1. `order-service` принимает HTTP-запрос
2. `order-service` публикует `Order` в topic `order-topic`
3. `warehouse-service` читает сообщение из Kafka
4. Полученное сообщение логируется в консоль

## Логи

При успешной отправке `order-service` пишет подтверждение записи в Kafka.  
`warehouse-service` пишет факт получения сообщения.

## Примечание: 

Проект намеренно упрощён и сфокусирован на базовом понимании Kafka как брокера сообщений. Здесь показан минимальный рабочий сценарий взаимодействия между producer и consumer без лишней инфраструктурной сложности.
