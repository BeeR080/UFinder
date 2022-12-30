import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

group = "me.user"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)

}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "UFinder"
            packageVersion = "1.0.0"
            description = "Find users in company"
            copyright = "© 2023 by BeeRkA. All rights reserved."
            vendor = "ABN Proggers"


            windows{
                iconFile.set(project.file("search_user.ico"))

                packageVersion = "1.0.0"

                msiPackageVersion = "1.0.0"

                exePackageVersion = "1.0.0"
        }
        }
    }
}