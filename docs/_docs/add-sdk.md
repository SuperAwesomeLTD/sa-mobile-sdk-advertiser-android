---
title: Add the SDK through Gradle
description: Add the SDK through Gradle
---

# Add the SDK through Gradle

The simplest way of adding the Android Publisher SDK to your Android Studio project is to download the AAR library through Gradle.

The first step is to include the following Maven repository in your module’s <strong>build.gradle</strong> file (usually the file under MyApplication/app/):

{% highlight gradle %}
repositories {
    maven { url "http://dl.bintray.com/superawesome/SuperAwesomeSDK" }
    maven { url  'http://dl.bintray.com/gabrielcoman/maven' }
}
{% endhighlight %}

Next you need to add the following dependency:

{% highlight gradle %}
dependencies {
    implementation 'tv.superawesome.sdk.advertiser:superawesomeadvertiser:{{ site.latest_version }}'
}
{% endhighlight %}

Once you’ve added the Android Advertiser SDK, you can access all functionality by including:

{% highlight java %}
import tv.superawesome.sdk.advertiser.*;
{% endhighlight %}
