package sirniy.teremok.first_youtube.tab_top
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tab_top.*
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.AdminLoginActivity
import sirniy.teremok.admin_activitys.IndexAdminActivity
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.admin_activitys.utiles.Product
import sirniy.teremok.first_youtube.tab_top.ui.main.SectionsPagerAdapter
import sirniy.teremok.user.*
import sirniy.teremok.utils.Constants
class IndexActivity : AppCompatActivity() {
    var dobleBackPressed = false
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_top)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        setSupportActionBar(toolbar_main)
        setTitleAcitivity(resources.getString(R.string.app_name),  resources.getString(R.string.description))
        toolbar_main.elevation = 0F
        toolbar_main.background = ColorDrawable(Color.parseColor("#E86441"))
    }

    fun setTitleAcitivity(x: String, y: String){
        toolbar_main!!.title = resources.getString(R.string.app_name)
        toolbar_main.subtitle = resources.getString(R.string.description)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.botom_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cor -> {
                val userId = App.sharedPreferences.getString(Constants.USER_ID, "")
                if(userId.isNullOrEmpty()) startActivity(Intent(this, UserProfilEdit::class.java))
                else startActivity(Intent(this, ProductActivity::class.java))
                return true
            }
            R.id.web -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://syrny-teremok.web.app")); startActivity(browserIntent)
                return true
            }
            R.id.my_orders -> {
                val userId = App.sharedPreferences.getString(Constants.USER_ID, "")
                if(userId.isNullOrEmpty()) startActivity(Intent(this, UserProfilEdit::class.java))
                else startActivity(Intent(this, UserOrders::class.java))
                return true
            }
            R.id.my_accoutn -> {
                val userId = App.sharedPreferences.getString(Constants.USER_ID, "")
                if(userId.isNullOrEmpty()) startActivity(Intent(this, UserProfilEdit::class.java))
                else startActivity(Intent(this, UserProfil::class.java))
                return true
            }
            R.id.admin -> {
                sharedPreferences = getSharedPreferences(Constants.SHARED_TAG, Context.MODE_PRIVATE)
                val is_admin = sharedPreferences.getString(Constants.IS_ADMIN, "none")
                if(is_admin == Constants.YES) {
                    startActivity(Intent(this, IndexAdminActivity::class.java)); finish()
                } else {
                    startActivity(Intent(this, AdminLoginActivity::class.java)); finish()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}



























