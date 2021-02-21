package sirniy.teremok.user
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_profil.*
import kotlinx.android.synthetic.main.activity_user_profil.tv_user_photo_data
import kotlinx.android.synthetic.main.activity_user_profil.user_name_detail
import kotlinx.android.synthetic.main.activity_user_profil_datas.*
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.utils.Constants
import sirniy.teremok.utils.GlideLoader
class UserProfil : AppCompatActivity() {
    var userId: String? = App.sharedPreferences.getString(Constants.USER_ID, null)
    var userImage: String? = App.sharedPreferences.getString(Constants.IMAGE, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profil_datas)

        if(!userId.isNullOrEmpty()){
            company_textview_detail.text = App.sharedPreferences.getString(Constants.COMPANY, "")
            name_detail.text = App.sharedPreferences.getString(Constants.NAME, "")
            tlf_detail.text = App.sharedPreferences.getString(Constants.TLF, "")
        }
        if(!userImage.isNullOrEmpty()){
            GlideLoader(this).loadUserImage(userImage.toString(), tv_user_photo_data)
        }

        edit_user_data.setOnClickListener {
            startActivity(Intent(this, UserProfilEdit::class.java))
            finish()
        }
    }
}