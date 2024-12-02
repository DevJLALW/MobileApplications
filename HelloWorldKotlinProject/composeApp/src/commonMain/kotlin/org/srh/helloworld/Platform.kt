package org.srh.helloworld

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform