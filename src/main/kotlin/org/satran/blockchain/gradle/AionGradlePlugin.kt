package org.satran.blockchain.gradle;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.file.SourceDirectorySetFactory

open class AionGradlePlugin: Plugin<Project> {

    lateinit var project: Project

    override fun apply(target: Project) {

        target?.let {
            project = target
            createTasks()
        }
    }

    private fun createTasks() {
        project.tasks?.let {

            it.create("compileSolidity", Compile::class.java)
        }
    }
}