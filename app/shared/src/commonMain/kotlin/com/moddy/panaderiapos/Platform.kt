package com.moddy.panaderiapos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform