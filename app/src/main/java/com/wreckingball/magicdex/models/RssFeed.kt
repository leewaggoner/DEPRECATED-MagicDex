package com.wreckingball.magicdex.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class RssFeed {
    @set:Element
    @get:Element
    var channel: RssChannel? = null

    override fun toString(): String {
        return "RssFeed [channel=${channel}]"
    }
}