package org.satran.aion.gradle;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.file.SourceDirectorySetFactory
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

open class AionGradlePlugin : Plugin<Project> {

//    val sourceFactory: SourceDirectorySetFactory;

    override fun apply(target: Project) {

        target?.let {
            target.pluginManager.apply(JavaPlugin::class.java)
            target.extensions.create(SolidityExtension.NAME, SolidityExtension.javaClass, target)

            val sourceSets: SourceSetContainer = target.convention.getPlugin(JavaPluginConvention::class.java).sourceSets

            sourceSets.all {
                sourceSet ->
                configureSourceSet(target, sourceSet)
            }

            target.afterEvaluate {
                sourceSets.all {
//                    SourceSet sourceSet -> configureTask(target, sourceSet)
                }
            }
        }
    }

    private fun createTasks(target: Project) {
        target.tasks?.let {

            it.create("compileSolidity", SolidityCompile::class.java)
        }
    }

    /**
     * Add default source set for Solidity.
     */
    private fun configureSourceSet(project: Project, sourceSet: SourceSet): Unit {

        val srcSetName = sourceSet.name.capitalize()
        //val soliditySourceSet = DefaultSoliditySourceSet(srcSetName, sourceFactory)

//        sourceSet.convention.plugins.put(NAME, soliditySourceSet)
//
//        def defaultSrcDir = new File(project.projectDir, "src/$sourceSet.name/$NAME")
//        def defaultOutputDir = new File(project.buildDir, "resources/$sourceSet.name/$NAME")
//
//        soliditySourceSet.solidity.srcDir(defaultSrcDir)
//        soliditySourceSet.solidity.outputDir = defaultOutputDir
//
//        sourceSet.allJava.source(soliditySourceSet.solidity)
//        sourceSet.allSource.source(soliditySourceSet.solidity)
    }

    /**
     * Configures code compilation tasks for the Solidity source sets defined in the project
     * (e.g. main, test).
     * <p>
     * By default the generated task name for the <code>main</code> source set
     * is <code>compileSolidity</code> and for <code>test</code>
     * <code>compileTestSolidity</code>.
     */
     fun configureTask(project: Project, sourceSet: SourceSet) {

        val srcSetName = if (sourceSet.name.equals("main")) "" else  sourceSet.name.capitalize()

        val compileTask = project.tasks.create(
                "compile" + srcSetName + "Solidity", SolidityCompile::class.java)

//        val soliditySourceSet = sourceSet.convention.plugins[NAME] as SoliditySourceSet

        // Set the sources for the generation task
//        compileTask.setSource(soliditySourceSet.solidity)
//
//        compileTask.outputs.dir(soliditySourceSet.solidity.outputDir)
//        compileTask.description = "Compiles Solidity contracts " +
//                "for $sourceSet.name source set."
//
//        project.getTasks().getByName('build') dependsOn(compileTask)
    }
}