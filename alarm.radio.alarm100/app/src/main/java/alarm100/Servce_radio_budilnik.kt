package alarm100
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import radio.alarm.Index
import radio.alarm.Off_sound
import radio.alarm.R
import java.util.*

class Servce_radio_budilnik : Service() {
    lateinit var sharedPreferences: SharedPreferences
    var dataTimeString = ""
    var title          = ""
    var name_radio     = ""
    var offSound       = ""
    private val binder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("radio", Context.MODE_PRIVATE)
        dataTimeString    = sharedPreferences.getString("dataTimeString", "Error").toString()
        val use           = resources.getString(R.string.installlition)
        name_radio        = sharedPreferences.getString("name_radio", "none").toString()
        title             = "$use $dataTimeString"
        offSound          = getString(R.string.offSound)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, Off_sound::class.java)
        val goToIndexPrepare   = Intent(this, Index::class.java)
        val pendingIntent      = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val goToIndex          = PendingIntent.getActivity(this, 0, goToIndexPrepare, 0)
        val notification       = NotificationCompat.Builder(this, App.channel_id)
                                 .setContentTitle(title)
                                 .setContentText(name_radio)
                                 .setSmallIcon(R.drawable.alarm_icon)
                                 .addAction(0, offSound, pendingIntent)
                                 .setContentIntent(goToIndex)
                                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                                 .build()
        startForeground(123, notification)
        return START_NOT_STICKY
    }



    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    inner  class LocalBinder : Binder() {
        fun getService(): Servce_radio_budilnik = this@Servce_radio_budilnik
    }

    // Random number generator
    private val mGenerator = Random()

    /** method for clients  */
    val randomNumber: Int
        get() = mGenerator.nextInt(100)

}