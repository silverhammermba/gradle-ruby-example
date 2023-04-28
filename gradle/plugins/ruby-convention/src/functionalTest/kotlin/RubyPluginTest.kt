import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class RubyPluginTest {
    @get:Rule
    var projectDir = TemporaryFolder()

    @Test
    fun rubyRuby() {
        val projectBuild = projectDir.newFile("build.gradle.kts")
        projectBuild.writeText(
            """
            plugins {
                id("com.max.ruby")
            }
            ruby {
                gemSource.set("https://rubygems.org")
            }
        """.trimIndent()
        )

        val taskPath = ":sayHi"

        val result = GradleRunner.create()
            .withProjectDir(projectDir.root)
            .withArguments(taskPath)
            .withPluginClasspath()
            .build()


        val task = result.task(taskPath)
        assertEquals(TaskOutcome.SUCCESS, task?.outcome)

        assertTrue("unexpected output ${result.output}", result.output.contains("Hello from Ruby!"))
    }
}
