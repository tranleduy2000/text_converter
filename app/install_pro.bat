cp build\outputs\apk\release\apppro-release.apk app-release.apk
adb uninstall duy.com.text_converter.pro
adb install -r  app-release.apk
adb shell am start -n "com.duy.text_converter.pro/com.duy.text.converter.pro.activities.MainActivityPro" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
