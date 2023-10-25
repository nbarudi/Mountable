pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}


rootProject.name = "Mountable-Parent"

startParameter.isParallelProjectExecutionEnabled = true

include("core")
include(":nms:shared")
include(":nms:v1_19_R3")
include(":nms:v1_20_R1")
include(":nms:v1_20_R2")