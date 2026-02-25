plugins {
    id("application")  // Подключаем плагин application
    id("java")
}

// Указываем главный класс приложения
application {
    mainClass.set("app.Main")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.googlecode.lanterna:lanterna:3.1.2")
}

tasks.test {
    useJUnitPlatform()
}