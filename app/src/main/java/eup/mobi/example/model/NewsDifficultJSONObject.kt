package eup.mobi.example.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsDifficultJSONObject {
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

    inner class Result {

        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("key")
        @Expose
        var key: Long? = null
        @SerializedName("value")
        @Expose
        var value: Value? = null

    }

    inner class Value {

        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("desc")
        @Expose
        var desc: String? = null
        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("jlpt")
        @Expose
        var jlpt: List<Int>? = null
    }
}
