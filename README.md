# Face Check Android SDK Sample App

Sample application for  Face Check Android SDK.
### Step to use the SDK below as well:
#### 1. settings.gradle :
```gradle
    pluginManagement {
        repositories {
            google()
            mavenCentral()
            jcenter()
            gradlePluginPortal()
        }
    }
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            jcenter()
            mavenCentral()
            //enter github user name and github token
            maven {
                url = "https://maven.pkg.github.com/surepassio/face-check-android-sdk-sample-app"
                credentials {
                    username = "USER_NAME"
                    password = "PAT_TOKEN"//https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token
                    // (Allow Package Read Permission in token)
                }
            }
        }
    }
rootProject.name = "FaceSDKHelper"
include ':app'
```

#### 2. build.grade (app):
```groovy
   minSdk 26 //min sdk should be 26
   
   dependencies {
            implementation 'io.surepass.sdk:face-check-android-sdk-v1:1.0.0'
   }
```
Make sure to sync your project after adding the dependency.
#### 3. Inside Application:
```kotlin
   binding.btnGetStarted.setOnClickListener {
//      val token = binding.etApiToken.text.toString()
        val token = "YOUR TOKEN"
        val env = "PREPROD"
        openActivity(env, token)
    }
```
SDK will be started from openActivity function
```kotlin 
    private fun openActivity(env: String, token: String) {
        val intent = Intent(this, InitSDK::class.java)
        intent.putExtra("token", token)
        intent.putExtra("env", env)
        faceScanActivityResultLauncher.launch(intent)
    }
```
Response can be obtained in 
```kotlin 
   private fun registerActivityForResult() {
        faceScanActivityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val resultCode = result.resultCode
                val data = result.data
                if (resultCode == RESULT_OK && data != null) {
                    val faceScanResponse = data.getStringExtra("faceResponse")
                    Log.e("MainActivity", "FaceScan Response $faceScanResponse")
                    showResponse(faceScanResponse)
                }
            }
    }
```
For better clarification you can check the code details inside the project

### Change Theme Of SDK

To customize the theme of the SDK, you can modify the `themes.xml` file. If your project has set up the theme in the `styles.xml` file instead, the steps will be the same for that as well.:

1. **Locate the `themes.xml` file**:
   You can usually find this file in the `res/values` directory of your Android project.

2. **Update the theme**:
   Inside the `themes.xml` file, you'll see a style defined for your application theme. It typically looks like this:

   ```xml
   <resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Base.Theme.FaceSDKHelper" parent="Theme.Material3.DayNight.NoActionBar">
        <!-- Customize your light theme here. -->
         <item name="colorPrimary">@color/sure_pass_color</item>
    </style>
    <style name="Theme.FaceSDKHelper" parent="Base.Theme.FaceSDKHelper" />
   </resources>
   ```

3. **Change the primary color**:
   Modify the `<item name="colorPrimary">@color/sure_pass_color</item>` line to change theme. Replace `@color/sure_pass_color` with the color resource you want to use.

   For example, if you want to change the primary color to red, you can use:

   ```xml
   <item name="colorPrimary">@color/red</item>
   ```

   Ensure that the color resource you refer to exists in your `colors.xml` file or define it if it doesn't.

4. **Save your changes**.

5. **Rebuild your project**:
   After making changes to the theme, rebuild your project to apply the updated theme
