import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	kotlin("plugin.jpa") version "1.4.32"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.0"

dependencies {
	/* Spring boot initialization */
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	/* Spring Cloud */
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	/* Datasource */
	runtimeOnly("com.h2database:h2:1.3.148")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	/* Util */
	implementation("org.modelmapper:modelmapper:2.3.8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	/* Kafka */
	implementation("org.springframework.kafka:spring-kafka")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
