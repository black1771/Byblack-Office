package org.apache.xmlbeans.impl.schema;

import java.io.InputStream;
import java.io.IOException;
import org.apache.xmlbeans.ResourceLoader;

public class ClassLoaderResourceLoader implements ResourceLoader {

 private final ClassLoader loader;

 public ClassLoaderResourceLoader(ClassLoader loader) {
  this.loader=loader;
 }

 public InputStream getResourceAsStream(String name) {
  InputStream in=null;

  if(loader!=null)
   in=loader.getResourceAsStream(name);

  if(in!=null)
   return in;

  try {
   android.app.Application app=
    (android.app.Application)
    Class.forName("android.app.ActivityThread")
     .getMethod("currentApplication")
     .invoke(null);

   if(app!=null)
    return app.getAssets().open("xmlbeans/"+name);

  }catch(Throwable ignored){}

  return null;
 }

 public void close() {}
}
