# mpv remote app
An Android remote that allow user to control [mpv](https://mpv.io/) on a remote host.
This project is meant to be used with this [mpv plugin](https://github.com/orchidae/mpv-remote-server).
# Installation
Clone the repository, and install Android Studio.
Then, open the project and install the app as a debug build. You must install the app on an Android device that has developper mode enabled.
# How it works
The app starts by listening to a server broadcast. Once it receive it (with the server's host IP), it can connect to the server. Wi-Fi must be enabled on the Android device for the remote to work.
Then, the server will simply retransmit commands to the mpv's IPC socket, and send response from the socket back to the Android remote.
The system was designed with simplicity in mind, the Android remote should automatically connect to the server without the need to input host IP manually.
# Security concerns
The IPC socket mechanism of mpv is by definition not secure, and while I attempted to make the remote connection somewhat safe, I am by no mean a cyber-security expert.
I developped this for personnal usage. **Use it at your own risks!**
