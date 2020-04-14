# neo4j-labs-pages

[neo4j.com/labs](https://neo4j.com/labs) pages.

## Tasks

Convert to HTML:

    ./gradlew convert

Clean:

    ./gradlew clean

Publish to WordPress:

    ./gradlew wordPressUpload -Pwordpress-host="neo4j.com" -Pwordpress-username={{username}} -Pwordpress-password={{password}} 

**NOTE:** You must replace `{{username}}` and `{{password}}` in the above command.

## Important AsciiDoc attributes

To publish to WordPress we are using the following AsciiDoc attributes:

- `slug`: A unique page slug (defined on each document).
- `parent-path`: The path of the parent page (defined in `build.gradle`). In this case, we are using the value `/labs`.

## Stage

The publication stage is either `production` or `testing`.
In testing, the `slug` is automatically prefixed by `_testing_`.
It allows to publish a "testing" page without interfering with the "production" page (because we are using two distinct slugs).