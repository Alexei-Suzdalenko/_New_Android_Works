package sirniy.teremok.admin_activitys.utiles
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sirniy.teremok.utils.Backet
import sirniy.teremok.utils.Constants
class App: Application() {
    companion object {
        lateinit var reference: DatabaseReference
        lateinit var GlobalListProduct: MutableList<Product>
        lateinit var GlobalListProductCategory: MutableList<String>
        lateinit var sharedPreferences: SharedPreferences
        lateinit var product: Product
        lateinit var basketProducts: MutableList<Backet>
    }

    override fun onCreate() {
        super.onCreate()
        reference = Firebase.database.getReference(Constants.CATEGORIES)
        GlobalListProduct = arrayListOf()
        GlobalListProductCategory = arrayListOf()
        sharedPreferences = getSharedPreferences(Constants.SHARED_TAG,  Context.MODE_PRIVATE)
        basketProducts = arrayListOf()
    }
}