package eup.mobi.example.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsContentJSONObject {
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("result")
    @Expose
    var result: Result? = null

    var isFavorite = false

    inner class Result {

        @SerializedName("_id")
        @Expose
        var id: String? = null
        @SerializedName("_rev")
        @Expose
        var rev: String? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("link")
        @Expose
        var link: String? = null
        @SerializedName("nameLink")
        @Expose
        var nameLink: String? = null
        @SerializedName("pubDate")
        @Expose
        var pubDate: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("content")
        @Expose
        var content: Content? = null
        @SerializedName("jlpt")
        @Expose
        var jlpt: List<Int>? = null
        @SerializedName("levelWords")
        @Expose
        var levelWords: LevelWords? = null
        @SerializedName("type")
        @Expose
        var type: String? = null
        @SerializedName("list_topik")
        @Expose
        var listTopik: String? = null
        @SerializedName("post_date")
        @Expose
        var postDate: Int? = null

    }

    inner class LevelWords {
        @SerializedName("1")
        @Expose
        var _1: List<String>? = null
        @SerializedName("2")
        @Expose
        var _2: List<String>? = null
        @SerializedName("3")
        @Expose
        var _3: List<String>? = null
        @SerializedName("4")
        @Expose
        var _4: List<String>? = null
        @SerializedName("5")
        @Expose
        var _5: List<String>? = null
        @SerializedName("unknown")
        @Expose
        var unknown: List<String>? = null

    }

    inner class Content {

        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("video")
        @Expose
        var video: String? = null
        @SerializedName("audio")
        @Expose
        var audio: String? = null
        @SerializedName("textbody")
        @Expose
        var textbody: String? = null
        @SerializedName("textmore")
        @Expose
        var textmore: String? = null

    }
}
