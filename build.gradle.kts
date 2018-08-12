import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.amazonaws.services.lambda.model.Runtime
import jp.classmethod.aws.gradle.lambda.AWSLambdaMigrateFunctionTask

plugins {
    base

    java
    maven
    war

    kotlin("jvm") version "1.2.60"

    id("jp.classmethod.aws.lambda") version "0.30"
}

group = "org.stvad"
version = "0.1.0"

val lambdaFunctionName = "AlexaLifeAdviser"

description = """Alexa Life Adviser"""

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    jcenter()
}

dependencies {
    compile("com.amazon.alexa", "ask-sdk", "2.3.5")
    compile("org.jetbrains.kotlin", "kotlin-stdlib", "1.2.60")
    compile("com.github.debop:koda-time:1.2.1")

    testCompile("org.jetbrains.kotlin", "kotlin-test", "1.2.60")
    compile("javax.servlet", "javax.servlet-api", "3.0.1")
}

dependencies {
    compile("org.stvad", "kask", "0.1.0")
}

aws {
    profileName = "default"
    region = "eu-west-1"
}

val buildLambdaArchive by tasks.creating(Zip::class) {
    val compileJava: JavaCompile by tasks
    from(compileJava)

    val compileKotlin: KotlinCompile by tasks
    from(compileKotlin)

    val processResources: ProcessResources by tasks
    from(processResources)

    into("lib") {
        from(configurations.compileClasspath)
    }
}

tasks.create<AWSLambdaMigrateFunctionTask>("deployLambda") {
    dependsOn(buildLambdaArchive)
    functionName = lambdaFunctionName
    role = "arn:aws:iam::${aws.accountId}:role/service-role/alexa-life-adviser-lambda"
    runtime = Runtime.Java8
    zipFile = buildLambdaArchive.archivePath
    handler = "org.stvad.alexa.advice.AlexaLifeAdviserStreamHandler"
}
