package com.amazonaws.emr.node.provisioner.install.assemble.bundle;

import com.google.common.collect.ImmutableList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

public final class PackageBundleNameGraph {
   private final DirectedGraph<String, DefaultEdge> nameGraph;

   private PackageBundleNameGraph(PackageBundleNameGraph.Builder builder) {
      this.nameGraph = copyGraph(builder.nameGraph);
   }

   public static PackageBundleNameGraph.Builder builder() {
      return new PackageBundleNameGraph.Builder();
   }

   public Set<String> getIncludedBundleNames(String bundleName) {
      Set<String> includedBundleNames = new HashSet();
      Iterator var3 = this.nameGraph.incomingEdgesOf(bundleName).iterator();

      while(var3.hasNext()) {
         DefaultEdge edge = (DefaultEdge)var3.next();
         includedBundleNames.add(this.nameGraph.getEdgeSource(edge));
      }

      return includedBundleNames;
   }

   public List<String> listTopologicallyOrdered() {
      return ImmutableList.copyOf(new TopologicalOrderIterator(this.nameGraph));
   }

   private static DirectedGraph<String, DefaultEdge> copyGraph(Graph<String, DefaultEdge> graph) {
      DirectedGraph<String, DefaultEdge> copiedGraph = new DirectedAcyclicGraph(DefaultEdge.class);
      Graphs.addGraph(copiedGraph, graph);
      return copiedGraph;
   }

   public static final class Builder {
      private final DirectedGraph<String, DefaultEdge> nameGraph = new DirectedAcyclicGraph(DefaultEdge.class);

      public Builder() {
      }

      public PackageBundleNameGraph.Builder addBundle(String bundleName) {
         this.nameGraph.addVertex(bundleName);
         return this;
      }

      public PackageBundleNameGraph.Builder addBundle(String bundleName, Iterable<String> includedBundleNames) {
         this.addBundle(bundleName);
         this.addBundles(includedBundleNames);
         Iterator var3 = includedBundleNames.iterator();

         while(var3.hasNext()) {
            String includedBundleName = (String)var3.next();
            this.nameGraph.addEdge(includedBundleName, bundleName);
         }

         return this;
      }

      public PackageBundleNameGraph.Builder addBundles(Iterable<String> bundleNames) {
         Iterator var2 = bundleNames.iterator();

         while(var2.hasNext()) {
            String bundleName = (String)var2.next();
            this.addBundle(bundleName);
         }

         return this;
      }

      public PackageBundleNameGraph build() {
         return new PackageBundleNameGraph(this);
      }
   }
}
