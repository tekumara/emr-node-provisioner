package com.amazonaws.emr.node.provisioner.puppet.hiera;

import com.amazonaws.emr.node.provisioner.util.io.MoreFiles;
import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import org.apache.commons.io.FileUtils;

public final class HieraInstaller {
   @VisibleForTesting
   static final ImmutableSet<PosixFilePermission> FILE_PERMS = Sets.immutableEnumSet(PosixFilePermissions.fromString("rw-------"));
   private final HieraFileLayout layout;

   public HieraInstaller() {
      this(new HieraFileLayout());
   }

   public HieraInstaller(HieraFileLayout layout) {
      this.layout = layout;
   }

   public static HieraInstaller installingTo(File destinationFolder) {
      CommonPreconditions.require(destinationFolder, "destinationFolder");
      return new HieraInstaller(new HieraFileLayout(destinationFolder));
   }

   public void writeConfigFile(String content) throws IOException {
      File configFile = this.layout.getConfigFile();
      FileUtils.writeStringToFile(configFile, content);
      this.setFilePermissions(configFile);
   }

   public void installDataDirectory(File sourceDirectory) throws IOException {
      File dataDirectory = this.layout.getDataDirectory();
      FileUtils.copyDirectory(sourceDirectory, dataDirectory);
      this.setFilePermissionsRecursively(dataDirectory);
   }

   public void writeDataSourceFile(String content, String dataSourceName) throws IOException {
      File dataSource = this.layout.getDataSource(dataSourceName);
      FileUtils.writeStringToFile(dataSource, content);
      this.setFilePermissions(dataSource);
   }

   public void deleteDataSourceFile(String dataSourceName) throws IOException {
      File dataSource = this.layout.getDataSource(dataSourceName);
      FileUtils.forceDelete(dataSource);
   }

   public boolean hasConfigFile() {
      return this.layout.getConfigFile().exists();
   }

   public boolean hasDataSourceDirectory(String directoryName) {
      return this.layout.getDataSource(directoryName).isDirectory();
   }

   public boolean hasDataSourceFile(String dataFile) {
      return this.layout.getDataSource(dataFile).isFile();
   }

   private void setFilePermissions(File file) throws IOException {
      Files.setPosixFilePermissions(file.toPath(), FILE_PERMS);
   }

   private void setFilePermissionsRecursively(File folder) throws IOException {
      MoreFiles.setPermissionsForFilesRecursively(folder.toPath(), FILE_PERMS);
   }
}
