# QueryBuilder para JPA

[![Java](https://img.shields.io/badge/Java-7%2B-orange.svg)](https://www.oracle.com/java/)
[![JPA](https://img.shields.io/badge/JPA-1.0-blue.svg)](https://www.oracle.com/java/technologies/persistence-jsp.html)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

**QueryBuilder** es una API fluida para construir consultas JPA-QL de forma programática, inspirada en Hibernate Criteria API pero diseñada específicamente para JPA 1.0.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Guía Rápida](#-guía-rápida)
- [Ejemplos de Uso](#-ejemplos-de-uso)
- [API Principal](#-api-principal)
- [Mejores Prácticas](#-mejores-prácticas)
- [Tests](#-tests)
- [Arquitectura](#-arquitectura)
- [Contribución](#-contribución)

---

## ✨ Características

- **API Fluida**: Construcción de queries mediante method chaining
- **Type-Safe**: Construcción de consultas con validación de tipos
- **Prevención de SQL Injection**: Uso de parámetros vinculados
- **Expresiones Complejas**: Soporte para condiciones anidadas (AND, OR, NOT)
- **Funciones SQL**: AVG, SUM, COUNT, MAX, MIN, etc.
- **Joins**: INNER, LEFT, FULL JOIN
- **Agrupamiento y Ordenamiento**: GROUP BY, HAVING, ORDER BY
- **Paginación**: LIMIT, OFFSET
- **Transformadores**: Transformación de resultados customizable
- **Encapsulamiento Robusto**: Vistas inmutables para prevenir modificaciones accidentales
- **Validaciones Exhaustivas**: Validación de parámetros nulos y valores inválidos

---

## 📦 Requisitos

- **Java**: 7 o superior
- **JPA**: 1.0 o superior
- **JUnit**: 4.x (para tests)

---

## 🚀 Instalación

### Maven

```xml
<dependency>
    <groupId>com.querybuilder</groupId>
    <artifactId>querybuilder</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'com.querybuilder:querybuilder:1.0.0'
```

### Manual

1. Descarga el JAR desde [releases](../../releases)
2. Agrega el JAR al classpath de tu proyecto

---

## 🎯 Guía Rápida

```java
import static com.querybuilder.expression.ExpressionFactory.*;
import com.querybuilder.QueryCreator;
import javax.persistence.EntityManager;

// Inicializar QueryCreator con EntityManager
QueryCreator qc = QueryCreator.init(entityManager);

// Construir y ejecutar consulta
List<Usuario> usuarios = qc
    .select(get(path("u.nombre"), "nombre"), get(path("u.email"), "email"))
    .from(entity(Usuario.class, "u"))
    .whereAll(
        eq("u.activo", true),
        gt("u.edad", 18)
    )
    .orderBy("u.nombre asc")
    .all();
```

---

## 📖 Ejemplos de Uso

### Ejemplo 1: Consulta Simple

```java
QueryCreator qc = QueryCreator.init(entityManager);

List<Producto> productos = qc
    .select(get(path("p"), "producto"))
    .from(entity(Producto.class, "p"))
    .whereAll(eq("p.disponible", true))
    .all();
```

**SQL Generado:**
```sql
SELECT p as producto
FROM Producto p
WHERE ( p.disponible = :e0 )
```

### Ejemplo 2: Consulta con Múltiples Condiciones (AND)

```java
List<Usuario> usuarios = qc
    .select(get(path("u"), "usuario"))
    .from(entity(Usuario.class, "u"))
    .whereAll(
        eq("u.activo", true),
        gt("u.edad", 18),
        like("u.email", "%@example.com")
    )
    .all();
```

**SQL Generado:**
```sql
SELECT u as usuario
FROM Usuario u
WHERE ( u.activo = :e0 AND u.edad > :e1 AND u.email LIKE :e2 )
```

### Ejemplo 3: Consulta con Condiciones OR

```java
List<Pedido> pedidos = qc
    .select(get(path("p"), "pedido"))
    .from(entity(Pedido.class, "p"))
    .whereAny(
        eq("p.estado", "PENDIENTE"),
        eq("p.estado", "PROCESANDO")
    )
    .all();
```

**SQL Generado:**
```sql
SELECT p as pedido
FROM Pedido p
WHERE ( p.estado = :e0 OR p.estado = :e1 )
```

### Ejemplo 4: Consulta con JOIN

```java
List<Object[]> resultado = qc
    .select(
        get(path("u.nombre"), "nombre"),
        get(path("d.nombre"), "departamento")
    )
    .from(entity(Usuario.class, "u"))
    .join(joinTo("u.departamento", "d", JoinType.LEFT))
    .whereAll(eq("u.activo", true))
    .all();
```

**SQL Generado:**
```sql
SELECT u.nombre as nombre, d.nombre as departamento
FROM Usuario u
LEFT JOIN u.departamento as d
WHERE ( u.activo = :e0 )
```

### Ejemplo 5: Consulta con GROUP BY y HAVING

```java
List<Object[]> estadisticas = qc
    .select(
        get(path("p.categoria"), "categoria"),
        get(count("p.id"), "total"),
        get(avg("p.precio"), "promedio")
    )
    .from(entity(Producto.class, "p"))
    .groupBy("p.categoria")
    .havingAll(gt("count(p.id)", 5))
    .orderBy("total desc")
    .all();
```

**SQL Generado:**
```sql
SELECT p.categoria as categoria, COUNT(p.id) as total, AVG(p.precio) as promedio
FROM Producto p
GROUP BY p.categoria
HAVING ( count(p.id) > :e0 )
ORDER BY total desc
```

### Ejemplo 6: Consulta con Paginación

```java
// Obtener 10 resultados a partir del registro 20
List<Producto> productos = qc
    .select(get(path("p"), "producto"))
    .from(entity(Producto.class, "p"))
    .orderBy("p.nombre asc")
    .take(20, 10)  // offset: 20, limit: 10
    .all();

// Alternativa usando startAt y limit
List<Producto> productos2 = qc
    .select(get(path("p"), "producto"))
    .from(entity(Producto.class, "p"))
    .orderBy("p.nombre asc")
    .startAt(20)
    .limit(10)
    .all();
```

### Ejemplo 7: Condiciones Anidadas Complejas

```java
// (activo = true AND edad > 18) OR (vip = true)
ConditionExpression condicion = any(
    all(
        eq("u.activo", true),
        gt("u.edad", 18)
    ),
    eq("u.vip", true)
);

List<Usuario> usuarios = qc
    .select(get(path("u"), "usuario"))
    .from(entity(Usuario.class, "u"))
    .whereAll(condicion)
    .all();
```

### Ejemplo 8: Consulta con IN

```java
List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5);

List<Producto> productos = qc
    .select(get(path("p"), "producto"))
    .from(entity(Producto.class, "p"))
    .whereAll(in("p.id", ids))
    .all();
```

**SQL Generado:**
```sql
SELECT p as producto
FROM Producto p
WHERE ( p.id IN :e0 )
```

### Ejemplo 9: Obtener un Único Resultado

```java
// Obtener un único resultado (lanza excepción si hay más de uno)
Usuario usuario = qc
    .select(get(path("u"), "usuario"))
    .from(entity(Usuario.class, "u"))
    .whereAll(eq("u.id", 123))
    .one();

// Obtener el primer resultado
Usuario primerUsuario = qc
    .select(get(path("u"), "usuario"))
    .from(entity(Usuario.class, "u"))
    .whereAll(eq("u.activo", true))
    .orderBy("u.nombre asc")
    .first();
```

### Ejemplo 10: Transformación de Resultados

```java
Transformer<UsuarioDTO> transformer = new AbstractTransformer<UsuarioDTO>() {
    @Override
    public UsuarioDTO transform(Object o) {
        Object[] row = (Object[]) o;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre((String) row[0]);
        dto.setEmail((String) row[1]);
        return dto;
    }
};

List<UsuarioDTO> usuarios = qc
    .select(
        get(path("u.nombre"), "nombre"),
        get(path("u.email"), "email")
    )
    .from(entity(Usuario.class, "u"))
    .whereAll(eq("u.activo", true))
    .all(transformer);
```

---

## 🔧 API Principal

### QueryCreator

Clase principal para construir queries con interfaz fluida.

#### Métodos de Inicialización

```java
QueryCreator init(EntityManager entityManager)
```

#### Métodos de Construcción

| Método | Descripción |
|--------|-------------|
| `select(Select... selects)` | Define las columnas a seleccionar |
| `from(From... froms)` | Define las entidades raíz |
| `join(Join... joins)` | Agrega joins (INNER, LEFT, FULL) |
| `whereAll(ConditionExpression... conditions)` | Condiciones con AND |
| `whereAny(ConditionExpression... conditions)` | Condiciones con OR |
| `groupBy(String... fields)` | Agrega GROUP BY |
| `havingAll(ConditionExpression... conditions)` | Condiciones HAVING con AND |
| `havingAny(ConditionExpression... conditions)` | Condiciones HAVING con OR |
| `orderBy(String... fields)` | Agrega ORDER BY |
| `take(int offset, int limit)` | Paginación (offset + limit) |
| `startAt(int offset)` | Define offset para paginación |
| `limit(int limit)` | Define límite de resultados |

#### Métodos de Ejecución

| Método | Descripción |
|--------|-------------|
| `all()` | Retorna lista de resultados |
| `one()` | Retorna un único resultado |
| `first()` | Retorna el primer resultado |
| `all(Transformer<T> transformer)` | Retorna lista transformada |
| `one(Transformer<T> transformer)` | Retorna un resultado transformado |
| `first(Transformer<T> transformer)` | Retorna el primer resultado transformado |

### ExpressionFactory

Métodos estáticos para crear expresiones.

#### Operadores de Comparación

```java
SimpleCondition eq(String property, Object value)        // Igual
SimpleCondition ne(String property, Object value)        // No igual
SimpleCondition gt(String property, Object value)        // Mayor que
SimpleCondition ge(String property, Object value)        // Mayor o igual
SimpleCondition lt(String property, Object value)        // Menor que
SimpleCondition le(String property, Object value)        // Menor o igual
SimpleCondition like(String property, Object value)      // LIKE
SimpleCondition notLike(String property, Object value)   // NOT LIKE
```

#### Operadores de Nulidad

```java
SimpleCondition isNull(String property)                  // IS NULL
SimpleCondition isNoNull(String property)                // IS NOT NULL
```

#### Operadores de Conjunto

```java
SimpleCondition in(String property, Object value)        // IN
SimpleCondition notin(String property, Object value)     // NOT IN
```

#### Operadores Lógicos

```java
ConditionExpression all(ConditionExpression... conditions)  // AND
ConditionExpression any(ConditionExpression... conditions)  // OR
ConditionExpression not(ConditionExpression condition)      // NOT
```

#### Funciones de Agregación

```java
FunctionExpression count(String expression)
FunctionExpression sum(String expression)
FunctionExpression avg(String expression)
FunctionExpression max(String expression)
FunctionExpression min(String expression)
```

#### Expresiones de Valor

```java
ValueExpression value(Object value)                      // Valor parametrizado
PathExpression path(String path)                         // Path de propiedad
LiteralExpression literal(String literal)                // Literal SQL
```

#### Selectores y Entidades

```java
Select get(Expression expression, String alias)          // SELECT expression AS alias
From entity(Class<?> entityClass, String alias)          // FROM Entity AS alias
Join joinTo(String path, String alias, JoinType type)    // JOIN path AS alias
```

---

## 💡 Mejores Prácticas

### 1. Siempre Usar Parámetros Vinculados

✅ **Correcto:**
```java
whereAll(eq("u.nombre", nombreUsuario))  // Usa parámetros vinculados
```

❌ **Incorrecto:**
```java
whereAll(literal("u.nombre = '" + nombreUsuario + "'"))  // Vulnerable a SQL injection
```

### 2. Validar Parámetros de Entrada

```java
if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
    throw new IllegalArgumentException("Nombre de usuario requerido");
}

List<Usuario> usuarios = qc
    .select(get(path("u"), "usuario"))
    .from(entity(Usuario.class, "u"))
    .whereAll(eq("u.nombre", nombreUsuario))
    .all();
```

### 3. No Reutilizar QueryCreator

❌ **Incorrecto:**
```java
QueryCreator qc = QueryCreator.init(entityManager);
List<Usuario> usuarios1 = qc.from(...).all();
List<Producto> productos = qc.from(...).all();  // Estado corrupto
```

✅ **Correcto:**
```java
QueryCreator qc1 = QueryCreator.init(entityManager);
List<Usuario> usuarios = qc1.from(...).all();

QueryCreator qc2 = QueryCreator.init(entityManager);
List<Producto> productos = qc2.from(...).all();
```

### 4. Usar Transformadores para DTOs

```java
// En lugar de mapear manualmente
List<Object[]> results = qc.select(...).all();
List<UsuarioDTO> dtos = new ArrayList<>();
for (Object[] row : results) {
    UsuarioDTO dto = new UsuarioDTO();
    dto.setNombre((String) row[0]);
    dtos.add(dto);
}

// Usar transformador
List<UsuarioDTO> dtos = qc.select(...).all(new UsuarioDTOTransformer());
```

### 5. Aprovechar el Encapsulamiento

El `QueryObject` retornado por `getQueryObject()` tiene vistas inmutables:

```java
QueryObject qo = qc.getQueryObject();
qo.getSelects();  // ✅ Lectura permitida
qo.getSelects().clear();  // ❌ Lanza UnsupportedOperationException
```

### 6. Ordenar Siempre con Paginación

```java
// Siempre usar ORDER BY con paginación para resultados consistentes
List<Producto> productos = qc
    .select(get(path("p"), "producto"))
    .from(entity(Producto.class, "p"))
    .orderBy("p.id asc")  // ← Importante para paginación
    .startAt(0)
    .limit(10)
    .all();
```

---

## 🧪 Tests

El proyecto incluye una suite completa de tests con cobertura ~70-80%.

### Ejecutar Tests

```bash
# Maven
mvn test

# Gradle
gradle test
```

### Estructura de Tests

```
test/
├── com/querybuilder/
│   ├── QueryCreatorValidationTest.java    # Tests de validaciones
│   ├── expression/
│   │   ├── BugFixTest.java                # Tests de bugs corregidos
│   │   ├── ConditionTest.java             # Tests de condiciones
│   │   └── QueryBuilderTest.java          # Tests generales
│   └── query/
│       └── QueryObjectEncapsulationTest.java  # Tests de encapsulamiento
```

### Categorías de Tests

- **Tests Funcionales**: Verifican funcionalidad correcta
- **Tests de Regresión**: Previenen reaparición de bugs
- **Tests de Validación**: Verifican validaciones de entrada
- **Tests de Seguridad**: Verifican encapsulamiento
- **Tests de Edge Cases**: Casos límite y situaciones especiales

---

## 🏗️ Arquitectura

### Patrón de Diseño: Interpreter

El proyecto implementa el patrón Interpreter:

- **Context**: `QueryObject` - mantiene el estado de la consulta
- **Abstract Expression**: `Expression` - interfaz para todas las expresiones
- **Concrete Expressions**: Implementaciones específicas (WHERE, SELECT, etc.)

### Componentes Principales

```
com.querybuilder/
├── QueryCreator.java              # API fluida principal
├── query/
│   ├── QueryObject.java           # Context (estado de la consulta)
│   ├── Select.java                # DTO para SELECT
│   ├── From.java                  # DTO para FROM
│   └── Join.java                  # DTO para JOIN
├── expression/
│   ├── Expression.java            # Interfaz base
│   ├── QueryExpression.java       # Parser principal
│   ├── ConditionExpression.java   # Base para condiciones
│   ├── ValueExpression.java       # Valores parametrizados
│   ├── PathExpression.java        # Paths de propiedades
│   ├── FunctionExpression.java    # Funciones SQL
│   ├── ExpressionFactory.java     # Factory para crear expresiones
│   ├── conditions/                # Implementaciones de condiciones
│   │   ├── SimpleCondition.java
│   │   ├── AllCondition.java      # AND
│   │   ├── AnyCondition.java      # OR
│   │   └── NotCondition.java      # NOT
│   └── clausules/                 # Expresiones de cláusulas
│       ├── SelectExpression.java
│       ├── FromExpression.java
│       ├── WhereExpression.java
│       ├── JoinExpression.java
│       ├── GroupExpression.java
│       ├── HavingExpression.java
│       └── OrderExpression.java
└── transformer/
    ├── Transformer.java           # Interfaz para transformación
    └── AbstractTransformer.java   # Implementación base
```

### Características Arquitecturales

- **Encapsulamiento**: Vistas inmutables en getters públicos
- **Validación**: Validaciones exhaustivas en todos los métodos
- **Prevención de SQL Injection**: Uso obligatorio de parámetros vinculados
- **Fail-Fast**: Validaciones tempranas con excepciones claras
- **Fluent API**: Method chaining para construcción intuitiva

---

## 🤝 Contribución

Las contribuciones son bienvenidas. Por favor:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Guías de Contribución

- Sigue el estilo de código existente
- Agrega tests para nuevas funcionalidades
- Actualiza la documentación según sea necesario
- Asegúrate de que todos los tests pasen

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 🙏 Agradecimientos

- Inspirado en Hibernate Criteria API
- Construido para JPA 1.0
- Mejorado con contribuciones de la comunidad

---

## 📞 Contacto

- **Autor**: rtincar
- **Issues**: [GitHub Issues](../../issues)
- **Pull Requests**: [GitHub PRs](../../pulls)

---

## 🔄 Changelog

### Versión 1.0.0 (Actual)

#### ✨ Características
- API fluida para construcción de queries
- Soporte completo para JPA-QL
- Encapsulamiento robusto con vistas inmutables
- Validaciones exhaustivas
- Suite completa de tests (cobertura ~70-80%)

#### 🐛 Bugs Corregidos
- HavingExpression: Eliminado operador extra al final
- WhereExpression: Manejo correcto de condiciones vacías
- JoinExpression: Refactorizado a if-else-if para eficiencia

#### 🏗️ Mejoras Arquitecturales
- Vistas inmutables en getters públicos
- Métodos package-private para acceso interno
- API pública controlada (addParameter, addAllParameters)
- JavaDoc exhaustivo con ejemplos

#### 🧹 Limpieza de Código
- Eliminado código duplicado
- Eliminados TODOs auto-generados
- Eliminado código comentado
- Mejorada documentación

---

**¡Disfruta construyendo queries dinámicas con QueryBuilder!** 🚀
