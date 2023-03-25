package build_logic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog get() {
    return extensions.getByType<VersionCatalogsExtension>().named("libs")
}

fun VersionCatalog.version(name: String): String {
    return findVersion(name).get().requiredVersion
}
