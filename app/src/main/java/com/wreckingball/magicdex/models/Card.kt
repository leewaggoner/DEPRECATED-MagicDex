package com.wreckingball.magicdex.models

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card (
    @PrimaryKey(autoGenerate = true) val cardId: Long,
    var name: String?,
    var names: List<String>?,
    var manaCost: String?,
    var cmc: Int?,
    var colors: List<String>?,
    var colorIdentity: List<String>?,
    var type: String?,
    var superTypes: List<String>?,
    var types: List<String>?,
    var subTypes: List<String>?,
    var rarity: String?,
    var set: String?,
    var setName: String?,
    var text: String?,
    var flavor: String?,
    var artist: String?,
    var number: String?,
    var power: String?,
    var toughness: String?,
    var layout: String?,
    var multiverseid: Int?,
    var imageUrl: String?,
    var variations: List<String>?,
    var rulings: List<Rulings>?,
    var foreignNames: List<ForeignNames>?,
    var printings:List<String>?,
    var originalText: String?,
    var originalType: String?,
    var legalities: List<Legalities>?,
    var id: String?
) {
    object Diff {
        val DIFF_ITEMS = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.name == newItem.name
                        && oldItem.manaCost == newItem.manaCost
                        && oldItem.cmc == newItem.cmc
                        && oldItem.type == newItem.type
                        && oldItem.rarity == newItem.rarity
                        && oldItem.set == newItem.set
                        && oldItem.setName == newItem.setName
                        && oldItem.text == newItem.text
                        && oldItem.flavor == newItem.flavor
                        && oldItem.artist == newItem.artist
                        && oldItem.number == newItem.number
                        && oldItem.power == newItem.power
                        && oldItem.toughness == newItem.toughness
                        && oldItem.layout == newItem.layout
                        && oldItem.multiverseid == newItem.multiverseid
                        && oldItem.imageUrl == newItem.imageUrl
                        && oldItem.originalText == newItem.originalText
                        && oldItem.originalType == newItem.originalType
                        && oldItem.id == newItem.id
            }
        }
    }
}