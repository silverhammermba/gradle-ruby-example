package com.max.ruby

import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.nio.file.Paths

abstract class FetchGems : Exec() {
    @get:Input
    abstract val source: Property<String>

    @get:Input
    abstract val gems: MapProperty<String, String>

    @TaskAction
    override fun exec() {
        val src = source.get()

        val bundler = pathLookup("bundle", System.getProperty("user.home") + "/.rbenv/shims")

        val bundleDir = Paths.get(workingDir.path, ".bundle").toFile()
        val bundleConfig = Paths.get(bundleDir.path, "config").toFile()
        if (!bundleConfig.exists()) {
            bundleDir.mkdirs()
            bundleConfig.writeText(
                """
                    ---
                    BUNDLE_PATH: ".bundle"
                """.trimIndent()
            )
        }

        // create Gemfile
        Paths.get(workingDir.path, "Gemfile").toFile().writeText(
            """
source "$src"

${gems.get().map { "gem \"${it.key}\", \"${it.value}\"" }.joinToString("\n")}
            """
        )

        commandLine(bundler, "install")

        super.exec()
    }
}
