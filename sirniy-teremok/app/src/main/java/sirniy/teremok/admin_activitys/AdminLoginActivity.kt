package sirniy.teremok.admin_activitys
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_admin_login.*
import sirniy.teremok.R
import sirniy.teremok.utils.Constants
class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val sharedPreferences: SharedPreferences = getSharedPreferences(Constants.SHARED_TAG, Context.MODE_PRIVATE)
        val oldEmail = sharedPreferences.getString(Constants.EMAILADMIN, Constants.NONE)
        val oldPassword = sharedPreferences.getString(Constants.PASSWORDADMIN, Constants.NONE)
        if(oldEmail != Constants.NONE && oldPassword != Constants.NONE){
            editTextTextEmailAddress.setText(oldEmail)
            editTextTextPersonName.setText(oldPassword)
        }

        enter_admin.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString()
            val password = editTextTextPersonName.text.toString()

            if(email == "leto@gmail.com" && password == "leto123"){

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(Constants.EMAILADMIN, email)
                    editor.putString(Constants.PASSWORDADMIN, password)
                    editor.putString(Constants.IS_ADMIN, Constants.YES)
                    editor.apply()

                startActivity(Intent(this, IndexAdminActivity::class.java))
                finish()
            } else {
                editTextTextEmailAddress.setText("")
                editTextTextPersonName.setText("")
                Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG ).show()
            }
        }
    }
}