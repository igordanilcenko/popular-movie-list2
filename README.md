# Popular Movie List

A sample Android app that showcases modern Android development practices through a real use case:
browsing popular movies from [TMDb](https://www.themoviedb.org/), viewing details, similar movies,
and user reviews – with pagination, pull-to-refresh, and offline support.

---

## What this project demonstrates

- **Clean Architecture** with strict layer separation: presentation → domain → data, across a multi-module Gradle project
- **MVI pattern** – each screen owns a single immutable `StateFlow<UiState>`; all changes flow through typed intents, making state transitions predictable and fully testable
- **Testing across all layers** – ViewModel state transitions, repository cache/offline behaviour, and whole-screen Compose UI tests
- **Business use cases** cover a reasonable scope for a sample project – the project is scalable, without being over-engineered

---

## Features

- Popular movies list with infinite scroll and pull-to-refresh
- Movie detail screen with similar movies carousel and user reviews
- Per-section loading, error, and retry states
- Offline fallback via Room for cached data
- Graceful error handling throughout

---

## Network Delay and Error Simulation

In debug builds the app enables network simulation via `simulateNetworkDelay()` in `lib-network`,
controlled by the `networkSimulationEnabled` flag (set to `BuildConfig.DEBUG` in
`SampleAppApplication`). Each call adds a random 1–4 second delay and has a chance of
throwing to simulate a transient network error. This makes loading states, transitions, and error
handling clearly visible during presentation. 
The simulation is a no-op in release builds and unit
tests.

---

## Tech Stack

| Area | Libraries |
|---|---|
| Language | Kotlin 2.3 · Coroutines · Flow |
| UI | Jetpack Compose · Material 3 |
| Architecture | Clean Architecture · MVI · Multi-module |
| DI | Koin 4.1 |
| Networking | Retrofit 3 · OkHttp 5 · Moshi |
| Persistence | Room 2.8 |
| Image loading | Coil 3 |
| Testing | JUnit 4 · MockK · Turbine · Compose UI Test |
| Build | Gradle 9 · AGP 9 · KSP2 · Version Catalog |

---

## Architecture

```
app/                    → App entry point, Koin setup, navigation routes
feature/movies/         → Movie list and detail screens
lib/lib-ui/             → Shared Compose components and theme
lib/lib-network/        → Retrofit + Moshi configuration
lib/lib-usecase/        → Base UseCase interfaces
lib/lib-navigation/     → Navigation controller abstraction
```

Each feature module follows a three-layer structure:

```
┌──────────────────────────────────────────────────────────┐
│  Presentation  (Composables + ViewModel)                 │
│    ↓ intents              ↑ StateFlow<UiState>           │
├──────────────────────────────────────────────────────────┤
│  Domain  (UseCases + Repository interfaces)              │
│    • Single-responsibility UseCases                      │
│    • No Android dependencies                             │
├──────────────────────────────────────────────────────────┤
│  Data  (Repository implementations + Room + REST)        │
│    • Network-first; in-memory + Room/SP fallback         │
└──────────────────────────────────────────────────────────┘
```

---

## Data Persistence

The application loads data in this priority:

**Cache → Internet → Database**

If the required data is available in the runtime cache, the app uses it directly – no API call is
made even if a connection is available. This is a good approach for mobile devices where network
conditions are unreliable.

If the data is not cached, the app fetches it from the internet, saves the result to the database
and populates the cache for subsequent reads.

If the internet call fails, the app falls back to data previously saved in the database.

---

## Testing

| Layer | Type | Tools |
|---|---|---|
| ViewModel (MVI state transitions) | JVM unit tests | JUnit 4 · MockK · coroutines-test · Turbine |
| Repository (cache / offline logic) | JVM unit tests | JUnit 4 · MockK · coroutines-test |
| Screen UI (loading / error / content states) | Compose instrumented tests | ui-test-junit4 · createComposeRule |

**ViewModel tests** verify `UiState` transitions including intermediate `Loading` states captured
with Turbine – confirming that states are emitted in the right order, not just that the final state
is correct.

**Repository tests** focus on non-obvious behaviour: in-memory cache hits that prevent duplicate
network calls, Room/SharedPreferences offline fallback, pagination accumulation across pages, and
the guard that rethrows when offline and the database is also empty.

**Compose UI tests** call screen content composables directly with a hand-crafted `UiState` – no
ViewModel, no Koin, no network. Each test is a pure UI assertion.

```bash
./gradlew :feature:movies:testDebugUnitTest          # unit tests
./gradlew :feature:movies:connectedAndroidTest        # instrumented tests (device required)
```

---

## CI

Every pull request to `main` runs two GitHub Actions checks:

| Workflow | What it does |
|---|---|
| **Unit Tests** | Runs JVM unit tests via `testDebugUnitTest` |
| **Lint** | Runs Android lint via `lintDebug`; uploads HTML report as artifact |

The `main` branch is protected – direct pushes are blocked, requiring all changes to go through
a pull request.

---

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
