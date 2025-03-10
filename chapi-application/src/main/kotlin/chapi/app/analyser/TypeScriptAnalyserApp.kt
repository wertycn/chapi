package chapi.app.analyser

import chapi.app.analyser.config.ChapiConfig
import chapi.app.analyser.config.Language
import chapi.app.analyser.support.AbstractFile
import chapi.app.analyser.support.BaseAnalyser
import chapi.ast.typescriptast.TypeScriptAnalyser
import chapi.domain.core.CodeDataStruct
import chapi.domain.core.DataStructType

class TypeScriptAnalyserApp(config: ChapiConfig = ChapiConfig(language = Language.TypeScript)) : BaseAnalyser(config) {
    private val analyser: TypeScriptAnalyser by lazy { TypeScriptAnalyser() }
    override fun analysisByFiles(files: Array<AbstractFile>): Array<CodeDataStruct> =
        files.filter { it.isFile }.flatMap(::analysisByFile).toTypedArray()

    override fun analysisByFile(file: AbstractFile): List<CodeDataStruct> {
        println("processing file: ${file.absolutePath}")
        val codeContainer = analyser.analysis(file.content, file.relativePath)
        return codeContainer.DataStructures.map {
            it.apply {
                if (it.Type != DataStructType.INTERFACE) {
                    it.Imports = codeContainer.Imports
                }
            }
        }
    }
}
