package dk.danskespil.gradle.plugins.release

import org.ajoberstar.gradle.git.release.opinion.Strategies
import org.ajoberstar.gradle.git.release.semver.PartialSemVerStrategy
import org.ajoberstar.gradle.git.release.semver.SemVerStrategy

import static org.ajoberstar.gradle.git.release.semver.StrategyUtil.closure

class DSStrategies {
    static final SemVerStrategy BRANCH = Strategies.DEFAULT.copyWith(
            name: 'branch',
            stages: ['branch'] as SortedSet,
            allowDirtyRepo: true,
            preReleaseStrategy: PreRelease.BRANCH,
            createTag: false
    )

    static final class PreRelease {
        static final PartialSemVerStrategy BRANCH = closure { state -> state.copyWith(inferredPreRelease: state.currentBranch.name) }
    }

}
