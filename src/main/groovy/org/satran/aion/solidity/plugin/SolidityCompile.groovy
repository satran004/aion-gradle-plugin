package org.satran.aion.solidity.plugin

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

/**
 * Originally from https://github.com/web3j/solidity-gradle-plugin project
 * Modified for Aion smart contract
 */
class SolidityCompile extends SourceTask {

    @Input
    @Optional
    private Boolean overwrite

    @Input
    @Optional
    private Boolean optimize

    @Input
    @Optional
    private Integer optimizeRuns

    @Input
    @Optional
    private Boolean prettyJson

    @Input
    @Optional
    private OutputComponent[] outputComponents


    @TaskAction
    void compileSolidity() {

        String cwd = System.getProperty("user.dir");

        println("Current working directory: " + cwd)

        String COMPILE_CMD = "docker run --rm -v \"$cwd:/src\" satran004/aion-fastvm:0.3.1 solc"

        println(COMPILE_CMD)

        for (File contract in source) {
            def options = []

            for (output in outputComponents) {
                options.add("--$output")
            }

            if (optimize) {
                options.add('--optimize')

                if (0 < optimizeRuns) {
                    options.add('--optimize-runs')
                    options.add(optimizeRuns)
                }
            }

            if (overwrite) {
                options.add('--overwrite')
            }

            if (prettyJson) {
                options.add('--pretty-json')
                options.add(options.add("--$OutputComponent.ASM_JSON"))
            }

            options.add('-o')
            options.add(outputs.files.singleFile.absolutePath)
            options.add(contract.absolutePath)

            project.exec {
                executable = COMPILE_CMD
                args = options
            }
        }
    }

    Boolean getOverwrite() {
        return overwrite
    }

    void setOverwrite(final Boolean overwrite) {
        this.overwrite = overwrite
    }

    Boolean getOptimize() {
        return optimize
    }

    void setOptimize(final Boolean optimize) {
        this.optimize = optimize
    }

    Integer getOptimizeRuns() {
        return optimizeRuns
    }

    void setOptimizeRuns(final Integer optimizeRuns) {
        this.optimizeRuns = optimizeRuns
    }

    Boolean getPrettyJson() {
        return prettyJson
    }

    void setPrettyJson(final Boolean prettyJson) {
        this.prettyJson = prettyJson
    }

    OutputComponent[] getOutputComponents() {
        return outputComponents
    }

    void setOutputComponents(final OutputComponent[] outputComponents) {
        this.outputComponents = outputComponents
    }

}
