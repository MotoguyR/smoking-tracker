# RAZ'S Smoke Tracker (Android app)

This folder is the full source of the app. GitHub (free) builds it into an APK
you can install on your phone. You only do the setup once.

## Get the APK

1. Make a free account at github.com, then create a **New repository**
   (any name, for example `raz-smoke-tracker`). Public is easiest. The app
   contains nothing personal.
2. On the new repo page choose **uploading an existing file** and drag in the
   **contents** of this folder, so that `settings.gradle` and `app` sit at the top
   of the repo (not inside another folder). Include the hidden `.github` folder:
   - Windows: it is visible, just select everything.
   - Mac: press Cmd+Shift+. in Finder to show hidden folders first.
   - If `.github` will not upload: in the repo choose **Add file > Create new file**,
     type `.github/workflows/build-apk.yml` as the name, paste in the contents
     of `build-apk.yml` from this folder, and commit.
3. Click **Commit changes**. Open the **Actions** tab. The "Build APK" job takes
   about 3 to 6 minutes. A green check means it worked.
4. On your Android phone open `github.com/YOUR-NAME/YOUR-REPO/releases/latest`
   in Chrome and tap **RAZ-Smoke-Tracker.apk** to download it.
5. Open the downloaded file. Android will ask to allow installs from Chrome
   (or your Files app). Allow it, then tap **Install**.

If the job shows a red X, open it, copy the error text and send it to Claude.

## First launch

- Tap **Allow** when it asks to send notifications. Without that, no alert can sound.
- Use **Test sound** in the app to check the alert on your phone.
- If the app shows a red warning, tap **Open settings** and turn the permission on.

## Notes

- Alerts use the phone's alarm sound and vibration, and arrive even when the app
  is closed or the phone is locked. Change the sound in Android Settings >
  Apps > RAZ'S Smoke Tracker > Notifications > Time is up.
- Some phone makers (Samsung, Xiaomi, OnePlus) stop apps they consider idle. If
  alerts do not arrive, set the app's battery use to **Unrestricted**
  (Settings > Apps > RAZ'S Smoke Tracker > Battery).
- Your log is stored on the phone only. The app has no internet permission.
- Every push to the repo builds a fresh APK, and installing it over the old one
  keeps your data.
