tasks.register("check") {
    description = "Runs all checks."
    group = "verification"
    dependsOn(subprojects.map { ":${it.name}:check" })
}
