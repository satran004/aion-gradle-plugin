package org.satran.aion.solidity.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.file.SourceDirectorySetFactory
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

import javax.inject.Inject

import static org.codehaus.groovy.runtime.StringGroovyMethods.capitalize

/**
 * Originally from https://github.com/web3j/solidity-gradle-plugin project
 * Modified for Aion smart contract
 */
/**
 * Gradle plugin for Solidity compile automation.
 */
class SolidityPlugin implements Plugin<Project> {

    private final SourceDirectorySetFactory sourceFactory

    private final static String NAME = "solidity"

    @Inject
    SolidityPlugin(final SourceDirectorySetFactory sourceFactory) {
        this.sourceFactory = sourceFactory
    }

    @Override
    void apply(final Project target) {
        target.pluginManager.apply(JavaPlugin.class)
        target.extensions.create(SolidityExtension.NAME,
                SolidityExtension, target)

        final SourceSetContainer sourceSets = target.convention
                .getPlugin(JavaPluginConvention.class).sourceSets

        sourceSets.all { SourceSet sourceSet ->
            configureSourceSet(target, sourceSet)
        }

        target.afterEvaluate {
            sourceSets.all { SourceSet sourceSet ->
                configureTask(target, sourceSet)
            }
        }
    }

    /**
     * Add default source set for Solidity.
     */
    private void configureSourceSet(final Project project, final SourceSet sourceSet) {

        def srcSetName = capitalize((CharSequence) sourceSet.name)
        def soliditySourceSet = new DefaultSoliditySourceSet(srcSetName, sourceFactory)

        sourceSet.convention.plugins.put(NAME, soliditySourceSet)

        def defaultSrcDir = new File(project.projectDir, "src/$sourceSet.name/$NAME")
        def defaultOutputDir = new File(project.buildDir, "resources/$sourceSet.name/$NAME")

        soliditySourceSet.solidity.srcDir(defaultSrcDir)
        soliditySourceSet.solidity.outputDir = defaultOutputDir

        sourceSet.allJava.source(soliditySourceSet.solidity)
        sourceSet.allSource.source(soliditySourceSet.solidity)
    }

    /**
     * Configures code compilation tasks for the Solidity source sets defined in the project
     * (e.g. main, test).
     * <p>
     * By default the generated task name for the <code>main</code> source set
     * is <code>compileSolidity</code> and for <code>test</code>
     * <code>compileTestSolidity</code>.
     */
    private static void configureTask(final Project project, final SourceSet sourceSet) {

        def srcSetName = sourceSet.name == 'main' ?
                '' : capitalize((CharSequence) sourceSet.name)

        def compileTask = project.tasks.create(
                "compile" + srcSetName + "Solidity", SolidityCompile)

        def soliditySourceSet = sourceSet.convention.plugins[NAME] as SoliditySourceSet

        // Set the sources for the generation task
        compileTask.setSource(soliditySourceSet.solidity)

        compileTask.outputComponents = project.solidity.outputComponents
        compileTask.overwrite = project.solidity.overwrite
        compileTask.optimize = project.solidity.optimize
        compileTask.optimizeRuns = project.solidity.optimizeRuns
        compileTask.prettyJson = project.solidity.prettyJson
        compileTask.outputs.dir(soliditySourceSet.solidity.outputDir)
        compileTask.description = "Compiles Solidity contracts " +
                "for $sourceSet.name source set."

        project.getTasks().getByName('build') dependsOn(compileTask)
    }

}
