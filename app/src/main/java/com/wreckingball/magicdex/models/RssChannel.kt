package com.wreckingball.magicdex.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "channel", strict = false)
class RssChannel {
    @set:Element
    @get:Element
    var title: String? = null

    @set:Element
    @get:Element
    var link: String? = null

    @set:Element(required = false)
    @get:Element(required = false)
    var description: String? = null

    @set:Element
    @get:Element
    var language: String? = null

    @set:ElementList(inline = true, required = false)
    @get:ElementList(inline = true, required = false)
    var item: List<RssItem>? = null

    override fun toString(): String {
        return "Channel [title=${title}, link=${link}, description=${description}, language=${language}, item=$item]"
    }
}