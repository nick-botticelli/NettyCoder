# NettyCoder
[![Java CI with Gradle](https://github.com/nick-botticelli/NettyCoder/workflows/Java%20CI%20with%20Gradle/badge.svg)](https://github.com/nick-botticelli/NettyCoder/actions?query=workflow%3A%22Java+CI+with+Gradle%22)

NettyCoder is a custom HTTP server for encoding videos. Built with Java, Netty, Jackson, SnakeYAML, and FastUtil, this
project utilizes test-driven development (TDD), continuous integration (CI), JSON technology for requests and responses,
YAML for configuration, generics for enhanced code reuse, and a multi-threaded HTTP protocol.

## Building
1. Download or clone repository
2. Build project with Gradle by using the command `./gradlew` on Unix-like operating systems or `.\gradlew` on Windows
   (refer to the [Java CI with Gradle GitHub workflow](./.github/workflows/gradle.yml) for more information). Output is 
   located in `build/libs` in both the `client` and `server` directories.
3. Optionally, run tests with the command `./gradlew test` on Unix-like operating systems or `.\gradlew test` on
   Windows.

## Using
Run the application with Java 8 or above (e.g., `java -jar nettycoder.jar`).

## Notes
* This is a work in progress—more functionality will slowly be added overtime.

## License
GNU Affero General Public License (AGPLv3)
