package eup.mobi.example.utils

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object NetWorkHelper {
    fun isNetWork(context: Context?): Boolean {
        if (context == null) return false
        val connect = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val netInfo = connect.activeNetworkInfo

        return netInfo != null && netInfo.isConnected
    }

    fun isNetWorkMobile(context: Context): Boolean {
        val connect = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val netInfo = connect.activeNetworkInfo

        return netInfo != null && netInfo.isConnected && netInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    fun getLogging(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder().method(original.method(), original.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
                .addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()
    }
}
