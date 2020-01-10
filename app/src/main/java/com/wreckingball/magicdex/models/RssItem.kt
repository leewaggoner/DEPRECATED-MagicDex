package com.wreckingball.magicdex.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class RssItem {
    @set:Element
    @get:Element
    var title: String? = null

    @set:Element
    @get:Element
    var link: String? = null

    @set:Element
    @get:Element
    var description: String? = null

    @set:Element
    @get:Element
    var pubDate: String? = null

    @set:Element
    @get:Element
    var pubDateString: String? = null

    override fun toString(): String {
        return "RssItem [title=${title}, link=${link}, pubDate=${pubDate},  description=${description}]"
    }
}