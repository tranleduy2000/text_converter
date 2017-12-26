cp build\outputs\apk\pro\release\app-pro-release.apk release/app-pro-release.apk

adb uninstall com.duy.text_converter.pro
adb install -r release/app-pro-release.apk
adb shell am start -n "com.duy.text_converter.pro/com.duy.text.converter.pro.activities.MainActivityPro" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
