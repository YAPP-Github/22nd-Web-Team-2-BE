import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
}
dependencies {
    val embeddedRedisVersion: String by project
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("it.ozimov:embedded-redis:$embeddedRedisVersion")
}

allOpen { // 추가적으로 열어줄 allOpen
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
