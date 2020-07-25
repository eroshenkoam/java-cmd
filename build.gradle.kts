plugins {
    java
    id("com.palantir.graal") version("0.6.0")
}

group = "io.github.eroshenkoam"
version = "1.0-SNAPSHOT"

tasks.withType(Wrapper::class) {
    gradleVersion = "6.1.1"
}

graal {
    mainClass("io.github.eroshenkoam.cmd.Tester")
    outputName("tester")
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen:4.4.0")
    implementation("info.picocli:picocli:4.4.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

}