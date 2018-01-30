package dk.danskespil.gradle.plugins.release

import org.ajoberstar.gradle.git.release.opinion.Strategies
import org.ajoberstar.gradle.git.release.semver.PartialSemVerStrategy
import org.ajoberstar.gradle.git.release.semver.SemVerStrategy

import static org.ajoberstar.gradle.git.release.semver.StrategyUtil.all
import static org.ajoberstar.gradle.git.release.semver.StrategyUtil.closure

class DSStrategies {
    static final SemVerStrategy BRANCH = Strategies.DEFAULT.copyWith(
            name: 'branch',
            stages: ['branch'] as SortedSet,
            allowDirtyRepo: false,
            preReleaseStrategy: all(Strategies.PreRelease.STAGE_FIXED, Strategies.PreRelease.COUNT_INCREMENTED, PreRelease.BRANCH),
            createTag: true
    )


    static final class PreRelease {
        static final PartialSemVerStrategy BRANCH = closure { state ->
            String shortenedBranch = state.currentBranch.name.replaceAll(/[\/]/, '-')
            state.copyWith(inferredPreRelease: shortenedBranch) }
    }
}
