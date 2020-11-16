---
title: Integrate with MoPub
description: Integrate with MoPub
---

# Integrate with MoPub

If you already have MoPub ads serving in your app, but want to integrate AwesomeAds as well, without having to directly use the Android Publisher SDK, you can follow the steps below:

## Add the MoPub plugin

You can either change your module’s `build.gradle` file (usually the file under MyApplication/app/) to the following format:

{% highlight gradle %}
repositories {
    maven { url "http://dl.bintray.com/superawesome/SuperAwesomeSDK" }
}

dependencies {
    # add the Android Publisher SDK
    compile 'tv.superawesome.sdk.publisher:superawesome:{{ site.latest_version }}'

    # add MoPub plugin
    compile 'tv.superawesome.sdk.publisher:samopub:{{ site.latest_version }}'
}
{% endhighlight %}

## Setup MoPub Custom Networks

Login to the MoPub dashboard using your preferred account.

From here forward the tutorial assumes you have an Android app with three ad units setup in MoPub; one banner, one interstitial ad and one rewarded video ad:

![image-title-here]({{ site.baseurl }}/assets/img/IMG_07_MoPub_0.png){:class="img-responsive"}

From your MoPub admin interface you should create a `New Order`

![image-title-here]({{ site.baseurl }}/assets/img/mopub-create-order.png){:class="img-responsive"}

From the next menu, select `New line item`

![image-title-here]({{ site.baseurl }}/assets/img/mopub-line-item.png){:class="img-responsive"}

Notice that the custom event class names required by MoPub are:
 - <strong>for Banner Ads:</strong>
 
 `tv.superawesome.plugins.publisher.mopub.AwesomeAdsMoPubBanner`
 - <strong>for Interstitial Ads:</strong> 
 
 `tv.superawesome.plugins.publisher.mopub.AwesomeAdsMoPubInterstitial`
 - <strong>for Rewarded Video Ads:</strong> 
 
 `tv.superawesome.plugins.publisher.mopub.AwesomeAdsMoPubVideo`

And, you can tell MoPub what AwesomeAds ads to load and how to display them by filling out the custom event class data field with a JSON similar to this:

```json
{
    "placementId": 30473,
    "isTestEnabled": true or false,
    "isParentalGateEnabled": true or false,
    "orientation": "ANY" or "PORTRAIT" or "LANDSCAPE",
    "shouldShowCloseButton": false or false,
    "shouldAutomaticallyCloseAtEnd": true or false,
    "shouldShowSmallClickButton": true or false
}
```

- In the second tab `Ad unit targeting`, <strong>Select</strong> your App&ad unit e.g. Banner

- In the third tab `Audience targetting`, <strong>Select</strong> your target audience

*Create multiple line items for banner, interstitial, and video(rewarded) ads.

{% include alert.html type="warning" title="Note" content="To test your adapter integration, you can disable other networks and only enable `AwesomeAds` line items to see if they are being served in your app." %}


## Implement Ads

Initialise the `MoPub` sdk with <strong>AwesomeAds</strong> adapter configuration

```kotlin
MoPub.initializeSdk(this, SdkConfiguration.Builder("_any_ad_unit_id_")
                .withAdditionalNetwork("tv.superawesome.plugins.publisher.mopub.AwesomeAdsMoPubAdapterConfiguration")
                .build()) {
        }
```

Once the previous steps are done, you can add MoPub banners, interstitials and rewarded video ads just as you normally would:

 - <strong>for Banner Ads:</strong>
 [Banners](https://developers.mopub.com/publishers/android/banner/)
 
 - <strong>for Interstitial Ads:</strong> 
 [Interstitials](https://developers.mopub.com/publishers/android/interstitial/)

 - <strong>for Rewarded Video Ads:</strong> 
 [Rewarded Video](https://developers.mopub.com/publishers/android/rewarded-video/)

Since the previously created custom events will run on these ads, and the Android Publisher SDK is integrated alongside the MoPub plugin, you should start seeing ads playing.