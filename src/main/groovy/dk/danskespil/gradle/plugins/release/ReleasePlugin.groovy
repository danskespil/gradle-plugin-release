package dk.danskespil.gradle.plugins.release

import org.ajoberstar.gradle.git.release.opinion.Strategies
import org.ajoberstar.gradle.git.release.semver.RebuildVersionStrategy
import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.exception.GrgitException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ReleasePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        //ReleasePluginExtension extension = project.extensions.create("dsRelease", ReleasePluginExtension)
        configureGrGitReleasePluginLikeWeDoWithCopyPasteInEveryProject(project)
    }

    private void configureGrGitReleasePluginLikeWeDoWithCopyPasteInEveryProject(Project project) {
        // See also https://github.com/ajoberstar/gradle-git
        project.plugins.apply('org.ajoberstar.release-base')

        project.release {
            versionStrategy RebuildVersionStrategy.INSTANCE
            versionStrategy Strategies.DEVELOPMENT
            versionStrategy Strategies.PRE_RELEASE
            versionStrategy Strategies.FINAL
            defaultVersionStrategy = Strategies.DEVELOPMENT
            tagStrategy {
                generateMessage = { version ->
                    StringBuilder builder = new StringBuilder()
                    builder << 'Releasing of '
                    builder << version.version
                    builder << '\n\n'

                    String previousVersion = "${project.release.tagStrategy.toTagString(version.previousVersion)}^{commit}"
                    List excludes = []
                    if (tagExists(grgit, previousVersion)) {
                        excludes << previousVersion
                    }
                    grgit.log(
                            includes: ['HEAD'],
                            excludes: excludes
                    ).inject(builder) { bldr, commit ->
                        bldr << '- '
                        bldr << commit.shortMessage
                        bldr << '\n'
                    }
                    builder.toString()
                }
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
