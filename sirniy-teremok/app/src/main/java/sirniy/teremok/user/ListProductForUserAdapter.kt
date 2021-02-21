package sirniy.teremok.user
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.admin_activitys.utiles.Category
import sirniy.teremok.admin_activitys.utiles.Product
import sirniy.teremok.utils.Backet
import sirniy.teremok.utils.Constants
import sirniy.teremok.utils.GlideLoader
class ListProductForUserAdapter(private val c: Context, private val listProduct: MutableList<Product>) : BaseAdapter() {
    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layotInflater   = LayoutInflater.from(c)
        val rowMain         = layotInflater.inflate(R.layout.list_product_from_user  , viewGroup, false)
        val imageView       = rowMain.findViewById<ImageView>(R.id.image_user_product)
            GlideLoader(c).loadPictureString(listProduct[position].image, imageView)
            imageView.setOnClickListener {
                val userId = App.sharedPreferences.getString(Constants.USER_ID,  "")
                  if(userId.isNullOrEmpty()){
                      Toast.makeText(c, c.resources.getString(R.string.for_add_products_to_batterf), Toast.LENGTH_LONG).show()
                      c.startActivity(Intent(c, UserProfilEdit::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                  } else {
                      App.product = listProduct[position]
                      c.startActivity(Intent(c, ProductActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                  }
            }
        val title_user_product     = rowMain.findViewById<TextView>(R.id.title_user_product)
        title_user_product.text = listProduct[position].name
        val price_user_product     = rowMain.findViewById<TextView>(R.id.price_user_product)
        price_user_product.text = listProduct[position].price.toString() + " руб."
        val click_minus = rowMain.findViewById<Button>(R.id.click_minus)
        val click_plus = rowMain.findViewById<Button>(R.id.click_plus)
        click_minus.setOnClickListener {

        }
        click_plus.setOnClickListener {
            Log.d("tag", "+++++=> " + listProduct[position].toString() )
            val thisProduct = listProduct[position]
            for(i in 0..App.basketProducts.size){
                val Backet = App.basketProducts[i]
                if(Backet.id == thisProduct.id){

                    break
                } else {
                    val quantityBacket = Backet.quantity + 1
                    val backet = Backet(thisProduct.id, quantityBacket, thisProduct)
                    App.basketProducts.add(backet)
                    break
                }
            }
            Log.d("tag", "+++++=> " + App.basketProducts.toString() )

        }
        return rowMain
    }

    override fun getItem(position: Int): Any {
        return listProduct[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listProduct.size
    }
}



// https://youtu.be/NRqb_AscCyA?list=PLaoF-xhnnrRW_FGeacuT1VLqnRMKfpp4v