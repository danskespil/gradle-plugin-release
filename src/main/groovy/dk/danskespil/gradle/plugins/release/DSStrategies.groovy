package dk.danskespil.gradle.plugins.release

import org.ajoberstar.gradle.git.release.opinion.Strategies
import org.ajoberstar.gradle.git.release.semver.PartialSemVerStrategy
import org.ajoberstar.gradle.git.release.semver.SemVerStrategy

import static org.ajoberstar.gradle.git.release.semver.StrategyUtil.all
import static org.ajoberstar.gradle.git.release.semver.StrategyUtil.closure
import static org.ajoberstar.gradle.git.release.semver.StrategyUtil.parseIntOrZero

class DSStrategies {
    static final SemVerStrategy BRANCH = Strategies.DEFAULT.copyWith(
            name: 'branch',
            stages: ['branch'] as SortedSet,
            allowDirtyRepo: false,
            preReleaseStrategy: all(PreRelease.BRANCH),
            enforcePrecedence: false,
            buildMetadataStrategy: Strategies.BuildMetadata.NONE,
            createTag: true
    )

    /**
     *  If the nearest version doesn't contain the branch name then use the
     *  branch name without forward dashes adding a number 1 for the release.
     *  e.q. branch "feature/issue-6/multiple-branch-patches" and nearest tag "0.1.0"
     *    -> "0.1.1-feature-issue-6-multiple-branch-patches-1"
     *
     *  If the nearest version does contain the branch name then use the
     *  branch name without forward dashes incrementing the number for the nearest version
     *  e.q. branch "feature/issue-6/multiple-branch-patches" and nearest tag "0.1.1-feature-issue-6-multiple-branch-patches-1"
     *    -> "0.1.1-feature-issue-6-multiple-branch-patches-2"
     */
    static final class PreRelease {
        static final PartialSemVerStrategy BRANCH = closure { state ->

            /// Remove all '/' from branch name
            String shortenedBranch = state.currentBranch.name.replaceAll(/[\/]/, '-')

            // Get Nearest any version as string
            def nearest = ''+state.nearestVersion.any

            // Split branch into list
            def branchList = shortenedBranch.split('\\-') as List

            // If nearest version not containing the branch name yet then add a "1" to the release
            if (!nearest.contains(shortenedBranch)) {
                branchList << '1'
            }
            // Else increment the version with +1
            else {
                // Split nearest into list
                def nearestList = nearest.split('\\-') as List

                // Get count
                def count = parseIntOrZero(nearestList[nearestList.size()-1])

                // Set branchList to branch name and add the incremented count
                branchList = shortenedBranch.split('\\-') as List
                branchList << Integer.toString(count + 1)
            }

            def release = branchList.join('-')

            state.copyWith(inferredPreRelease: release)
        }
    }
}
