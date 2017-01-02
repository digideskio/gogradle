package com.github.blindpirate.gogradle.vcs.git

import com.github.blindpirate.gogradle.GogradleRunner
import com.github.blindpirate.gogradle.core.dependency.NotationDependency
import com.github.blindpirate.gogradle.util.DependencyUtils
import org.gradle.api.specs.Spec
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(GogradleRunner)
class GitResolvedDependencyTest {
    @Mock
    NotationDependency notationDependency
    @Mock
    Spec exclusionSpec

    @Before
    void setUp() {
        Mockito.when(notationDependency.getTransitiveDepExclusions()).thenReturn([exclusionSpec] as Set)
    }

    GitResolvedDependency newResolvedDependency() {
        return GitResolvedDependency.builder()
                .withName("package")
                .withCommitId('commitId')
                .withCommitTime(42L)
                .withRepoUrl('repoUrl')
                .withNotationDependency(notationDependency)
                .build()
    }

    @Test
    void 'a resolved dependency should be converted to notation successfully'() {
        // given
        GitResolvedDependency dependency = newResolvedDependency()
        // then
        assert dependency.toLockedNotation() == [name: 'package', commit: 'commitId', vcs: 'git', url: 'repoUrl']
    }

    @Test
    void 'notation\'s specs should be copied into resolved dependency'() {
        // given
        GitResolvedDependency dependency = newResolvedDependency()
        assert DependencyUtils.getExclusionSpecs(dependency).contains(exclusionSpec)
    }
}
