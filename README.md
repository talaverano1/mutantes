# 🧬 Mutant Detector API - Examen Técnico MercadoLibre

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Tests](https://img.shields.io/badge/Tests-35%20passing-success.svg)]()
[![Coverage](https://img.shields.io/badge/Coverage-86%25-green.svg)]()

> 📚 **Proyecto Integrador**: API RESTful que implementa un sistema para detectar si una persona es mutante analizando su secuencia de ADN (matriz NxN de A, T, C, G). El proyecto prioriza la **optimización algorítmica** y la **calidad del código**.

---

## 📋 Tabla de Contenidos

1. [Prerequisitos](#-prerequisitos)
2. [Instalación y Ejecución](#-instalación-y-ejecución)
3. [API Endpoints](#-api-endpoints)
4. [Arquitectura y Optimización](#-arquitectura-y-optimización)
5. [Testing y Cobertura](#-testing-y-cobertura)
6. [Diagrama de Secuencia](#-diagrama-de-secuencia)

---

## 📦 Prerequisitos

Asegúrate de tener instalado el siguiente software:

* **Java JDK:** Versión 17 o superior.
* **Git:** Para clonar el repositorio.
* **Gradle:** (Opcional, incluido con el wrapper `./gradlew`).
* **Postman o Swagger:** Para probar los *endpoints*.

---

## 🚀 Instalación y Ejecución

La aplicación utiliza **Spring Boot 3** y **Gradle**. La base de datos H2 se inicializa en memoria al inicio de la aplicación.

### Paso 1: Clonar el Repositorio

```bash
git clone [https://github.com/talaverano1/mutantes](https://github.com/talaverano1/mutantes)
cd mutantes
