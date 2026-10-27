package com.byblack.office;

import android.content.res.AssetManager;
import java.io.*;

public class AssetClassLoader extends ClassLoader {
 private final AssetManager assets;

 public AssetClassLoader(ClassLoader parent,
                         AssetManager assets) {
  super(parent);
  this.assets=assets;
 }

 @Override
 public InputStream getResourceAsStream(String name) {
  try {
   return assets.open("xmlbeans/"+name);
  }catch(IOException e){
   return super.getResourceAsStream(name);
  }
 }
}
