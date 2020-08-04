# Notification Grabber

Notification grabber is an android application which takes screenshot of truecaller notifications, apply OCR and collects user phone number, current unix timestamp of call, incoming phone number, state of incoming phone call (Missed or Blocked call), Spam checking tag, date and additional information related to call from truecaller notifications and sends these data to firebase database.

## Getting Started

Clone the repository using git clone command from terminal and execute the repo in Android Studio from "Open existing Android Project" option and select the downloaded repo and select build.gradle.

### Prerequisites

Insall these from SDK manager:

SDK platform: Android Oreo(8.1), 
SDK Tools: Android SDK Platform tools, Google Play Services, Google Play APK expansion library, Google Play licensing library, Instant Apps Development SDK, 
Support Repository: Android Support repository, Google Repository


## Deployment

Deploy the application in real device i.e. android phone by connecting it with usb data cable to local machine where the project is existing. Activate the developer option and usb debugging from phone's setting.

## Dependency

The following dependencies must be present in build.gradle:

    implementation 'com.google.firebase:firebase-core:16.0.1'
    implementation 'com.google.firebase:firebase-database:16.0.1'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    implementation 'com.google.android.gms:play-services-vision:15.0.2'

## Authors

* **Anik Chowdhury**



