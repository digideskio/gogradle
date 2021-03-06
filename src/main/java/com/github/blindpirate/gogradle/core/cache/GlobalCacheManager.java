/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.blindpirate.gogradle.core.cache;

import com.github.blindpirate.gogradle.GolangPluginSetting;
import com.github.blindpirate.gogradle.core.VcsGolangPackage;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Manages global cache which users should never know.
 * Typically, global cache exists in ~/.gradle/go with following structure:
 * <br>+ ~/.gradle/go
 * <br>|
 * <br>|-- cache  stores global go repository cache, e.g., golang.org/x/tools.
 * <br>|   \-- golang.org/x/tools
 * <br>|       |-- metadata
 * <br>|       \-- (1a2b3c4d5e6f7890)
 * <br>\-- binary  stores go distributions.
 * The metadata also acts as a lock in synchronization of multiple JVM process.
 */
public interface GlobalCacheManager {
    /**
     * Ensures global cache exists and is writable.
     */
    void ensureGlobalCacheExistAndWritable();

    /**
     * Starts a session with given package.
     *
     * @param pkg e.g. golang.org/x/tools
     */
    void startSession(VcsGolangPackage pkg);

    /**
     * Terminates current session (in the same thread).
     */
    void endSession();

    /**
     * Notifies that the repository in current session has been updated.
     */
    void repoUpdated();

    /**
     * Check if the repository in current session has been updated recently.
     *
     * @return {@code true} if the package is up-to-date, {@code false} otherwise.
     * @see GolangPluginSetting#getGlobalCacheSecond()
     */
    boolean currentRepositoryIsUpToDate();

    /**
     * Get the go repository in global cache in current session
     *
     * @return the location of that repository in global cache
     */
    File getGlobalCacheRepoDir();

    /**
     * Get the path which locates in {@code relativePath} relative to ~/.gradle/go/binary
     *
     * @param relativePath the relative path to ~/.gradle/go/binary
     * @return the path
     */
    File getGlobalGoBinCacheDir(String relativePath);

    /**
     * Get the metadata of a package specified by {@code packagePath}
     *
     * @param packagePath import path of the package
     * @return the metadata if that package exists in global cache, {@code Optional.empty()} otherwise.
     */
    Optional<GlobalCacheMetadata> getMetadata(Path packagePath);


}
