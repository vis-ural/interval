docker build -t interval-api .

docker run -p 8080:8080 interval-api

OpenAPI Specification
http://localhost:8080/swagger-ui/index.html


# Добавить замкнутый интервал
curl -X POST "http://localhost:8080/api/addClosedInterval?x1=2.0&x2=5.0"

# Добавить объединенный интервал
curl -X POST "http://localhost:8080/api/addOpenInterval?x1=3.0&x2=7.0"

# Получить пересечения интервалов
curl -X GET "http://localhost:8080/api/getIntersections"

# Найти ближайшее число
curl -X GET "http://localhost:8080/api/findClosest?x=6.0"
