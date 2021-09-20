# Falcon-9-Launches

This project implements a scrollable list of SpaceX’s ‘Falcon 9’ rocket launches. 
For each launch, it shows the name, launch date, whether mission was a success or not and the mission badge/patch image

## Features
* MVVM
* Kotlin Coroutines
* Dagger Hilt
* GitHub actions for CI
* Light/Dark Mode Support

## Prerequisite
To build this project, you require:
- Android Studio arctic fox
- Gradle 7.0.2

<h2 align="left">Screenshots</h2>
<h4 align="center">
<img src="https://github.com/ESIDEM/Falcon-9-Launches/blob/master/screenshots/Screenshot_20210919-213539.png" width="30%" vspace="10" hspace="10">
<img src="https://github.com/ESIDEM/Falcon-9-Launches/blob/master/screenshots/Screenshot_20210919-213616.png" width="30%" vspace="10" hspace="10"><br>

## Libraries

- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - ViewModel for persisting view state across config changes
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
- [Dagger Hilt](https://dagger.dev/hilt) - handles dependency injection
