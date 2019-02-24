package com.amazonaws.emr.node.provisioner.util.io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public final class MoreFiles {
   private MoreFiles() {
   }

   public static void forceMkdirForParent(Path path) throws IOException {
      Preconditions.checkNotNull(path, "Path is required");
      Path parentPath = path.getParent();
      if (parentPath != null) {
         FileUtils.forceMkdir(parentPath.toFile());
      }

   }

   public static void setPermissionsForFilesRecursively(Path path, Set<PosixFilePermission> permissions) throws IOException {
      Preconditions.checkNotNull(path, "Path is required");
      Preconditions.checkNotNull(permissions, "Permissions are required");
      Files.walkFileTree(path, new MoreFiles.SetFilePermissionVisitor(permissions));
   }

   private static final class SetFilePermissionVisitor extends SimpleFileVisitor<Path> {
      private final Set<PosixFilePermission> permissions;

      private SetFilePermissionVisitor(Set<PosixFilePermission> permissions) {
         this.permissions = permissions;
      }

      public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
         if (attrs.isRegularFile()) {
            Files.setPosixFilePermissions(path, this.permissions);
         }

         return FileVisitResult.CONTINUE;
      }
   }
}
