# Mongoose OS & AWS IoT - Neopixel and Thing Shadow with Android Companion App

## Overview
This app allows for easy neopixel control using AWS IoT and comes with an
Android App.

## How to install this app

## Step 1: Import

##### Via MOS UI tool
- Install and start [mos tool](https://mongoose-os.com/software.html)
- Switch to the Project page, find and import this app(if available otherwise follow the steps below), build and flash it:

<p align="center">
  <img src="https://mongoose-os.com/images/app1.gif" width="75%">
</p>

##### If app not available in MOS tool UI

- Clone this repo ```git clone https://github.com/austinn/AWS-IoT-Neopixel-MongooseOS.git```
- Go into the directory `mos-aws` and run `mos ui` which opens the project in UI

## Step 2: Set up Wi-Fi

- You can do this via mos tool ui or using command `mos wifi WIFI_NAME WIFI_PASSWORD`

## Step 3: Set up and Provision AWS IoT thing, Policy

- You can do this via mos tool ui or using command `mos aws-iot-setup --aws-iot-policy mos-default`
- Create a thing in AWS IoT and you are done

## Step 4: Configure the Android App with AWS Keys

- Clone this repo ```git clone https://github.com/austinn/AWS-IoT-Neopixel-Android```
- Import project into Android Studio
- Edit the file /utils/Constants.java
- Add your device names to /res/strings.xml
- Build and upload your .apk to a device

## Step 5: Connect Neopixels

- Connect your Neopixel data line to pin5 (default)

Note* You can edit the pin and/or numPixels in the init.js file in the mos tool ui

## Screenshots
![Android Companion App](http://i.imgur.com/WoNeQRom.png)
