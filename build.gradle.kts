import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.amazonaws.services.lambda.model.Runtime
import jp.classmethod.aws.gradle.lambda.AWSLambdaMigrateFunctionTask
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.stvad.kask.gradle.Kask

plugins {
    base
    java
    maven
    war

    kotlin("jvm") version "1.2.70"

    id("jp.classmethod.aws.lambda") version "0.30"
    id("org.stvad.kask") version "0.1.0"
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

tasks.withType<Kask> {
    packageName = "org.stvad.alexa.advice.model"
    modelPath.set(layout.projectDirectory.dir("models").file("en-US.json"))
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

aws {
    profileName = "default"
    region = "eu-west-1"
}

val buildLambdaArchive by tasks.creating(Zip::class) {
    val compileKotlin: KotlinCompile by tasks
    from(compileKotlin)

    val processResources: ProcessResources by tasks
    from(processResources)

    into("lib") {
        from(configurations.runtimeClasspath)
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

repositories {
    jcenter()
    maven(url = "https://jitpack.io")
}

dependencies {
    compile("com.amazon.alexa", "ask-sdk", "2.3.5")
    compile("org.jetbrains.kotlin", "kotlin-stdlib", "1.2.60")
    compile("com.github.debop:koda-time:1.2.1")
    implementation("com.github.Stvad:kask:-SNAPSHOT")

    compile("javax.servlet", "javax.servlet-api", "3.0.1")

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.9")
}

dependencies {
    implementation("org.stvad", "kask", "0.1.0")
}