/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven("https://jitpack.io")
        mavenCentral()
    }
}
rootProject.name = "medical-assistant"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:common")
include(":core:analytics")
include(":core:database")
include(":core:datastore")
include(":core:datastore-test")
include(":core:data")
include(":core:domain")

include(":core:network")
include(":core:data-test")
include(":core:designsystem")
include(":core:ui")
include(":core:model")
include(":core:testing")

include(":feature:home")
include(":feature:records")
include(":feature:me")
include(":feature:settings")

/*
include(":benchmarks")
include(":lint")
include(":sync:work")
include(":sync:sync-test")
include(":ui-test-hilt-manifest")*/
include(":feature:voice")
include(":feature:playback")
include(":feature:login")
