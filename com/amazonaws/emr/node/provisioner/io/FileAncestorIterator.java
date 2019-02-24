package com.amazonaws.emr.node.provisioner.io;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class FileAncestorIterator implements Iterator<File> {
   private File file;

   public FileAncestorIterator(File file) {
      this.file = file;
   }

   public boolean hasNext() {
      return this.file != null;
   }

   public File next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         File nextFile = this.file;
         this.file = this.file.getParentFile();
         return nextFile;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
