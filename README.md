# android-kmm-research

The TMDB simple project will demonstrate how Kotlin Multipleplatform Mobile adapt to architecture base clean code. The KMM help to share business in common module and each Native UI Mobile can use without taking care concrete business.

The feature of App (Android)
- List of movie, trending list and search list
- Detail of movie when click on each item
- Cache movies in given minute or until pull to refresh, in case app has no connection
- Quickly open movie detail by deeplink, with format: tmdb://movie/{${movieId}}
  
The feature of App iOS
- List of movie, trending list, simple UI
- App logg to OSLog when click on each item

Config for testing
- change cache time, set Constant.CONFIG_MINUTE_CACHE to expected minute
- Change TMDB API key, set NetworkConfiguration.PERSONAL_KEY to expected key
- To quickly clear cache movies, try to pull to refresh in trending list
- For logging (INFO), use tag: TheMovieDatabase_Info

Note
- App is used for demonstrate how KMM work for both android & IOS
- This implementation does not have unittest

The Screenshot after testing in Android + IOS, log also:
https://drive.google.com/drive/folders/1ofnPqSUJAsD_qGs2Uf5YRzkNK0l5wqKI?usp=sharing
  
Reference:
- https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-ktor-sqldelight.html
- Android Compose
- Swift UI
- https://developer.themoviedb.org/docs/getting-started

THE END
