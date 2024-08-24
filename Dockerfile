# Используем официальный образ OpenJDK
FROM openjdk:11-jre-slim

# Скопировать файл JAR в контейнер
COPY target/interval-0.1.0.jar interval-api.jar

# Указать команду для запуска приложения
ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-jar", "interval-api.jar"]

# Пример значения порта, на котором будет работать ваше приложение
EXPOSE 8080