import com.max.ruby.FetchGems
import com.max.ruby.RubyExtension

val ruby = extensions.create<RubyExtension>("ruby")

val rubyDir: Provider<Directory> = layout.buildDirectory.dir("ruby")
val rubyGroup = "ruby"

val fetchRuntimeDependencies = tasks.register<FetchGems>("fetchRuntimeDependencies") {
    group = rubyGroup
    description = "install gems"

    workingDir = rubyDir.get().asFile

    source.set(ruby.gemSource)

    gems.set(mapOf("paint" to "2.3.0"))
}

tasks.register<RunRuby>("sayHello") {
    group = rubyGroup
    description = "Say hi using Ruby"
    dependsOn(fetchRuntimeDependencies)

    workingDir = rubyDir.get().asFile

    script.set("""
        require 'paint'

        puts Paint['Hello, world!', :red]
    """.trimIndent())
}
