package com.amazonaws.emr.node.provisioner.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

public final class ResourceUtil {
   private ResourceUtil() {
   }

   public static String extractToString(String name) {
      try {
         InputStream stream = ResourceUtil.class.getResourceAsStream(name);
         Throwable var2 = null;

         String var3;
         try {
            var3 = IOUtils.toString(stream);
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (stream != null) {
               if (var2 != null) {
                  try {
                     stream.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  stream.close();
               }
            }

         }

         return var3;
      } catch (IOException var15) {
         throw new RuntimeException("Failed to extract resource " + name, var15);
      }
   }

   public static void extractToFile(String name, File output) {
      try {
         InputStream in = ResourceUtil.class.getResourceAsStream(name);
         Throwable var3 = null;

         try {
            OutputStream out = new FileOutputStream(output);
            Throwable var5 = null;

            try {
               IOUtils.copy(in, out);
            } catch (Throwable var30) {
               var5 = var30;
               throw var30;
            } finally {
               if (out != null) {
                  if (var5 != null) {
                     try {
                        out.close();
                     } catch (Throwable var29) {
                        var5.addSuppressed(var29);
                     }
                  } else {
                     out.close();
                  }
               }

            }
         } catch (Throwable var32) {
            var3 = var32;
            throw var32;
         } finally {
            if (in != null) {
               if (var3 != null) {
                  try {
                     in.close();
                  } catch (Throwable var28) {
                     var3.addSuppressed(var28);
                  }
               } else {
                  in.close();
               }
            }

         }

      } catch (IOException var34) {
         throw new RuntimeException(String.format("Failed to load resource %s to file %s", name, output.getAbsoluteFile()), var34);
      }
   }

   public static <T> T jsonToObject(String resourcePath, Class<T> klass) {
      ObjectMapper mapper = new ObjectMapper();

      try {
         InputStream inputStream = ResourceUtil.class.getResourceAsStream(resourcePath);
         Throwable var18 = null;

         Object var5;
         try {
            var5 = mapper.readValue(inputStream, klass);
         } catch (Throwable var15) {
            var18 = var15;
            throw var15;
         } finally {
            if (inputStream != null) {
               if (var18 != null) {
                  try {
                     inputStream.close();
                  } catch (Throwable var14) {
                     var18.addSuppressed(var14);
                  }
               } else {
                  inputStream.close();
               }
            }

         }

         return var5;
      } catch (IOException var17) {
         String message = String.format("Unable to load resource '%s' as an object of type '%s'", resourcePath, klass.getCanonicalName());
         throw new RuntimeException(message, var17);
      }
   }
}
