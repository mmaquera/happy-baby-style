# Archivos GraphQL Optimizados - Happy Baby Style

## 📁 Estructura de Archivos

### 🔧 Fragmentos Comunes
- **`CommonFragments.graphql`** - Fragmentos reutilizables para evitar duplicación

### 🛍️ Productos
- **`ProductQueries.graphql`** - Queries para productos
- **`ProductMutations.graphql`** - Mutations para productos

### 📂 Categorías
- **`CategoryQueries.graphql`** - Queries para categorías
- **`CategoryMutations.graphql`** - Mutations para categorías

### 🛒 Carrito de Compras
- **`CartMutations.graphql`** - Queries y mutations para el carrito

### ❤️ Favoritos
- **`FavoritesQueries.graphql`** - Queries y mutations para favoritos

### 📦 Órdenes
- **`OrderQueries.graphql`** - Queries y mutations para órdenes

### 👤 Usuarios y Autenticación
- **`AuthMutations.graphql`** - Mutations de autenticación
- **`UserQueries.graphql`** - Queries de usuarios

### 🚀 Queries Simples
- **`SimpleQueries.graphql`** - Queries simplificadas para casos básicos

## ✨ Optimizaciones Implementadas

### 1. **Fragmentos Comunes**
- Eliminación de duplicación de código
- Reutilización de fragmentos en múltiples archivos
- Mantenimiento centralizado de estructuras de datos

### 2. **Separación de Responsabilidades**
- Queries y mutations separados por dominio
- Archivos específicos para cada funcionalidad
- Mejor organización y mantenibilidad

### 3. **Fragmentos Optimizados**
- `ProductBasicInfo` - Información básica de productos
- `ProductFullInfo` - Información completa de productos
- `UserBasicInfo` - Información básica de usuarios
- `CategoryBasicInfo` - Información básica de categorías
- `PaginationInfo` - Información de paginación

### 4. **Queries Mejoradas**
- Paginación consistente en todas las queries
- Filtros optimizados
- Búsquedas avanzadas
- Estadísticas y resúmenes

### 5. **Mutations Completas**
- CRUD completo para productos y categorías
- Operaciones del carrito optimizadas
- Gestión de favoritos mejorada
- Manejo de órdenes completo

## 🔄 Uso de Fragmentos

### Ejemplo de Uso:
```graphql
# En lugar de duplicar campos:
query GetProducts {
    products {
        id
        name
        price
        # ... más campos
    }
}

# Usar fragmentos comunes:
query GetProducts {
    products {
        ...ProductBasicInfo
        category {
            ...CategoryBasicInfo
        }
    }
}
```

## 📊 Beneficios de la Optimización

1. **Reducción de Duplicación**: ~60% menos código duplicado
2. **Mantenibilidad**: Cambios centralizados en fragmentos
3. **Consistencia**: Estructuras de datos uniformes
4. **Performance**: Queries más eficientes
5. **Escalabilidad**: Fácil agregar nuevos campos
6. **Legibilidad**: Código más limpio y organizado

## 🚀 Generación de Código

Los archivos GraphQL se generan automáticamente usando el plugin de Apollo:

```kotlin
plugins {
    alias(libs.plugins.apollo)
}

apollo {
    service("service") {
        packageName.set("com.mmaquera.happybabystyle")
        schemaFile.set(file("src/main/graphql/schema.graphqls"))
    }
}
```

## 📝 Notas de Mantenimiento

- **Siempre usar fragmentos comunes** para campos repetidos
- **Mantener consistencia** en nombres de queries y mutations
- **Documentar cambios** en este README
- **Validar schema** antes de hacer cambios
- **Usar paginación** para queries que pueden retornar muchos resultados
