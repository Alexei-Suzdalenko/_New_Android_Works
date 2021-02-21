package sirniy.teremok.admin_activitys
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.admin_add_product.*
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.AdminFirestoreProducts
import sirniy.teremok.admin_activitys.utiles.Product
import sirniy.teremok.first_youtube.BaseActivity
import sirniy.teremok.utils.Constants
import sirniy.teremok.utils.GlideLoader
import java.io.IOException
import java.lang.Exception
class AdminCreateProduct : BaseActivity() {
    var categoryName: String = ""
    var categoryId: String = ""
    var position: Int = 0
    private var selectedImageUri: Uri? = null
    private var userProfileImageUrl: String = ""
    lateinit var product: Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_add_product)

        try{
            categoryId   = intent.getStringExtra(Constants.CATEGORY_ID)!!
            categoryName = intent.getStringExtra (Constants.CATEGORY_NAME)!!
            position     = intent.getIntExtra(Constants.POSITION, 0)
        } catch (e: Exception){}

        setSupportActionBar(toolbar)
        title = resources.getString(R.string.category) + " " +  categoryName
        toolbar.subtitle = resources.getString(R.string.add_product)
        toolbar.setElevation(0F)
        toolbar.setBackground(ColorDrawable(Color.parseColor("#E86441")));

        image_product.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                showErrorSnackBar(resources.getString(R.string.you_are_have_permission_to_read), false)
                Constants.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_CODE)
            }
        }

        save_new_product.setOnClickListener {
            if(name_product.text.toString().length < 3 || description_product.text.toString().length < 3 || price_product.text.toString().toInt() == 0 || selectedImageUri == null){
                Toast.makeText(this, "Введите имя продукта, описание, изображение и цену", Toast.LENGTH_LONG).show();
            } else {
                showProgressDialog("Подождите")
                AdminFirestoreProducts().saveImageProduct(selectedImageUri!!, this)
            }
        }

    }


    fun gotoListProduct(product_category: String){
        hideProgressDialog()
        startActivity(Intent(this, AdminListProducts::class.java).putExtra(Constants.CATEGORY_NAME, product_category))
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showErrorSnackBar(resources.getString(R.string.you_are_have_permission_to_read), false)
                Constants.showImageChooser(this)
            } else {
                showErrorSnackBar(resources.getString(R.string.you_dont_have), true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if(data != null){
                    try {
                        selectedImageUri = data.data
                        GlideLoader(this).loadPicture(selectedImageUri!!, image_product)
                    } catch (ex: IOException){
                        showErrorSnackBar(ex.message.toString(), true)
                    }
                }
            }
        }
    }

    fun imageUploadSucess(imageUrl: String){
        val id: String = System.currentTimeMillis().toString()
        product = Product(
            id,
            name_product.text.toString(),
            description_product.text.toString(),
            price_product.text.toString().toInt(),
            imageUrl,
            categoryName
        )
        AdminFirestoreProducts().saveProduct(this, product)
    }

}