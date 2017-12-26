cp build\outputs\apk\free\release\app-free-release.apk release/app-free-release.apk
adb uninstall duy.com.text_converter
adb install -r  release\app-free-release.apk
adb shell am start -n "duy.com.text_converter/com.duy.text.converter.core.activities.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
cd ..
