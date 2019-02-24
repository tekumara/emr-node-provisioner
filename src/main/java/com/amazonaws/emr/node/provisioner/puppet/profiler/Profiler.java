package com.amazonaws.emr.node.provisioner.puppet.profiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface Profiler<T, K, V> {
   void profile(File var1, File var2) throws IOException;

   void profile(File var1, File var2, ArrayList<T> var3) throws IOException;

   Map<K, V> profileMap(File var1) throws IOException;

   Map<K, V> profileMap(File var1, ArrayList<T> var2) throws IOException;
}
