package com.yilami.archie.skeleton.core;

import com.yilami.archie.skeleton.constant.Constant;
import com.yilami.archie.common.util.FileUtils;

import java.io.File;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class Src {

  private File javaFolder;
  private File resFolder;
  private File testFolder;

  public Src(File mainFolder){
    this.javaFolder = FileUtils.createFolder(mainFolder, Constant.JAVA_FOLDER);
    this.resFolder = FileUtils.createFolder(mainFolder, Constant.RESOURCE_FOLDER);
    this.testFolder = FileUtils.createFolder(mainFolder, Constant.TEST_FOLDER);
  }

  public File getJavaFolder() {
    return javaFolder;
  }

  public File getResFolder() {
    return resFolder;
  }

  public File getTestFolder() {
    return testFolder;
  }
}
