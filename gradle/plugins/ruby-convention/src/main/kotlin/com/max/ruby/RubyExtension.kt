package com.max.ruby

import org.gradle.api.provider.Property

interface RubyExtension {
    val gemSource: Property<String>
}
