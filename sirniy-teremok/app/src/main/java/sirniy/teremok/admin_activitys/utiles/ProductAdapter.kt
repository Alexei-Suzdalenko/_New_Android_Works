package sirniy.teremok.admin_activitys.utiles
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import sirniy.teremok.R
import sirniy.teremok.utils.GlideLoader

class ProductAdapter(val c: Context, val list: MutableList<Product> ): BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layotInflater   = LayoutInflater.from(c)
        val rowMain         = layotInflater.inflate(R.layout.list_product_layout  , parent, false)
        val admin_image_show: ImageView = rowMain.findViewById(R.id.admin_image_show)
        GlideLoader(c).loadPictureString(list[position].image, admin_image_show)
        val admin_title: TextView = rowMain.findViewById(R.id.admin_title)
        admin_title.text = list[position].name
        val admin_price: TextView = rowMain.findViewById(R.id.admin_price)
        admin_price.text = list[position].price.toString()
        return rowMain
    }

    override fun getCount(): Int {
        return list.size
    }
    override fun getItem(position: Int): Any {
        return list[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}