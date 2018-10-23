package org.satran.aion.gradle;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.*
import org.gradle.internal.impldep.aQute.libg.reporter.ReporterMessages.base



class SolidityCompile: SourceTask() {

    val DOCKER_PULL_COMMAND = "docker pull satran004/aion-fastvm:0.3.1";

    @TaskAction
    fun compile() {
        val prjDir = project.projectDir.absolutePath;
        val COMPILE_COMMAND = "docker run --rm -v \"$prjDir:/src\" satran004/aion-fastvm:0.3.1 solc"

        println("Docker compile command: $COMPILE_COMMAND")

        for(file in source) {
            val contract = file as File


            project.exec {
                it.executable = COMPILE_COMMAND

                it.args = listOf("--abi", "--bin", "-o" + outputs.files.singleFile.absolutePath)

                println(outputs.files.singleFile.absolutePath)
                it.args.add(contract.absolutePath)

            }
        }
            //Pull docker image
            val proc = Runtime.getRuntime().exec(COMPILE_COMMAND)
            Scanner(proc.inputStream).use {
                while (it.hasNextLine()) println(it.nextLine())

        }
    }

    fun relativePath(parentPath: String, childPath: String) {

        val relative = File(parentPath).toURI().relativize(File(childPath).toURI()).getPath()
    }

}