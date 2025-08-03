#!/bin/bash

# Firebase App Distribution Deployment Script
# Usage: ./scripts/deploy-to-firebase.sh [debug|staging|release] [release-notes]

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Default values
BUILD_TYPE="debug"
RELEASE_NOTES=""
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [BUILD_TYPE] [RELEASE_NOTES]"
    echo ""
    echo "BUILD_TYPE:"
    echo "  debug     - Debug build (default)"
    echo "  staging   - Staging build"
    echo "  release   - Release build"
    echo ""
    echo "RELEASE_NOTES:"
    echo "  Custom release notes (optional, uses default from release-notes.txt)"
    echo ""
    echo "Examples:"
    echo "  $0 debug"
    echo "  $0 staging \"New feature: improved UI\""
    echo "  $0 release"
}

# Parse arguments
if [[ $# -gt 0 ]]; then
    BUILD_TYPE="$1"
fi

if [[ $# -gt 1 ]]; then
    RELEASE_NOTES="$2"
fi

# Validate build type
if [[ ! "$BUILD_TYPE" =~ ^(debug|staging|release)$ ]]; then
    print_error "Invalid build type: $BUILD_TYPE"
    show_usage
    exit 1
fi

# Check if we're in the right directory
if [[ ! -f "$PROJECT_DIR/app/build.gradle.kts" ]]; then
    print_error "Please run this script from the project root directory"
    exit 1
fi

# Note: Using Gradle tasks instead of Firebase CLI for better integration

print_status "🚀 Starting Firebase App Distribution deployment"
print_status "Build Type: $BUILD_TYPE"
print_status "Project Dir: $PROJECT_DIR"

# Change to project directory
cd "$PROJECT_DIR"

# Clean previous builds
print_status "🧹 Cleaning previous builds..."
./gradlew clean

# Build the APK
print_status "🔨 Building $BUILD_TYPE APK..."
if [[ "$BUILD_TYPE" == "debug" ]]; then
    ./gradlew assembleDebug
elif [[ "$BUILD_TYPE" == "staging" ]]; then
    ./gradlew assembleStaging
else
    ./gradlew assembleRelease
fi

# Find the generated APK
APK_PATH=""
if [[ "$BUILD_TYPE" == "debug" ]]; then
    APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
elif [[ "$BUILD_TYPE" == "staging" ]]; then
    APK_PATH="app/build/outputs/apk/staging/app-staging.apk"
else
    APK_PATH="app/build/outputs/apk/release/app-release.apk"
fi

if [[ ! -f "$APK_PATH" ]]; then
    print_error "APK not found at $APK_PATH"
    exit 1
fi

print_success "✅ APK built successfully: $APK_PATH"

# Prepare release notes
print_status "☁️  Uploading to Firebase App Distribution..."
if [[ -n "$RELEASE_NOTES" ]]; then
    echo "$RELEASE_NOTES" > temp-release-notes.txt
    print_status "📝 Using custom release notes: $RELEASE_NOTES"
else
    print_status "📝 Using default release notes from app/release-notes.txt"
fi

# Execute the distribution using Gradle tasks
if [[ "$BUILD_TYPE" == "debug" ]]; then
    print_status "🔨 Deploying debug build..."
    ./gradlew distributeDebugToFirebase
elif [[ "$BUILD_TYPE" == "staging" ]]; then
    print_status "🔨 Deploying staging build..."
    ./gradlew distributeStagingToFirebase
else
    print_status "🔨 Deploying release build..."
    ./gradlew appDistributionUploadRelease
fi

# Clean up temporary files
if [[ -f "temp-release-notes.txt" ]]; then
    rm temp-release-notes.txt
fi

print_success "🎉 Successfully deployed $BUILD_TYPE build to Firebase App Distribution!"
print_status "📱 Testers in 'happy-baby-style-testers' group will receive a notification"
print_status "🔗 Check Firebase Console for distribution details: https://console.firebase.google.com/project/happy-baby-style/appdistribution"

echo ""
print_status "Next steps:"
echo "  1. Check Firebase Console to verify the upload"
echo "  2. Add testers to the 'happy-baby-style-testers' group if needed"
echo "  3. Monitor feedback from testers"
echo "  4. Update release notes for future deployments"