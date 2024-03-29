package com.data.common.enum

enum class EnumBuild(val id: String) {
    RELEASE("release"),
    PREPROD("preprod"),
    QA("qa"),
    DEBUG("debug"),
    LOCAL("local")
}