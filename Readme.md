## 在已有Android工程中添加Flutter模块

1.进入已有android工程根目录
2.创建flutter module

```
//终端执行已下命令，flutter_module：flutter 模块名，可以自己命名
flutter create -t module flutter_module
```

3.在已有android工程的根目录中的Settings.gradle中添加如下代码

```kotlin
// FlutterHybirdDemo 为已有的android工程根目录
val filePath =
    settingsDir.parentFile.toString() + "/FlutterHybirdDemo/flutter_module/.android/include_flutter.groovy"
apply(from = File(filePath))
```

4.在app模块依赖新建的flutter模块，固定写法

```
//注：不要写成flutter模块名，这里是固定写法
implementation(project(":flutter"))
```
5.在android/app/src/main/AndroidManifest.xml中添加如下代码

```xml
<activity
    android:name="io.flutter.embedding.android.FlutterActivity" android:configChanges="orientation|keyboardHidden|keyboard|screenSize|smallestScreenSize|locale|layoutDirection|fontScale|screenLayout|density"
    android:hardwareAccelerated="true"
    android:windowSoftInputMode="adjustResize" />
```
6.从原生页面打开Flutter页面

```kotlin
vb.btnFlutter.setOnClickListener {
    startActivity(FlutterActivity.createDefaultIntent(this))
}
```

7.处理构建时错误

错误1：

```
An exception occurred applying plugin request [id: 'dev.flutter.flutter-gradle-plugin']
> Failed to apply plugin 'dev.flutter.flutter-gradle-plugin'.
   > Build was configured to prefer settings repositories over project repositories but repository 'maven' was added by plugin 'dev.flutter.flutter-gradle-plugin'

```

修改已有android工程根目录中settings.gradle.kts中的代码

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    //改为
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    ...
}
```

错误2：

```
Execution failed for task ':app:dataBindingMergeDependencyArtifactsDebug'.
> Could not resolve all files for configuration ':app:debugCompileClasspath'.
   > Could not resolve all dependencies for configuration ':app:debugCompileClasspath'.
      > Could not find io.flutter:flutter_embedding_debug:1.0.0-13e658725ddaa270601426d1485636157e38c34c.
        Required by:
            project :app > project :flutter
   > Could not resolve all dependencies for configuration ':app:debugCompileClasspath'.
      > Could not find io.flutter:armeabi_v7a_debug:1.0.0-13e658725ddaa270601426d1485636157e38c34c.
        Required by:
            project :app > project :flutter
   > Could not resolve all dependencies for configuration ':app:debugCompileClasspath'.
      > Could not find io.flutter:arm64_v8a_debug:1.0.0-13e658725ddaa270601426d1485636157e38c34c.
        Required by:
            project :app > project :flutter
   > Could not resolve all dependencies for configuration ':app:debugCompileClasspath'.
      > Could not find io.flutter:x86_64_debug:1.0.0-13e658725ddaa270601426d1485636157e38c34c.
        Required by:
            project :app > project :flutter

Possible solution:
 - Declare repository providing the artifact, see the documentation at https://docs.gradle.org/current/userguide/declaring_repositories.html

```

找不到一些代码包，在已有android工程根目录中settings.gradle.kts中的添加：

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        //添加这个仓库
        maven { url = uri("https://storage.googleapis.com/download.flutter.io") }
    }
}
```



