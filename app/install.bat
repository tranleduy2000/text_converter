cd app
adb uninstall duy.com.text_converter

adb install -r  app-release.apk

adb shell am start -n "duy.com.text_converter/com.duy.text_converter.free.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
cd ..
exit