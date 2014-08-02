/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import com.wordnik.swagger.codegen.BasicAndroidGenerator

object AaaidaAndroigGenerator extends BasicAndroidGenerator {
  var dstDir=""
  // modified to be able to specify the output folder
  def main(args: Array[String]) = {
    if(args.length>1){
      dstDir = args(1)
    }

    generateClient(args)
    //important to exit here, otherwise the code will hang
    System.exit(0)
  }

  // location of templates
  override def templateDir = "Android"

  // where to write generated code
  override def destinationDir = dstDir+"generated-code/aaaida/android/src/main/java"

  // package for api invoker, error files
  override def invokerPackage = Some("aaaida.api.client")

  // package for models
  override def modelPackage = Some("aaaida.api.model")

  // package for api classes
  override def apiPackage = Some("aaaida.api")
  
  // For classes that are not included in the model, it is necessary to specify the package to import
  //Note that File does not correspon to java.io.File, it is a trick to provide POST (www-form)
  override def importMapping = super.importMapping ++ Map("InputStream" -> "java.io.InputStream", "FormDataContentDisposition" -> "com.sun.jersey.core.header.FormDataContentDisposition", "File" -> "com.sun.jersey.multipart.FormDataMultiPart")
  //Classes that are not included in the model will not be valid. However, since we need POST(www-form) capabilities, we map "file" variable to a FormDataMultiPart
  override def typeMapping = super.typeMapping ++ Map(
    "file" -> "FormDataMultiPart")

  //nothing rare here
  additionalParams ++= Map(
    "artifactId" -> "android-aaaida-api", 
    "artifactVersion" -> "1.0.0",
    "groupId" -> "aaaida")

  // supporting classes
  override def supportingFiles =
    List(
      ("apiInvoker.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiInvoker.java"),
      ("JsonUtil.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "JsonUtil.java"),
      ("apiException.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "ApiException.java"),
      ("androidServiceIteratorProvider.mustache", destinationDir + java.io.File.separator + invokerPackage.get.replace(".", java.io.File.separator) + java.io.File.separator, "AndroidServiceIteratorProvider.java"),
      ("pom.mustache", dstDir+"generated-code/aaaida/android", "pom.xml"))}
