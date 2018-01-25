package dk.danskespil.gradle.plugins.release

import org.ajoberstar.gradle.git.release.opinion.Strategies
import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.exception.GrgitException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ReleaseClassicPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        configureGrGitReleasePluginLikeWeDoWithCopyPasteInEveryProject(project)
    }

    private void configureGrGitReleasePluginLikeWeDoWithCopyPasteInEveryProject(Project project) {
        // See https://github.com/ajoberstar/gradle-git
        project.plugins.apply('org.ajoberstar.release-base')
        project.plugins.apply('org.ajoberstar.release-opinion')

        // This is what the classical copy/paste solution looks like
        //        release {
        //            grgit = org.ajoberstar.grgit.Grgit.open()
        //            defaultVersionStrategy = Strategies.SNAPSHOT
        //            versionStrategy Strategies.SNAPSHOT
        //            tagStrategy {
        //                prefixNameWithV = false // defaults to true
        //                generateMessage = { version -> "My new version $version.version" }
        //            }
        //        }

        // This would seem to be the equivalent, only configured in this plugin
        project.release {
            grgit = org.ajoberstar.grgit.Grgit.open()
            defaultVersionStrategy = Strategies.SNAPSHOT
            versionStrategy Strategies.SNAPSHOT
            tagStrategy {
                prefixNameWithV = false // defaults to true
                generateMessage = { version -> "My new version $version.version" }
            }
        }
    }

    private boolean tagExists(Grgit grgit, String revStr) {
        try {
            grgit.resolve.toCommit(revStr)
            return true
        } catch (GrgitException e) {
            return false
        }
    }
}
