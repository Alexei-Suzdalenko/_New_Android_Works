package sirniy.teremok.admin_activitys.ui.main
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.IndexAdminActivity
import sirniy.teremok.admin_activitys.utiles.*
import sirniy.teremok.utils.User
class PlaceholderFragment : Fragment() {
    var index = 0
    // lateinit var listCategoires: MutableList<String>
    lateinit var listView: ListView
    lateinit var list_client: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_tab_options, container, false)
        if(index == 1) {
            root = inflater.inflate(R.layout.admin_create_categories_list_categories, container, false)
            val editText: EditText = root.findViewById(R.id.create_category)
            val save_category = root.findViewById<Button>(R.id.save_category)
                save_category.setOnClickListener {
                    if(editText.text.toString().length < 3) {
                        Toast.makeText(activity!!.applicationContext, resources.getString(R.string.get_me_name_category), Toast.LENGTH_LONG).show()
                    } else {
                        AdminFirebaseCategory().saveOneCategory(editText.text.toString())
                        Toast.makeText(activity!!.applicationContext, resources.getString(R.string.saving), Toast.LENGTH_LONG).show()
                        editText.setText("")
                    }
                }
            listView = root.findViewById(R.id.list_categories)
            AdminFirebaseCategory().getMeListCategories(this)
        }
        if(index == 2) {
            root = inflater.inflate(R.layout.admin_show_list_client, container, false)
            list_client = root.findViewById(R.id.list_client)
            AdminFirestoreProducts().getListUsers(this)
        }

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


    fun getMelistCategoriesReturn(listCategories: MutableList<Category>){
        listView.adapter = CategoryAdapter(activity!!.applicationContext, listCategories)
        listView.setOnItemClickListener { parent, view, position, id ->
         showMeCategoryoptions(activity as IndexAdminActivity, listCategories, position)
        }
    }
    fun showMeCategoryoptions(activity: IndexAdminActivity, listCategories: MutableList<Category>, position: Int){
        activity.showMeCategoryOptions(listCategories, position)
    }



    fun renewListUserTab(listUsers: MutableList<User>){
        list_client.adapter = ListUserAdapter(activity!!.applicationContext, listUsers)
        list_client.setOnItemClickListener { _, _, position, _ ->
            showMeOptionsFromListUser(activity as IndexAdminActivity, listUsers, position)
        }
    }
    fun showMeOptionsFromListUser(activity: IndexAdminActivity, listUsers: MutableList<User>, position: Int){
        activity.showMeOptionsListUsers(listUsers[position].id)
    }


}