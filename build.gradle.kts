plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "2.2.4"
}

group = "net.sneakymouse"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION


dependencies {
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.release = 21
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}