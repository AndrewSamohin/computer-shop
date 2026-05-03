# Computer Shop API — Тестовое задание

Backend-приложение магазина компьютеров и комплектующих с RESTful API.

## 📋 Описание проекта

Приложение управляет четырьмя типами товаров:

| Тип товара | Дополнительное поле |
|-----------|---------------------|
| Настольный компьютер | Форм-фактор (desktop, nettop, all-in-one) |
| Ноутбук | Размер экрана (13, 14, 15, 17 дюймов) |
| Монитор | Диагональ (дюймы) |
| Жёсткий диск | Объём (ГБ) |

Каждый товар имеет общие свойства:
- серийный номер (уникальный),
- производитель,
- цена,
- количество на складе.

## 🛠 Технологии

- **Java 17** (или выше)
- **Spring Boot 3.x** (Web, Data JPA, Validation)
- **H2 Database** (in-memory — данные хранятся, пока приложение запущено)
- **Maven** для сборки
- **Lombok** для сокращения шаблонного кода
- **SpringDoc OpenAPI** — Swagger UI для документации и тестирования API
- **JUnit 5 / Mockito** (опционально, для тестов)

## 🚀 Быстрый старт (собрать и запустить)

### 🔧 Предварительные требования

1. Установите **JDK 17+** ([скачать](https://adoptium.net/)).
2. Установите **Maven** (3.8+) или используйте Maven Wrapper, включённый в проект.

### 📦 Сборка

Из корня проекта выполните:

```
mvn clean package
```
### ▶️ Запуск
```
mvnw.cmd spring-boot:run
```

Приложение запускается на порту 8181.

### 🔍 Проверка работы
### Swagger UI (рекомендуется)
Откройте в браузере:

```
http://localhost:8181/swagger-ui/index.html
```

Здесь можно сразу отправлять запросы и смотреть ответы без дополнительных инструментов.


## 📡 Примеры запросов через curl

Ниже приведены примеры для ручного тестирования из терминала (PowerShell / Bash).

### Добавление товара

**Настольный компьютер (DESKTOP)**
```bash

curl -X POST http://localhost:8181/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "type": "DESKTOP",
    "serialNumber": "D-001",
    "manufacturer": "Dell",
    "price": 899.99,
    "quantity": 15,
    "formFactor": "DESKTOP"
  }'
```
✅ Ожидаемый ответ: 201 Created

**Монитор (MONITOR)**

```bash

curl -X POST http://localhost:8181/api/products \
-H "Content-Type: application/json" \
-d '{
"type": "MONITOR",
"serialNumber": "M-001",
"manufacturer": "Samsung",
"price": 299.50,
"quantity": 20,
"diagonal": 27.0
}'
```
✅ Ожидаемый ответ: 201 Created.

**Жёсткий диск (HARD_DRIVE)**

```bash

curl -X POST http://localhost:8181/api/products \
-H "Content-Type: application/json" \
-d '{
"type": "HARD_DRIVE",
"serialNumber": "HD-001",
"manufacturer": "Seagate",
"price": 89.99,
"quantity": 50,
"capacity": 1000
}'
```
✅ Ожидаемый ответ: 201 Created.

**Получение товара по ID**

```
curl http://localhost:8181/api/products/1
```
✅ Ответ 200 OK с полными данными товара.

❌ Если товара с таким id нет → 404 Not Found.

**Получение всех товаров заданного типа**
```
curl "http://localhost:8181/api/products?type=DESKTOP"
```
✅ Возвращает массив товаров указанного типа (может быть пустым []).

Допустимые значения type: DESKTOP, LAPTOP, MONITOR, HARD_DRIVE.

❌ Неверный тип → 400 Bad Request.

**Обновление товара**
```
curl -X PUT http://localhost:8181/api/products/1 \
-H "Content-Type: application/json" \
-d '{
"type": "DESKTOP",
"serialNumber": "D-001-v2",
"manufacturer": "Dell Inc.",
"price": 799.99,
"quantity": 10,
"formFactor": "NETTOP"
}'
```
✅ Ответ 200 OK с обновлённым товаром.

❌ Несуществующий id → 404 Not Found.

### ⚠️ Проверка ошибок
**Дубликат серийного номера**

Повторный POST с уже существующим serialNumber:
```
curl -X POST http://localhost:8181/api/products \
-H "Content-Type: application/json" \
-d '{
"type": "DESKTOP",
"serialNumber": "D-001",
"manufacturer": "HP",
"price": 500.00,
"quantity": 3,
"formFactor": "DESKTOP"
}'
```
❌ Ответ 409 Conflict.

**Неверный тип товара**

```
curl "http://localhost:8181/api/products?type=PHONE"
```
❌ Ответ 400 Bad Request.

**Пропущено обязательное поле**

```
curl -X POST http://localhost:8181/api/products \
-H "Content-Type: application/json" \
-d '{
"type": "DESKTOP",
"manufacturer": "Dell"
}'
```
❌ Ответ 400 Bad Request с перечнем ошибок валидации.