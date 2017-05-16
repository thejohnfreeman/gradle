/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.ivyservice.resolveengine.result;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import org.gradle.api.artifacts.FileCollectionDependency;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ArtifactSet;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.DependencyArtifactsVisitor;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.VisitedFileDependencyResults;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.DependencyGraphNode;
import org.gradle.internal.component.local.model.LocalFileDependencyMetadata;

import java.util.Map;

public class FileDependencyCollectingGraphVisitor implements DependencyArtifactsVisitor {
    private final SetMultimap<Long, ArtifactSet> filesByNodeId = LinkedHashMultimap.create();
    private final Map<FileCollectionDependency, ArtifactSet> rootFiles = Maps.newLinkedHashMap();

    @Override
    public void startArtifacts(DependencyGraphNode root) {
    }

    @Override
    public void visitArtifacts(DependencyGraphNode from, DependencyGraphNode to, ArtifactSet artifacts) {
    }

    @Override
    public void visitArtifacts(DependencyGraphNode node, LocalFileDependencyMetadata fileDependency, ArtifactSet artifactSet) {
        if (node.isRoot()) {
            rootFiles.put(fileDependency.getSource(), artifactSet);
        }
        filesByNodeId.put(node.getNodeId(), artifactSet);
    }

    @Override
    public void finishArtifacts() {
    }

    public VisitedFileDependencyResults complete() {
        return new DefaultVisitedFileDependencyResults(filesByNodeId, rootFiles);
    }
}
