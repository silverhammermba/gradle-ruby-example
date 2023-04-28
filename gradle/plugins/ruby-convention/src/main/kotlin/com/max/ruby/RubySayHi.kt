import com.max.ruby.pathLookup
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class RubySayHi : Exec() {
    @TaskAction
    override fun exec() {
        val scriptPath = File(temporaryDir, "say_hi.rb")
        scriptPath.writeText(script)

        val bundler = pathLookup("bundle", System.getProperty("user.home") + "/.rbenv/shims")

        commandLine(
            bundler,
            "exec",
            "ruby",
            scriptPath
        )

        super.exec()
    }

    companion object {
        const val script = """
require 'paint'

puts Paint['Hello from Ruby!', :red]
"""
    }
}
