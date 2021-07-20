# Android Video Stream Decoding Sample

## Introduction

This project is derived from the original DJI sample project [Android-VideoStreamDecodingSample](https://github.com/DJI-Mobile-SDK-Tutorials/Android-VideoStreamDecodingSample). It utilizes the [Mobile DJI Drone SDK](https://developer.dji.com/mobile-sdk/) for Android and the [DJI Streamer Lib](https://github.com/neilyoung/djistreamerlib) in order to enable **WebRTC video streaming from the drone's camera via an Android device to another WebRTC client**.

For details regarding the DJI sample app please refer to the original [Android-VideoStreamDecodingSample](https://github.com/DJI-Mobile-SDK-Tutorials/Android-VideoStreamDecodingSample).

## Prerequisites

You need to have:

- a **DJI drone** with a **Remote Control** and an Android device running at least **Android 7**.
- a [**DJI developer account**](https://developer.dji.com/).
- a registered [**DJI SDK API key**](https://developer.dji.com/user/apps/#all), you can use the original package name for registration (`com.dji.videostreamdecodingsample`). You need to configure this key in `AndroidManifest.xml`.
- a **credential consisting of a user Id and a license key** for being able to use the [**DJI Streamer Lib**](https://github.com/neilyoung/djistreamerlib) which brings WebRTC connectivity to the DJI drone . You can either contact our [technical support](mailto:decades-software@freenet.de) for details or checkout the self-provisioning service at [WebRTC Signalling Server](https://webrtcserver.ddns.net/licensed).
- a bit of Android developer skills and a recent version of **Android Studio** plus the **Android SDK** installed on your machine.
- an **Internet access**.

## What does this sample project do?

The sample demonstrates how to enable your Android device to be ready for incoming WebRTC calls from another party in order to forward the video from the connected drone in real-time to a consumer. The other party can either use our [WebRTC Signalling Server](https://webrtcserver.ddns.net/licensed) or any other WebRTC client (e.g. GStreamer). The easiest way for a first instant success it to use the [WebRTC Signalling Server](https://webrtcserver.ddns.net/licensed).

Whereas the `ConnectionActivity.java` class is widely the unchanged original, the `MainActivity.java` has been completely refactored. It now contains `GLSurfaceView` for displaying the decoded video and a `Start` button to enable WebRTC functionality.

On `Start` the app immediately begins to obtain H.264 from the drone's camera and displays the decoded YUV signal in the `GLSurfaceView`. In parallel it contacts a WebRTC signalling server, using the credential tuple (userId, licenseKey), which has been obtained earlier as described above. If the login process is successful, the app is ready for incoming calls, which can be issued by either the [WebRTC Signalling Server](https://webrtcserver.ddns.net/licensed).

You can hangup and call again from remote, but once you hit `Stop`, the library respectively the Android app  will not respond to calls anymore, until the next `Start` happens.

In case the colors displayed look weird, play a bit with the `StreamerConfig.setYuvFormat()` function. 

For details for the API provided by the DJI Streamer Lib please refer to the [DJI Streamer Lib](https://github.com/neilyoung/djistreamerlib).

## What are you supposed to do?

- Clone this repository and open it in Android Studio.
- Provide your DJI SDK key in `AndroidManifest.xml`. The place is marked. 
- Provide your DJI Streamer Lib credential tuple in `MainActivity.java`. The place is marked.
- Compile and install the app on your Android device, which is connected to the Remote Control of the drone via USB. It goes without saying, that all connected devices should be powered up. **You cannot run this app on an emulator**.
- Once the app is installed, you will be asked whenever you plug the RC via USB, which application you would like to run. Choose this, but not as a permanent choice.
- Follow the little tutorial above regarding the `Start/Stop` functionality.
  
## Note

Since this app deals with some large files it might be necessary to checkout the statement regarding `git lfs` from the original sample [Android-VideoStreamDecodingSample](https://github.com/DJI-Mobile-SDK-Tutorials/Android-VideoStreamDecodingSample). We haven't found a real reason for this requirement, since the largest file is about 50 MB "only", but you never know.

## Status

This application as well as the [DJI Streamer Lib](https://github.com/neilyoung/djistreamerlib) is beta and has been successfully tested on a **DJI Mavic Pro** and a **DJI Spark**.
