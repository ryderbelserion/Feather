# Feather
Provides a set of opinionated utilities that may or may not make your life easier.

### Features
- Ability to send customized embeds to discord webhooks by running ./gradlew webhook

### Usage
```kotlin
plugins {
    id("com.ryderbelserion.feather.core") version "0.1.0"
}

tasks {
    webhook {
        post("insert_discord_webhook_url_here")

        content("This is content!")

        username("Ryder Belserion")

        embeds {
            embed {
                title("This is a title")

                description("This is a description")

                color("#e91e63")
            }
        }
    }
}
```