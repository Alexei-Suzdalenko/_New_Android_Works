package sirniy.teremok.admin_activitys
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.ui.main.SectionsPagerAdapter
import sirniy.teremok.admin_activitys.utiles.AdminFirebaseCategory
import sirniy.teremok.admin_activitys.utiles.AdminFirestoreProducts
import sirniy.teremok.admin_activitys.utiles.Category
import sirniy.teremok.utils.Constants
import sirniy.teremok.utils.FirebaseDatabase

class IndexAdminActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_options)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

   fun showMeCategoryOptions(listCategories: MutableList<Category>, position: Int){
       val arr = arrayOf("Добавить продукт", "Посмотреть список товапров для этой категории", "", "Удалить категорию")
       val builder: AlertDialog.Builder = AlertDialog.Builder(this)
       builder.setTitle("Выберите действие или отмените")
       builder.setItems(arr) { _, which ->
           when(which.toString()){
               "0" -> {
                 val intent = Intent(this, AdminCreateProduct::class.java)
                   intent.putExtra(Constants.CATEGORY_ID, listCategories[position].id )
                   intent.putExtra(Constants.CATEGORY_NAME , listCategories[position].name)
                   intent.putExtra(Constants.POSITION , position)
                 startActivity(intent)
               }
               "1" -> {
                   val intent = Intent(this, AdminListProducts::class.java)
                   intent.putExtra(Constants.CATEGORY_NAME , listCategories[position].name)
                   startActivity(intent)
               }
               "3" -> {
                   showMeOptionsToDeleteCategory(listCategories[position].id)
               }
           }
       }
       builder.show()
   }

   fun showMeOptionsToDeleteCategory(x: String){
       val builder: AlertDialog.Builder = AlertDialog.Builder(this)
       builder.setTitle("Удалить категорию?")
       builder.setPositiveButton(resources.getString(R.string.yes)){ dialog, which ->
           Toast.makeText(this, resources.getString(R.string.start_deleting), Toast.LENGTH_LONG).show()
           AdminFirebaseCategory().deleteCategory(x, this)
       }
       builder.setNegativeButton(resources.getString(R.string.no)){ dialog, which -> }
       builder.show()
   }

    fun showMeOptionsListUsers(id_user: String){
        val arr = arrayOf("Удалить данные пользователя?", "", "Отменить")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите действие или отмените")
        builder.setItems(arr) { _, which ->
            when(which.toString()){
                "0" -> {
                    showMeDeleteUserData(id_user)
                }
            }
        }
        builder.show()
    }

    fun showMeDeleteUserData(idUser: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Удалить данные пользователя?")
        builder.setPositiveButton(resources.getString(R.string.yes)){ dialog, which ->
            Toast.makeText(this, resources.getString(R.string.start_deleting_user), Toast.LENGTH_LONG).show()
            AdminFirestoreProducts().deleteUserData(idUser, this)
        }
        builder.setNegativeButton(resources.getString(R.string.no)){ dialog, which -> }
        builder.show()
    }

    fun refreshPageWhenUserDeleted(){
        val i = intent; finish(); startActivity(i)
    }



    fun gotoAdminCreateCategories(){

    }


}