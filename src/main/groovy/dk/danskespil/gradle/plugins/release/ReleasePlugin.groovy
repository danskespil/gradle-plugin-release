package dk.danskespil.gradle.plugins.release

import org.gradle.api.Plugin
import org.gradle.api.Project

class ReleasePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        ReleasePluginExtension extension = project.extensions.create("dsRelease", ReleasePluginExtension)
    }
}
