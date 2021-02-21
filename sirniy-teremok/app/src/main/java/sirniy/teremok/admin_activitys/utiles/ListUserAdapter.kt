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
import sirniy.teremok.utils.User
class ListUserAdapter(private val c: Context, private val listUser: MutableList<User>) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layotInflater   = LayoutInflater.from(c)
        val rowMain         = layotInflater.inflate(R.layout.list_users_tab_layout  , viewGroup, false)
        val user_company     = rowMain.findViewById<TextView>(R.id.user_company)
            user_company.text = listUser.get(index = position).company
        val user_name     = rowMain.findViewById<TextView>(R.id.user_name)
            user_name.text = listUser.get(index = position).name
        val user_tlf     = rowMain.findViewById<TextView>(R.id.user_tlf)
            user_tlf.text = listUser.get(index = position).tlf
        val admin_image_show = rowMain.findViewById<ImageView>(R.id.admin_image_show)
            if(listUser[position].image.toString().length < 10){}
            else { GlideLoader(c).loadUserImage(listUser[position].image, admin_image_show) }
        return rowMain
    }

    override fun getItem(position: Int): Any {
        return listUser[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listUser.size
    }
}