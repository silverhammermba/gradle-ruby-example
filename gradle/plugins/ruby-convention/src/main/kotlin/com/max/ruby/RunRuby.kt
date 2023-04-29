import com.max.ruby.pathLookup
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class RunRuby : Exec() {
    @get:Input
    abstract val script: Property<String>

    @TaskAction
    override fun exec() {
        val scriptPath = File(temporaryDir, "tmp.rb")
        scriptPath.writeText(script.get())

        val bundler = pathLookup("bundle", System.getProperty("user.home") + "/.rbenv/shims")

        commandLine(
            bundler,
            "exec",
            "ruby",
            scriptPath
        )

        super.exec()
    }
}
