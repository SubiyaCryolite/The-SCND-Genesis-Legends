/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
import org.gradle.internal.os.OperatingSystem

plugins {
    java
    `java-library`
    application
}

group = "com.scndgen"
version = "26" // Major rewrite in 2026

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
    options.encoding = "UTF-8"
}

val mainClassName = "com.scndgen.legends.ScndGenLegends"

val lwjglNatives: String = when {
    OperatingSystem.current().isLinux -> {
        val osArch = System.getProperty("os.arch")
        if (osArch.startsWith("arm") || osArch.startsWith("aarch64")) {
            val suffix = if (osArch.contains("64") || osArch.startsWith("armv8")) "arm64" else "arm32"
            "natives-linux-$suffix"
        } else {
            "natives-linux"
        }
    }
    OperatingSystem.current().isMacOsX -> {
        if (System.getProperty("os.arch").startsWith("aarch64")) "natives-macos-arm64" else "natives-macos"
    }
    OperatingSystem.current().isWindows -> {
        val osArch = System.getProperty("os.arch")
        if (osArch.contains("64")) {
            "natives-windows${if (osArch.startsWith("aarch64")) "-arm64" else ""}"
        } else {
            "natives-windows-x86"
        }
    }
    else -> error("Unsupported OS for LWJGL natives")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:3.4.3"))
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("commons-io:commons-io:2.14.0")
    implementation(platform("tools.jackson:jackson-bom:3.2.2"))
    implementation("tools.jackson.core:jackson-databind")
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-nanovg")
    implementation("org.lwjgl:lwjgl-nuklear")
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-stb")
    runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-nanovg::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-nuklear::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")
}

application {
    mainClass.set(mainClassName)
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
            "Implementation-Title" to rootProject.name,
            "Implementation-Version" to project.version,
            "Created-By" to "${System.getProperty("java.version")} (${System.getProperty("java.specification.vendor")})",
            "Main-Class" to mainClassName,
        )
    }
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}
