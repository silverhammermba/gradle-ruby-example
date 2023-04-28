package com.max.ruby

import java.io.File

fun pathLookup(exe: String, vararg paths: String): String {
    paths.forEach { path ->
        val fullPath = File("$path/$exe")
        if (fullPath.exists()) {
            return fullPath.absolutePath
        }
    }
    return exe
}
