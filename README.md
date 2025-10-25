# @ravinandan25/react-native-lottie-splash-screen

[![npm](https://img.shields.io/badge/npm-@ravinandan25/react--native--lottie--splash--screen-blue)](https://www.npmjs.com/package/@ravinandan25/react-native-lottie-splash-screen)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)](https://github.com/ravinandan25/react-native-lottie-splash-screen/pulls)
[![License MIT](https://img.shields.io/badge/license-MIT-orange.svg)](https://opensource.org/licenses/MIT)

Fixed version of `react-native-lottie-splash-screen` — resolves gray overlay and status bar issues on Android. Works on React Native 0.82+ with Swift implementation on iOS.

## Contents
- Version Compatibilities
- Examples
- Installation
- iOS Setup
- Android Setup
- Usage
- API
- Contribution

## Versions Compatibilities
| React Native | Package Version |
|---|---|
| >= 0.82 | 3.x |
| >= 0.70 & < 0.77 | 2.x |
| < 0.70 | 1.x |

## Examples
![Android](screenshot/Lottie-Splash-Screen-Android.gif)
![iOS](screenshot/Lottie-Splash-Screen-IOS.gif)

Run examples:

```
yarn install
yarn bare:install
yarn bare:ios
yarn bare:android
yarn expo:install
yarn expo:ios
yarn expo:android
```

## Installation
```
yarn add "@ravinandan25/react-native-lottie-splash-screen" "lottie-react-native@7.3.4"
cd ios && bundle install && bundle exec pod install
```

## iOS Setup
1. Add your Lottie JSON (e.g., `loading.json`) to the Xcode project and include it in your app target.  
2. Replace or edit your `AppDelegate.swift`:

```
import UIKit
import SplashScreen

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
  var window: UIWindow?

  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

    SplashScreen.setupLottieSplash(
      in: window,
      lottieName: "loading",
      backgroundColor: UIColor.white,
      forceToCloseByHideMethod: false
    )

    return true
  }
}
```

3. Remove the default launch screen or make it blank in `LaunchScreen.storyboard`.

## Android Setup
1. Create `android/app/src/main/res/layout/launch_screen.xml`:

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/windowSplashScreenBackground">
  <com.airbnb.lottie.LottieAnimationView
      android:id="@+id/lottie"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      app:lottie_rawRes="@raw/loading"
      app:lottie_autoPlay="false"
      app:lottie_loop="false" />
</LinearLayout>
```

2. Add colors in `android/app/src/main/res/values/colors.xml`:

```
<resources>
  <color name="windowSplashScreenBackground">#ffffff</color>
</resources>
```

3. Add styles in `android/app/src/main/res/values/styles.xml`:

```
<resources>
  <style name="AppTheme" parent="Theme.AppCompat.DayNight.NoActionBar">
    <item name="android:statusBarColor">#ffffff</item>
    <item name="android:windowDisablePreview">true</item>
  </style>

  <style name="SplashScreen_SplashAnimation">
    <item name="android:windowExitAnimation">@android:anim/fade_out</item>
  </style>

  <style name="SplashScreen_SplashTheme" parent="Theme.AppCompat.NoActionBar">
    <item name="android:windowAnimationStyle">@style/SplashScreen_SplashAnimation</item>
    <item name="windowActionBarOverlay">false</item>
    <item name="android:windowTranslucentStatus">true</item>
  </style>
</resources>
```

4. Update `MainActivity.kt`:

```
import android.os.Bundle
import org.devio.rn.splashscreen.SplashScreen
import com.facebook.react.ReactActivity

class MainActivity : ReactActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SplashScreen.show(this, R.style.SplashScreen_SplashTheme, R.id.lottie, false)
  }
}
```

## Usage
```
import { useEffect } from "react";
import LottieSplashScreen from "@ravinandan25/react-native-lottie-splash-screen";

export default function App() {
  useEffect(() => {
    LottieSplashScreen?.hide();
  }, []);
  return null;
}
```

## API
| Method | Type | Description |
|--------|------|-------------|
| hide() | function | Hides the Lottie splash overlay |

## Contribution
Issues and PRs are welcome. Include a minimal repro if possible.

**MIT Licensed**  
by Ravinandan
