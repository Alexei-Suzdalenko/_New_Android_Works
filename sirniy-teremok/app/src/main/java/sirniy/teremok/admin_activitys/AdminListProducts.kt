package sirniy.teremok.admin_activitys
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.admin_list_products.*
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.AdminFirestoreProducts
import sirniy.teremok.admin_activitys.utiles.Product
import sirniy.teremok.admin_activitys.utiles.ProductAdapter
import sirniy.teremok.first_youtube.SplashActivity
import sirniy.teremok.utils.Constants
import java.lang.Exception
class AdminListProducts : AppCompatActivity() {
    var categoryName: String = ""
    lateinit var listProductFromCategory: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_list_products)

        try{
            categoryName = intent.getStringExtra (Constants.CATEGORY_NAME)!!
        } catch (e: Exception){}
        title = "Товары категории $categoryName"
        AdminFirestoreProducts().getListProductFromCategory(categoryName, this)

        list_products.setOnItemClickListener { parent, view, position, id ->
            showMeOptionsFromListProduts(listProductFromCategory[position], )
        }


    }

    override fun onBackPressed() {
        startActivity(Intent(this, SplashActivity::class.java)); finish()
    }

    fun getMeListProductFromCategory(x: MutableList<Product>){
        if(x.size == 0) Toast.makeText(this, "Товаров еще нет!!!", Toast.LENGTH_LONG).show()
        listProductFromCategory = x
        list_products.adapter = ProductAdapter(this, listProductFromCategory)
    }

    fun showMeOptionsFromListProduts(product: Product){
        val datas = arrayOf("Удалить", "", "Отменить")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.choose_action))
        builder.setItems(datas) { _, which ->
            when(which.toString()){
                "0" -> {
                    showMeOptionsToDeleteProduct(product)
                }
            }
        }
        builder.show()
    }

    fun showMeOptionsToDeleteProduct(product: Product){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Удалить товар?")
        builder.setPositiveButton(resources.getString(R.string.yes)){ _ , which ->
            Toast.makeText(this, resources.getString(R.string.start_deleting), Toast.LENGTH_LONG).show()
            AdminFirestoreProducts().deleteProductAndImage(product, this)
        }
        builder.setNegativeButton(resources.getString(R.string.no)){ dialog, which -> }
        builder.show()
    }

    fun finishMeAndOpenMe(){
        val i = intent; startActivity(i); finish()
    }

}