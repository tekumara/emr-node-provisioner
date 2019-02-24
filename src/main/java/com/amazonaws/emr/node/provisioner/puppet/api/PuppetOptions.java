package com.amazonaws.emr.node.provisioner.puppet.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class PuppetOptions {
   private PuppetColor color;
   private boolean evaltrace;
   private boolean useFutureParser;
   private List<String> modulePaths;

   public PuppetOptions() {
      this.color = PuppetColor.ANSI;
      this.evaltrace = false;
      this.modulePaths = new ArrayList();
   }

   public PuppetOptions setEvaltrace(boolean evaltrace) {
      this.evaltrace = evaltrace;
      return this;
   }

   public boolean isEvaltrace() {
      return this.evaltrace;
   }

   public PuppetOptions setColor(PuppetColor color) {
      this.color = color;
      return this;
   }

   public PuppetColor getColor() {
      return this.color;
   }

   public PuppetOptions setUsingFutureParser(boolean useFutureParser) {
      this.useFutureParser = useFutureParser;
      return this;
   }

   public boolean isUsingFutureParser() {
      return this.useFutureParser;
   }

   public PuppetOptions addModulePath(String modulePath) {
      this.modulePaths.add(modulePath);
      return this;
   }

   public PuppetOptions addModulePath(File modulePath) {
      return this.addModulePath(modulePath.getAbsolutePath());
   }

   public List<String> getModulePaths() {
      return new ArrayList(this.modulePaths);
   }
}
