# 🔐 Configuración Google Auth + Ktor + Supabase

## ✅ Implementación Completa

La autenticación con Google usando **Ktor + Supabase** está 100% implementada. Solo faltan las **claves de configuración**.

---

## 🔑 Paso 1: Obtener Google Client ID

### 1.1 Ve a Google Cloud Console
- Abre: [console.cloud.google.com](https://console.cloud.google.com)
- **Selecciona tu proyecto** (o crea uno nuevo)

### 1.2 Habilitar Google Sign-In API
- **APIs & Services → Library**
- Busca: **"Google Sign-In API"**
- **Habilitar**

### 1.3 Crear Credenciales OAuth
- **APIs & Services → Credentials**
- **+ Create Credentials → OAuth 2.0 Client ID**
- **Application type:** Android
- **Package name:** `com.mmaquera.happybabystyle`
- **SHA-1 Certificate:** Obtén ejecutando:
  ```bash
  ./gradlew signingReport
  ```
  Copia el SHA-1 del certificado **debug**.

### 1.4 Copiar Client ID
- Una vez creado, copia el **Client ID**
- Tiene formato: `123456789-abcdefg.googleusercontent.com`

---

## 🔧 Paso 2: Obtener Claves de Supabase

### 2.1 Ve a Supabase Dashboard
- Abre: [supabase.com/dashboard](https://supabase.com/dashboard)
- **Selecciona tu proyecto:** `uumwjhoqkiiyxuperrws`

### 2.2 Obtener API Keys
- **Settings → API**
- Copia:
  - **URL:** `https://uumwjhoqkiiyxuperrws.supabase.co`
  - **anon public:** `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

---

## ⚙️ Paso 3: Actualizar Configuración

### 3.1 Editar AppConfig.kt
```kotlin
// app/src/main/java/com/mmaquera/happybabystyle/data/config/AppConfig.kt

object AppConfig {
    const val SUPABASE_URL = "https://uumwjhoqkiiyxuperrws.supabase.co"
    const val SUPABASE_ANON_KEY = "TU_CLAVE_ANON_AQUI" // ← CAMBIAR
    const val GOOGLE_CLIENT_ID = "TU_GOOGLE_CLIENT_ID_AQUI" // ← CAMBIAR
}
```

### 3.2 Configurar Google Services
- Descarga `google-services.json` desde Google Cloud Console
- Colócalo en: `app/google-services.json`

---

## 🚀 Paso 4: Probar la Implementación

### 4.1 Compilar y Ejecutar
```bash
./gradlew assembleDebug
./gradlew installDebug
```

### 4.2 Flujo de Autenticación
1. **Usuario toca "Continue with Google"**
2. **Se abre Google Sign-In**
3. **Usuario selecciona cuenta**
4. **Google devuelve ID Token**
5. **Ktor envía token a Supabase Auth**
6. **Supabase valida y devuelve JWT**
7. **AuthService crea/obtiene perfil en DB**
8. **Usuario autenticado exitosamente**

---

## 🔍 Debugging

### Logs útiles:
- **Ktor Client:** Configurado con `LogLevel.BODY`
- **Google Sign-In:** Revisar en Logcat
- **Supabase Auth:** Logs en Dashboard

### Errores comunes:
- **"Invalid Client ID":** Verificar Client ID y SHA-1
- **"Network Error":** Revisar permisos de internet
- **"Supabase Error":** Verificar claves y políticas RLS

---

## 📱 Características Implementadas

✅ **Google Sign-In** con Play Services  
✅ **Ktor HTTP Client** configurado para Supabase  
✅ **Autenticación JWT** con Bearer tokens  
✅ **Gestión de usuarios** en base de datos  
✅ **Inyección de dependencias** con Hilt  
✅ **Manejo de estados** en ViewModel  
✅ **UI reactiva** con Compose  
✅ **Persistencia de sesión**  
✅ **Logout** completo  

---

## 🎯 Próximos Pasos

Una vez configurado, puedes:

1. **Implementar Facebook/Apple Auth** siguiendo el mismo patrón
2. **Agregar persistencia local** con DataStore/Room
3. **Implementar refresh tokens** automático
4. **Agregar biometría** para acceso rápido
5. **Integrar con el resto de la app** (carrito, órdenes, etc.)

---

**¡Tu implementación está lista! Solo configura las claves y funcionará perfectamente.** 🎉