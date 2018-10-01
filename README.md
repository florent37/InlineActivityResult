Inline Activity Result
===================

[![Language](https://img.shields.io/badge/compatible-java%20%7C%20kotlin%20%7C%20rx-brightgreen.svg)](https://www.github.com/florent37/InlineActivityResult)

**Work in progress**

Receive the activity result directly after the startActivityForResult with InlineActivityResult, choose your way : 
- [Kotlin](https://github.com/florent37/InlineActivityResult#kotlin)
- [Kotlin with Coroutines](https://github.com/florent37/InlineActivityResult#kotlin-coroutines)
- [RxJava](https://github.com/florent37/InlineActivityResult#rxjava)
- [Java8](https://github.com/florent37/InlineActivityResult#java8)
- [Java7](https://github.com/florent37/InlineActivityResult#java7)

**No need to override Activity or Fragment**`onActivityResult(code, permissions, result)`**using this library, you just have to execute InlineActivityResult's methods** 
This will not cut your code flow

# General Usage (cross language)

[ ![Download](https://api.bintray.com/packages/florent37/maven/inline-activity-result/images/download.svg) ](https://bintray.com/florent37/maven/inline-activity-result/)
```java
dependencies {
    implementation 'com.github.florent37:inline-activity-result:(lastest version)'
}
```

```
startForResult(this, new Intent(MediaStore.ACTION_IMAGE_CAPTURE), new ActivityResultListener() {
      @Override
      public void onSuccess(Result result) {
          //the started activity result is RESULT_OK
      }

      @Override
      public void onFailed(Result result) {
          //the started activity result is RESULT_CANCEL
      }
});
```

# Kotlin-Coroutines

```kotlin
launch(UI) {
   try {
       val result = startForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

       //use the result, eg:
       val imageBitmap = result.data?.extras?.get("data") as Bitmap
       resultView.setImageBitmap(imageBitmap)
   } catch (e: InlineActivityResultException) {

   }
}
```

### Download 

[ ![Download](https://api.bintray.com/packages/florent37/maven/inline-activity-result/images/download.svg) ](https://bintray.com/florent37/maven/inline-activity-result/)
```groovy
implementation 'com.github.florent37:inline-activity-result-kotlin:(last version)'
```

# Kotlin

```kotlin
startForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE)) { result ->
     //use the result, eg:
     val imageBitmap = result.data?.extras?.get("data") as Bitmap
     resultView.setImageBitmap(imageBitmap)
}.onFailed { result ->

}
```

### Download 

[ ![Download](https://api.bintray.com/packages/florent37/maven/inline-activity-result/images/download.svg) ](https://bintray.com/florent37/maven/inline-activity-result/)
```groovy
implementation 'com.github.florent37:inline-activity-result-kotlin:(last version)'
```

# RxJava

```java
new RxInlineActivityResult(this).request(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)))
    .subscribe(result -> {
        //use the result, eg:
        Bundle extras = result.getData().getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        resultView.setImageBitmap(imageBitmap);
    }, throwable -> {
        if (throwable instanceof RxInlineActivityResult.Error) {
            final Result result = ((RxInlineActivityResult.Error) throwable).getResult();

        }
    })
```

### Download 
```groovy
implementation 'com.github.florent37:inline-activity-result-rx:(last version)'
```

# Java8

```java
new InlineActivityResult(this)
       .startForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE))
       .onSuccess(result -> {
           //use the result, eg:
           Bundle extras = result.getData().getExtras();
           Bitmap imageBitmap = (Bitmap) extras.get("data");
           resultView.setImageBitmap(imageBitmap);
       })
       .onFail(result -> {
            
       });
```

### Download
 
[ ![Download](https://api.bintray.com/packages/florent37/maven/inline-activity-result/images/download.svg) ](https://bintray.com/florent37/maven/inline-activity-result/)
```groovy
implementation 'com.github.florent37:inline-activity-result:(last version)'
```
 
# Java7

```java
startForResult(this, new Intent(MediaStore.ACTION_IMAGE_CAPTURE), new ActivityResultListener() {
      @Override
      public void onSuccess(Result result) {
          Bundle extras = result.getData().getExtras();
          Bitmap imageBitmap = (Bitmap) extras.get("data");
          resultView.setImageBitmap(imageBitmap);
      }

      @Override
      public void onFailed(Result result) {

      }
});
```

# How to Contribute

We welcome your contributions to this project. 

The best way to submit a patch is to send us a [pull request](https://help.github.com/articles/about-pull-requests/). 

To report a specific problem or feature request, open a new issue on Github. 

# Credits

Author: Florent Champigny [http://www.florentchampigny.com/](http://www.florentchampigny.com/)

Blog : [http://www.tutos-android-france.com/](http://www.tutos-android-france.com/)

Fiches Plateau Moto : [https://www.fiches-plateau-moto.fr/](https://www.fiches-plateau-moto.fr/)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>

# License

    Copyright 2018 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
