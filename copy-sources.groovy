/**
 * This script will copy sources from the `:a` subproject into the other subprojects, but will remap them to different packages
 */
 
def source = new File('a/src')

def list = new File('.').listFiles().findAll { it.directory && it.name ==~ /[a-z][a-z]?/ && it.name != 'a'}
def subprojects = []
list.each {
   def subdirs = it.listFiles()
   if (subdirs*.name.contains('build.gradle')) {
      subprojects << it
   } else {
      subprojects.addAll(subdirs)
   }
}

String remap(String elem, String projectName) {
   elem.replaceAll('org.wordpress.android', "org.wp.${projectName}")
       .replaceAll('org/wordpress/android', "org/wp/${projectName}")
       .replaceAll('org.xmlrpc.android', "org.xmlrpc.${projectName}")
       .replaceAll('org/xmlrpc/android', "org/xmlrpc/${projectName}")
       .replaceAll('tools:replace="allowBackup, icon"','tools:replace="allowBackup, icon, android:name"')
       
}

subprojects.each { projectDir ->
   def projectName = projectDir.name
   def buildFile = new File("$projectDir/build.gradle")
   if (buildFile.exists() && buildFile.text.contains("apply plugin: 'com.android")) {
      println "Copying sources to project ${projectName}"
  
      
      new File("${projectDir.absolutePath}/src").deleteDir()
      new File("${projectDir.absolutePath}/build").deleteDir()
      
      source.eachFileRecurse { srcFile ->
          def dest = new File("${projectDir.absolutePath}/src/${srcFile.absolutePath-source.absolutePath}")
          dest = new File(dest.absolutePath.replace('./', ''))
          dest = new File(remap(dest.absolutePath, projectName))
          print "."
          int idx = dest.name.lastIndexOf('.')
          def ext = idx>0?dest.name.substring(idx+1):''
          if (srcFile.directory) {
             dest.mkdirs()
          } else {
             
             if (ext in ['xml', 'properties', 'html', 'java', 'md', 'json']) {
                dest.write(remap(srcFile.text, projectName))
             } else {
                dest.bytes = srcFile.bytes
             }
             
          }
      }
      println " [OK]"
      
      println "Patching ${buildFile}"
      if (!buildFile.text.contains('wordpress-mobile.github.io/WordPress-Android')) {
      buildFile << '''
   repositories {
       maven { url 'http://wordpress-mobile.github.io/WordPress-Android' }
   }

   dependencies {
       compile deps.support.cardView
       compile deps.support.design
       compile deps.support.multidex
       compile deps.other.rest
       compile deps.other.greenrobot
       compile deps.other.commonslang
       compile deps.other.gcm
       compile deps.external.gson
   }

   android {
       useLibrary 'org.apache.http.legacy'
   }
      '''
      }
   }
}
