package eup.mobi.example.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsEasyJSONObject {

    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("results")
    @Expose
    var results: MutableList<Result>? = null

    inner class Result {

        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("key")
        @Expose
        var key: String = ""
        @SerializedName("value")
        @Expose
        var value: Value? = null
    }

    inner class Value {
        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("title")
        @Expose
        var title: String = ""
        @SerializedName("desc")
        @Expose
        var desc: String = ""
        @SerializedName("image")
        @Expose
        var image: String = ""
        @SerializedName("jlpt")
        @Expose
        var jlpt: MutableList<Int>? = null
        @SerializedName("source")
        @Expose
        var source: String? = null
    }
}
