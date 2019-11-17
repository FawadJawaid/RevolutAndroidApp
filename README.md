# Revolut - Android Test Application
This is the currency converter android app which has been designed for according to the requirements using MVP architecture.

## Information About The Solution Of Revolut Coding Challenge

**Candidate Name:** Muhammad Fawad Jawaid Malik

**Email:** muhammad.fawadjawaid@gmail.com

#### General Information
I have created the Splash Screen which shows the Revolut logo. Even the app icon is using the Revolut logo. Moreover, the app has also been named as Revolut.

### Implementation
The project architecture of the application is very clean and easy to understand, MVP (Model View Presenter) design pattern along with clean architecture abilities and view model has been followed. MVP is a good fit with RxJava, Android Architecture Components: Room and ViewModel. Retrofit has been used for the consumption of the REST API. Coroutines have been used for calling and fetching data from the API. Besides that, the latest dependency injection framework, Koin has been used for the dependency injection of Presenters and ViewModels. The Room database library has been used for saving data into the database. For the designing of the layouts, I have used ConstraintLayout, RelativeLayout, and LinearLayout in this project. 
The architecture, I have followed leads to clean architecture. So in the end, there are mainly three architecture-layers in the project; Presentation Layer (View, ViewModel, Presenter, Adapter), Domain Layer (Entity, Model) and Data Layer (Rest Client, Repository, Persistance). Packages have been made in the project according to these layers.
I have used the Developer.Android recommended application architecture where the ViewModels are interacting with the data source (web service) through the presenter (in our case can be considered as a repository). The repository (pattern) decouples the application from the data sources and isolates the data layer. ViewModel doesn’t have any information whether the data is coming from the API or the database. The Presenter makes some changes in View interface, which is later implemented in the activity to incorporate those.
Here is the link of the recommended app architecture by developer android:
https://developer.android.com/jetpack/docs/guide#recommended-app-arch

### Testing
I have also written Integration test to check whether data is being loaded in the RecyclerView. This test case can be seen in *MainActivityTest* file.

