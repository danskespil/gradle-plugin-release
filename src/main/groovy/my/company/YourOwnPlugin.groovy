package my.company

import org.gradle.api.Plugin
import org.gradle.api.Project

class YourOwnPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        YourOwnPluginExtension extension = project.extensions.create("yourownplugin", YourOwnPluginExtension)
        project.task('emitVersion') {
            doLast {
                println project.version
                if (project.yourownplugin.writeToFile) {
                    project.yourownplugin.writeToFile << project.version
                }
            }
        }
    }
}
