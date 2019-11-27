package eup.mobi.example.utils

import eup.mobi.example.model.NewsContentJSONObject
import eup.mobi.example.model.NewsDifficultJSONObject
import eup.mobi.example.model.NewsEasyJSONObject
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object GetNewsHelper {

    private var maziiApi: MaziiApi? = null
//    private var apiMaziiApi: ApiMaziiApi? = null

    fun getMaziiApi(): MaziiApi {
        if (maziiApi == null) {
            maziiApi = Retrofit.Builder()
                .baseUrl("http://mazii.net/")
                .client(NetWorkHelper.getLogging())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MaziiApi::class.java)
        }
        return maziiApi!!
    }

//    fun getApiMaziiApi(): ApiMaziiApi {
//        if (apiMaziiApi == null) {
//            apiMaziiApi = Retrofit.Builder()
//                    .baseUrl(BASE_URL_API_MAZII_NET)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build().create(ApiMaziiApi::class.java)
//        }
//        return apiMaziiApi!!
//    }

    interface MaziiApi {
        @GET("api/news/{page}/{limit}")
        fun getNewsEasy(@Path("page") page: Int, @Path("limit") limit: Int): Observable<NewsEasyJSONObject>

        @GET("api/news_normal/{page}/{limit}")
        fun getNewsDifficult(@Path("page") page: Int, @Path("limit") limit: Int): Observable<NewsDifficultJSONObject>

        @GET("api/news/{id}")
        fun getNewsContent(@Path("id") id: String): Observable<NewsContentJSONObject>
    }
//
//    interface ApiMaziiApi {
//        @GET("ej/api/gettrans/{id_news}/{lag_code}")
//        fun getNumberTranslate(@Path("id_news") id: String, @Path("lag_code") lag_code: String): Observable<BadgeTranslate>
//    }
}
