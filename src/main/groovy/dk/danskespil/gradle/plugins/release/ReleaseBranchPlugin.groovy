package dk.danskespil.gradle.plugins.release

import org.ajoberstar.gradle.git.release.opinion.Strategies
import org.gradle.api.Plugin
import org.gradle.api.Project

class ReleaseBranchPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply(ReleaseClassicPlugin)
        project.release {
            versionStrategy DSStrategies.BRANCH
        }
    }

}
