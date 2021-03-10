# Features
This is a very basic demo app using the Imgur Gallery Search API. It supports:
- Displays albums for a given query from the Imgur API.
- Upon selecting an album, the app displays a gallery of images for that album.

It doesn’t support the following features:
- Sorting & paging.
- pinch-to-zoom a single photo.
- Display videos.
- Cache response.

When the time is limited, I prefer implementing the must-have features and deliver the app first instead of postponing the launch date to implement all nice-to-have features. In addition, I expect to implement some of them in the next round。

# Design Principles
In general, this app is built on the MVVM architecture. Besides, I also add two other principles:
1. Fragment shouldn’t be the Views in the MVVM, unless the view is super simple. Therefore, for complicated views, I add all presentation logic in the custom view class. This approach has two major advantages:
    - It avoids packing all business logic into one Fragment, so the Fragment is more maintainable.
    - It makes the business logics more modular and thus makes testing easier. In unit tests, we can test each View individually, which is way much easier than testing a big monolith Fragment.
2. There is only one source of truth for the view state. Also, when a view receives the same view state, the UI should always be the same.
    - I borrowed this idea from React and Flutter. For most UI bugs, the root causes are we don’t handle state change correctly. When we have only one source of truth for the view state and the view is idempotent for the same state, we can greatly reduce the number of bugs.

# Tech Stack
Here is a brief introduction of the tech stack and why I choose them.

## Language: Kotlin
Kotlin has many advantages comparing to Java:
- Kotlin leads to safer code (e.g., avoiding NullPointerException) and reduced code size, which eventually improves productivity.
- We can use Kotlin coroutine to execute async jobs. Kotlin coroutine is easier to learn, light-weight, and has good Jetpack integration support.
- We can use Kotlin Parcelize plugin to generate parcelable classes, which reduces a lot of boilerplate code.

## Image Loading: Glide
Glide is one of the well-known image loading libraries for Android apps. It uses both in-memory cache and disk cache so that we can save mobile data for users. Besides, it provides many built-in transformations, for example, cross-fading animation.

## Dependency Injection: Hilt
Hilt is a dependency injection library built on top of Dagger. It reduces a lot of boilerplate code, for example, injecting dependencies into a ViewModel class. Besides, we can easily create scoped dependencies with Hilt, for example, Activity-scoped dependency and Fragment-scoped dependency.

## Networking: Retrofit
Retrofit is the de-facto HTTP client library for Android apps. It requires very minimum configuration to create an HTTP client class for an API. Besides, it is scalable and developers can provide a converter to specify how to convert the received response. The Imgur API returns data in JSON format, so in this app I also add GSON library to deserialize the JSON response into Kotlin objects.

In addition, Retrofit starts supporting the Kotlin suspend function from 2.6.0, so it is a good fit for apps using Kotlin coroutine.

