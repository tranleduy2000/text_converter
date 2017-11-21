cp build\outputs\apk\release\app-release.apk app-release.apk
adb uninstall duy.com.text_converter.pro
adb install -r  app-release.apk
adb shell am start -n "duy.com.text_converter.pro/com.duy.text.converter.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
cd ..
exit