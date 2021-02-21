package sirniy.teremok.user
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_product.*
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.first_youtube.tab_top.IndexActivity
import sirniy.teremok.utils.GlideLoader
class ProductActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        GlideLoader(this).loadPictureString(App.product.image, imageViewProduct)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels / 2
        imageViewProduct.layoutParams.height = height

        titleProduct.text = App.product.name
        descriptionProduct.text = App.product.description
        priceProduct.text = App.product.price.toString() + " руб."
    }
}