# 🚀 Firebase App Distribution - Happy Baby Style

Este documento explica cómo configurar y usar Firebase App Distribution para distribuir builds de testing de la aplicación Happy Baby Style.

## 📋 Configuración Inicial

### 1. Firebase Console Setup

1. Ve a [Firebase Console](https://console.firebase.google.com/project/happy-baby-style/appdistribution)
2. Navega a App Distribution en el menú lateral
3. Crea grupos de testing:
   - `happy-baby-style-testers` - Grupo principal de testers
   - `happy-baby-style-devs` - Desarrolladores
   - `happy-baby-style-stakeholders` - Stakeholders y product owners

### 2. Service Account Configuration

1. Ve a [Google Cloud Console](https://console.cloud.google.com/iam-admin/serviceaccounts?project=happy-baby-style)
2. Crea un service account con los siguientes roles:
   - `Firebase App Distribution Admin`
   - `Firebase Project Admin`
3. Genera una key JSON y guárdala como `app/firebase-service-account.json` (no committear este archivo)

### 3. Agregar Testers

```bash
# Agregar testers individuales
firebase appdistribution:testers:add test@example.com --project happy-baby-style

# Agregar testers a un grupo
firebase appdistribution:group:create happy-baby-style-testers --project happy-baby-style
firebase appdistribution:group:add-member happy-baby-style-testers test@example.com --project happy-baby-style
```

## 🛠️ Build Types Disponibles

### Debug
- **Package**: `com.mmaquera.happybabystyle.debug`
- **Propósito**: Testing diario y desarrollo
- **Minify**: Deshabilitado
- **Debuggable**: Sí

### Staging  
- **Package**: `com.mmaquera.happybabystyle.staging`
- **Propósito**: Testing pre-release y QA
- **Minify**: Deshabilitado
- **Debuggable**: Sí

### Release
- **Package**: `com.mmaquera.happybabystyle`
- **Propósito**: Builds de producción para testing final
- **Minify**: Habilitado
- **Debuggable**: No

## 🚀 Métodos de Distribución

### 1. Script Manual

```bash
# Build debug y distribuir
./scripts/deploy-to-firebase.sh debug

# Build staging con release notes custom
./scripts/deploy-to-firebase.sh staging "Nueva feature: Login mejorado"

# Build release
./scripts/deploy-to-firebase.sh release
```

### 2. Gradle Tasks

```bash
# Distribuir debug
./gradlew distributeDebugToFirebase

# Distribuir staging  
./gradlew distributeStagingToFirebase

# Upload directo con Gradle
./gradlew appDistributionUploadDebug
./gradlew appDistributionUploadStaging
./gradlew appDistributionUploadRelease
```

### 3. CI/CD Automático (GitHub Actions)

- **Push a `develop`**: Automáticamente distribuye debug build
- **Push a `staging`**: Automáticamente distribuye staging build  
- **Push a `main`**: Automáticamente distribuye release build
- **Manual Dispatch**: Permite elegir el build type y release notes

## 📝 Configuración de Release Notes

### Automáticas
- Se genera automáticamente con información del commit
- Incluye branch, autor, fecha y últimos cambios

### Manuales
- Edita `app/release-notes.txt` para notes por defecto
- Usa el parámetro del script para notes custom
- En GitHub Actions, usa el input `release_notes`

### Templates Recomendados

```markdown
🎯 [Nombre de la Feature]

✨ Nuevas funcionalidades:
- Feature 1
- Feature 2

🐛 Correcciones:
- Bug fix 1  
- Bug fix 2

📋 Para testing:
- Flujo 1 a probar
- Flujo 2 a probar  

⚠️ Problemas conocidos:
- Issue 1 (workaround: X)
```

## 🔧 Configuración de Entornos

### Variables de Entorno

```bash
# Service account file path
export FIREBASE_SERVICE_ACCOUNT_FILE="/path/to/service-account.json"

# Custom app ID (optional)
export FIREBASE_APP_ID="1:581901746036:android:5a5b5302d77d7ac73bf621"

# Custom groups
export FIREBASE_DISTRIBUTION_GROUPS="happy-baby-style-testers,happy-baby-style-devs"
```

### GitHub Secrets

Configura estos secrets en tu repositorio:

- `FIREBASE_SERVICE_ACCOUNT_JSON`: Contenido del archivo service account JSON

## 📱 Para Testers

### Instalación

1. Recibirás un email de Firebase App Distribution
2. Instala la app Firebase App Distribution desde Play Store
3. Usa el link del email para descargar el APK
4. Permite instalación de fuentes desconocidas si es necesario

### Reportar Issues

1. Toma screenshots de cualquier problema
2. Incluye información del dispositivo
3. Describe los pasos para reproducir
4. Usa el canal de Slack #happy-baby-testing

## 🔍 Troubleshooting

### Error: Service account not found
```bash
# Verificar que el archivo existe
ls -la app/firebase-service-account.json

# Verificar permisos
firebase auth:list --project happy-baby-style
```

### Error: Groups not found
```bash
# Listar grupos existentes
firebase appdistribution:group:list --project happy-baby-style

# Crear grupo faltante
firebase appdistribution:group:create happy-baby-style-testers --project happy-baby-style
```

### APK no se genera
```bash
# Limpiar y rebuild
./gradlew clean assembleDebug

# Verificar configuración
./gradlew tasks --all | grep distribution
```

## 📊 Monitoreo y Analytics

### Métricas Importantes
- Download rate por grupo
- Crash reports por build
- Feedback de testers
- Time to test (desde distribución hasta feedback)

### Firebase Console
- Visita regularmente [App Distribution Dashboard](https://console.firebase.google.com/project/happy-baby-style/appdistribution)
- Revisa métricas de adopción
- Monitor feedback de testers

## 🔄 Workflow Recomendado

1. **Desarrollo**: Usa debug builds para testing diario
2. **Feature Complete**: Distribuye staging build para QA
3. **Release Candidate**: Distribuye release build para testing final
4. **Post-Release**: Mantén debug builds para hotfixes

## 📞 Soporte

Para problemas con Firebase App Distribution:

- **Documentación**: [Firebase App Distribution Docs](https://firebase.google.com/docs/app-distribution)
- **Slack**: #happy-baby-support
- **Email**: dev-team@happybabystyle.com