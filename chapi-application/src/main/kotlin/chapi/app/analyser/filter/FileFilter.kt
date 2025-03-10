package chapi.app.analyser.filter

import chapi.app.analyser.config.ChapiConfig
import chapi.app.analyser.config.Language
import java.io.File

open class FileFilter {
    companion object {
        fun filterByLanguage(path: String, config: ChapiConfig): Boolean {
            // todo: change to grep
            if (config.filterRule.isNotEmpty()) {
                return config.filterRule.toRegex().matches(path)
            }

            if (!File(path).isFile) {
                return false;
            }

            return when (config.language) {
                "java" -> {
                    path.endsWith(".java")
                }
                "go" -> {
                    path.endsWith(".go")
                }
                "python" -> {
                    path.endsWith(".py")
                }
                "typescript" -> {
                    val isNormalFile = path.endsWith(".ts") || path.endsWith(".tsx")
                        || path.endsWith(".js") || path.endsWith(".jsx")
                    val isNotMinFile = path.endsWith(".min.js")

                    if (config.ignoreMinFile) {
                        return isNormalFile && !isNotMinFile
                    } else {
                        isNormalFile
                    }
                }
                "scala" -> {
                    path.endsWith(".scala")
                }
                Language.CSharp -> {
                    path.endsWith(".cs")
                }
                Language.KOTLIN -> {
                    path.endsWith(".kt") || path.endsWith(".kts")
                }
                else -> {
                    path.endsWith(".java")
                }
            }
        }
    }
}
