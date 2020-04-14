plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  google()
  jcenter()
}

dependencies {
  implementation("com.squareup.okhttp3:okhttp:4.4.0")
  implementation("org.yaml:snakeyaml:1.25")
  implementation("com.beust:klaxon:5.0.1")
  implementation(gradleApi())
}

gradlePlugin {
  plugins {
    create("wordpressPlugin") {
      id = "com.neo4j.gradle.wordpress.WordPressPlugin"
      implementationClass = "com.neo4j.gradle.wordpress.WordPressPlugin"
    }
  }
}
