package eup.mobi.example.model

import android.text.Html
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import com.bumptech.glide.Glide
import eup.mobi.example.R
import eup.mobi.example.customview.furiganaview.FuriganaView

class NewsItem: BaseObservable() {
    var json: String? = null
    var pubdate: Long = 0L
    var id: String? = null

    var title: String = ""
    var date: String = ""
    @Bindable
    var image: String? = ""
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.image)
            }
        }
        get() {
            var converted = field
            if (field != null && !field!!.contains("http")) {
                if (field!!.contains("../")) {
                    converted = "https://www3.nhk.or.jp/news/html/" + field!!.replace("../", "")
                } else if (field!!.contains(".")) {
                    val split = field!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    converted = "https://www3.nhk.or.jp/news/easy/" + split[0] + "/" + split[0] + "." + split[1]
                }
            }

            return converted
        }

    @Bindable
    var viewed = false
    set(value) {
        if(field != value) {
            field = value
            notifyPropertyChanged(BR.viewed)
        }
    }

    companion object {
        @BindingAdapter("imageNews")
        @JvmStatic
        fun loadImage(imageView: ImageView,url: String){
            Glide.with(imageView.context)
                .load(url)
                .placeholder(R.drawable.nhk_logo)
                .into(imageView)
        }

        @BindingAdapter("textFV")
        @JvmStatic
        fun setText(fv: FuriganaView,text: String){
            fv.text_set(Html.fromHtml(text.replace("<ruby.*?>(.+?)<rt>(.+?)</rt></ruby>".toRegex(), "{$1;$2}")).toString(),-1,-1)
        }
    }
}
