package sirniy.teremok.first_youtube.tab_top.ui.main
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.admin_activitys.utiles.Product
import sirniy.teremok.user.BasketActivity
import sirniy.teremok.user.ListProductForUserAdapter
import sirniy.teremok.user.ProductActivity
import sirniy.teremok.user.UserProfilEdit
import sirniy.teremok.utils.Constants

class PlaceholderFragment : Fragment() {
    private var index = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index =  arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val nameCategory =  App.GlobalListProductCategory[index]
        val customListProduct: MutableList<Product> = arrayListOf()

        for (i in 0 until App.GlobalListProduct.size){
            val productCustom = App.GlobalListProduct[i]
            if(productCustom.category == nameCategory){
                customListProduct.add(productCustom)
            }
        }

        val root = inflater.inflate(R.layout.fragment_tab_top, container, false)
        val list_product_user: ListView = root.findViewById(R.id.list_product_user)
            list_product_user.adapter = ListProductForUserAdapter(activity!!.applicationContext, customListProduct)

        // list_product_user.setOnItemClickListener { parent, view, position, id ->
        //     val userId = App.sharedPreferences.getString(Constants.USER_ID,  "")
        //    if(userId.isNullOrEmpty()){
        //        Toast.makeText(activity!!.applicationContext, resources.getString(R.string.for_add_products_to_batterf), Toast.LENGTH_LONG).show()
        //        startActivity(Intent(activity!!.applicationContext, UserProfilEdit::class.java))
        //    } else {
        //        App.product = customListProduct[position]
        //        startActivity(Intent(activity!!.applicationContext, ProductActivity::class.java))
        //    }
        // }
        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}