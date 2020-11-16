---
title: Measure installs
description: Measure installs
---

# Measure installs

The Android Advertiser SDK contains a submodule that allows you to measure installs generated by CPI (Cost per Install) campaigns.

Once you’ve done that you’ll need to call the <strong>handleInstall</strong> method, preferably in your <strong>AppDelegate</strong>, in the <strong>application:didFinishLaunchingWithOptions:</strong> method.

{% highlight java %}
import tv.superawesome.sdk.advertiser.SAVerifyInstall;

SAVerifyInstall.getInstance().handleInstall(this, new SAVerifyInstall.Interface() {
  @Override
  public void saDidCountAnInstall(boolean success) {
    // handle callback
  }
});
{% endhighlight %}

The method will only run once per application lifecycle, usually when the application is first installed on a user’s phone.

It also contains a simple callback with a boolean success parameter that lets you know if AwesomeAds considered this install as having been generated by a CPI campaign.

{% include alert.html type="info" title="Warning" content="Please be aware that not implementing this method at all will mean you won’t be able to measure installs coming from AwesomeAds CPI campaigns, at all." %}