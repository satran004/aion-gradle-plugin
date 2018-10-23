package org.satran.aion.solidity.plugin

/**
 * Originally from https://github.com/web3j/solidity-gradle-plugin project
 * Modified for Aion smart contract
 */
enum OutputComponent {

    AST,
    AST_JSON,
    AST_COMPACT_JSON,
    ASM,
    ASM_JSON,
    OPCODES,
    BIN,
    BIN_RUNTIME,
    CLONE_BIN,
    ABI,
    HASHES,
    USERDOC,
    DEVDOC,
    METADATA

    @Override
    String toString() {
        return name().replaceAll('_', '-').toLowerCase()
    }

}