package dk.danskespil.gradle.plugins.release

import org.ajoberstar.gradle.git.release.opinion.Strategies
import org.ajoberstar.gradle.git.release.semver.PartialSemVerStrategy
import org.ajoberstar.gradle.git.release.semver.SemVerStrategy
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.ajoberstar.gradle.git.release.semver.StrategyUtil.*

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


    static final class PreRelease {
        private static final Logger logger = LoggerFactory.getLogger(PreRelease)

        static final PartialSemVerStrategy BRANCH = closure { state ->
            logger.info('DSStrategies.PreRelease: state='+state)

            String shortenedBranch = state.currentBranch.name.replaceAll(/[\/]/, '-')
            logger.info('DSStrategies.PreRelease: shortenedBranch='+shortenedBranch)

            // Get Nearest any version as string
            def nearest = ''+state.nearestVersion.any
            logger.info('DSStrategies.PreRelease: nearest='+nearest)

            // Split branch into list
            def branchList = shortenedBranch.split('\\-') as List
            logger.info('DSStrategies.PreRelease: branchList='+branchList)

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
                logger.info('DSStrategies.PreRelease: count='+count)

                // Set branchList to branch name and add the incremented count
                branchList = shortenedBranch.split('\\-') as List
                branchList << Integer.toString(count + 1)
            }

            def release = branchList.join('-')
            logger.info('DSStrategies.PreRelease: release='+release)

            state.copyWith(inferredPreRelease: release)
        }
    }
}
