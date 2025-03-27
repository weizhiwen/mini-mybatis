plugins {
    id("java")
}

group = "com.shixin.mybatis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.hutool:hutool-core:5.8.36")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

//配置测试目录到类路径
sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
}