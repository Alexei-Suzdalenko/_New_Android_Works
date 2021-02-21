package sirniy.teremok.first_youtube
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.admin_activitys.utiles.Product
import sirniy.teremok.first_youtube.tab_top.IndexActivity
import sirniy.teremok.utils.Constants
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


    //  Handler().postDelayed({
    //      // startActivity(Intent(this, UserProfileActivity::class.java))
    //      // startActivity(Intent(this, RegisterActivity::class.java))
    //       startActivity(Intent(this, LoginActivity::class.java))
    //      // startActivity(Intent(this, MainActivity::class.java))
    //      finish()
    //  }, 500)
        requestDataToDatabase()

    }

    fun requestDataToDatabase(){
        App.GlobalListProduct = arrayListOf()
        App.GlobalListProductCategory = arrayListOf()

        FirebaseFirestore.getInstance().collection(Constants.PRODUCTS).get()
            .addOnSuccessListener { DocumentSnapshot ->
                try {
                    for(document in DocumentSnapshot.documents){
                        val product: Product? = document.toObject<Product>()
                        if(product != null) {
                            App.GlobalListProduct.add(product)
                            if(App.GlobalListProductCategory.indexOf(product.category) == -1 ){
                                App.GlobalListProductCategory.add(product.category)
                            }
                        }
// https://stackoverflow.com/questions/7145606/how-do-you-save-store-objects-in-sharedpreferences-on-android
                        Log.d("tag", "==> " + App.GlobalListProduct.toString())
                        val gson = Gson()
                        val json = gson.toJson(App.GlobalListProduct)
                        Log.d("tag", "==> $json")
                    }
                } catch (e: Exception){}
                startActivity(Intent(this, IndexActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                requestDataToDatabase()
                count++
                if(count > 7){
                    startActivity(Intent(this, IndexActivity::class.java))
                    finish()
                }
            }
    }
}