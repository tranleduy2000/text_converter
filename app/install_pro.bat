cd app
adb uninstall com.duy.text_converter.pro

adb install -r  apppro-release.apk

adb shell am start -n "duy.com.text_converter.pro/com.duy.text.converter.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
cd ..
exit