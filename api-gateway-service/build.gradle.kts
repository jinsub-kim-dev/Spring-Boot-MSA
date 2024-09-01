import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
}

group = "com.example"
version = "1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.0"

dependencies {
	/* Spring boot initialization */
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("io.netty:netty-resolver-dns-native-macos")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	/* Spring Cloud */
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
	implementation("org.springframework.cloud:spring-cloud-starter-bus-amqp")

	/* Micrometer */
	implementation("io.micrometer:micrometer-registry-prometheus")

	/* Util */
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("com.sun.xml.bind:jaxb-impl:4.0.1")
	implementation("com.sun.xml.bind:jaxb-core:4.0.1")
	implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
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
