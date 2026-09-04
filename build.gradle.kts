/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

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
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
    options.encoding = "UTF-8"
}

val mainClassName = "com.scndgen.legends.ScndGenLegends"

val lwjglNatives: String = when {
    OperatingSystem.current().isLinux -> {
        val osArch = System.getProperty("os.arch")
        when {
            osArch.startsWith("aarch64") || osArch == "arm64" -> "natives-linux-arm64"
            osArch.contains("64") -> "natives-linux"
            else -> error("Unsupported Linux architecture: $osArch (64-bit required)")
        }
    }
    OperatingSystem.current().isMacOsX -> {
        if (System.getProperty("os.arch").startsWith("aarch64")) "natives-macos-arm64" else "natives-macos"
    }
    OperatingSystem.current().isWindows -> {
        val osArch = System.getProperty("os.arch")
        when {
            osArch.startsWith("aarch64") -> "natives-windows-arm64"
            osArch.contains("64") -> "natives-windows"
            else -> error("Unsupported Windows architecture: $osArch (64-bit required)")
        }
    }
    else -> error("Unsupported OS for LWJGL natives")
}

val distClassifier: String = when (lwjglNatives) {
    "natives-linux" -> "linux-x64"
    "natives-windows" -> "windows-x64"
    "natives-macos" -> "macos-x64"
    else -> lwjglNatives.removePrefix("natives-")
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

val runtimeJars = configurations.runtimeClasspath

fun resolveBuildId(): String {
    val fromProp = findProperty("buildId")?.toString()?.trim().orEmpty()
    if (fromProp.isNotEmpty()) {
        return fromProp
    }
    return try {
        val proc = ProcessBuilder("git", "describe", "--always", "--dirty")
            .directory(rootDir)
            .redirectErrorStream(true)
            .start()
        val text = proc.inputStream.bufferedReader().readText().trim()
        if (proc.waitFor() == 0 && text.isNotEmpty()) "dev-$text" else "dev"
    } catch (_: Exception) {
        "dev"
    }
}

sourceSets.named("main") {
    resources.srcDir(layout.buildDirectory.dir("generated/resources"))
}

tasks.register("generateBuildId") {
    val output = layout.buildDirectory.file("generated/resources/text/build-id.txt")
    val id = provider { resolveBuildId() }
    inputs.property("buildId", id)
    outputs.file(output)
    doLast {
        val file = output.get().asFile
        file.parentFile.mkdirs()
        file.writeText(id.get())
    }
}

tasks.named<ProcessResources>("processResources") {
    dependsOn("generateBuildId")
}

tasks.jar {
    archiveBaseName.set("legends")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
            "Implementation-Title" to rootProject.name,
            "Implementation-Version" to project.version,
            "Created-By" to "${System.getProperty("java.version")} (${System.getProperty("java.specification.vendor")})",
            "Main-Class" to mainClassName,
        )
    }
    doFirst {
        val classPath = runtimeJars.get()
            .filter { it.extension.equals("jar", ignoreCase = true) }
            .sortedBy { it.name }
            .joinToString(" ") { "libs/${it.name}" }
        manifest.attributes["Class-Path"] = classPath
        manifest.attributes["Build-Id"] = resolveBuildId()
    }
}

tasks.register<Sync>("stageDistribution") {
    dependsOn(tasks.jar)
    into(layout.buildDirectory.dir("distribution/legends-${version}-$distClassifier"))
    from(tasks.jar)
    from(runtimeJars) {
        include("*.jar")
        into("libs")
    }
}

tasks.register<Zip>("packageZip") {
    group = "distribution"
    description = "Thin JAR plus libs/ as a zip"
    dependsOn("stageDistribution")
    archiveFileName.set("legends-$distClassifier.zip")
    destinationDirectory.set(layout.buildDirectory.dir("dist"))
    from(layout.buildDirectory.dir("distribution"))
}

tasks.assemble {
    dependsOn("packageZip")
}

tasks.test {
    failOnNoDiscoveredTests = false
}
