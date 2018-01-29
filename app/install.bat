cp build\outputs\apk\free\release\app-free-release.apk release/app-free-release.apk
adb uninstall duy.com.text_converter
adb install -r  release\app-free-release.apk
adb shell am start -n "duy.com.text_converter/com.duy.text.converter.core.activities.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
exit
cp build\outputs\apk\pro\release\app-pro-release.apk release/app-pro-release.apk

adb uninstall com.duy.text_converter.pro
adb install -r release/app-pro-release.apk
adb shell am start -n "com.duy.text_converter.pro/com.duy.text.converter.core.activities.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

exit