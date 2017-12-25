cp build\outputs\apk\free\release\app-free-release.apk app-release.apk
adb uninstall duy.com.text_converter
adb install -r  app-release.apk
adb shell monkey -p "duy.com.text_converter" -c android.intent.category.LAUNCHER 1
cd ..
