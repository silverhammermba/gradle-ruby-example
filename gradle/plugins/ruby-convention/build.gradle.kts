plugins {
    `kotlin-dsl`
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

val functionalTest by sourceSets.creating

val functionalTestTask = tasks.register<Test>("functionalTest") {
    description = "Runs the functional tests."
    group = "verification"
    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    useJUnitPlatform()
    mustRunAfter(tasks.test)
}

tasks.check {
    dependsOn(functionalTestTask)
}

gradlePlugin {
    testSourceSets(functionalTest)
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.1")
    "functionalTestImplementation"("junit:junit:4.13.2")
    "functionalTestRuntimeOnly"("org.junit.vintage:junit-vintage-engine:5.9.1")
}
