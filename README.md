# Popular Movie List Sample App (Compose/Navigation)
## About
This project is a refactor of my previous sample project [popular-movie-list](https://github.com/ihardanilchanka/popular-movie-list) and has the same functionality, but uses the modern stack of technologies, and has modular architecture.

## Project structure
The project has a structure with multiple modules/libs.
The main **app module** is very simple and has only MainActivity initializing and starting navigation screen, the Application class with [Koin](https://insert-koin.io/) initialization, and definition of navigation routes.

**Feature** modules contain screens related to certain the app's functionality, as also domain, and data logic.

**Lib** modules contain reusable classes used by other app's modules, such as network, UI components, or navigation.

## Architecture
The app is implemented in compliance with Google's [Guide to app architecture](https://developer.android.com/jetpack/guide). The navigation around the app uses the [Jetpack Navigation](https://developer.android.com/guide/navigation) library.

### Presentation layer
The main change is using [Jetpack Compose](https://developer.android.com/jetpack/compose) instead of traditional Activity/Fragments and XML layouts. **Composable components** receive UI state from **ViewModel** and update UI. **ViewModels** handle UI state as [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) and update it accordingly to domain logic.

### Domain layer
Domain logic is driven by **UseCases**. Every UseCase is a simple or suspend function that only performs one specific business logic action, such a retrieving data from a repository, or a navigation action.

### Data layer
**Repositories** load data from different sources such as the Internet, Database, or Cache and keep the app's data state.

## Loading delay
I added `randomDelay()` suspend function for making an artificial extra load to repository calls to simulate possible request delay. Also, it may cause an error forcing the user to reload the data.

## License

```
Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements. See the NOTICE file distributed with this work for
additional information regarding copyright ownership. The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```






