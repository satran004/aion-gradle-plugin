package org.satran.aion.solidity.plugin
import org.gradle.api.Action
import org.gradle.api.file.SourceDirectorySet

/**
 * Originally from https://github.com/web3j/solidity-gradle-plugin project
 * Modified for Aion smart contract
 */
/**
 * Source set for Solidity classes in a Gradle project.
 */
interface SoliditySourceSet {

    /**
     * Returns the source to be compiled by the Solidity compiler for this source set.
     * This may contain both Java and Solidity source files.
     *
     * @return The Solidity source. Never returns null.
     */
    SourceDirectorySet getSolidity()

    /**
     * Configures the Solidity source for this set.
     *
     * <p>The given closure is used to configure the {@link SourceDirectorySet}
     * which contains the Solidity source.
     *
     * @param configureClosure The closure to use to configure the Solidity source.
     * @return this
     */
    SoliditySourceSet solidity(Closure configureClosure)

    /**
     * Configures the Solidity source for this set.
     *
     * <p>The given action is used to configure the {@link SourceDirectorySet}
     * which contains the Solidity source.
     *
     * @param configureAction The action to use to configure the Solidity source.
     * @return this
     */
    SoliditySourceSet solidity(Action<? super SourceDirectorySet> configureAction)

    /**
     * All Solidity source for this source set.
     *
     * @return the Solidity source. Never returns null.
     */
    SourceDirectorySet getAllSolidity()

    String NAME = "solidity"

}
