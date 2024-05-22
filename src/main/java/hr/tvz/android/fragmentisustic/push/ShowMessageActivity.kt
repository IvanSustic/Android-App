package hr.tvz.android.fragmentisustic.push

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.RemoteMessage
import hr.tvz.android.fragmentisustic.R


class ShowMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_message)

        var remoteMessage: RemoteMessage?

        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            remoteMessage = intent.extras!!.getParcelable("poruka", RemoteMessage::class.java)
        } else {
            remoteMessage = intent.extras!!.getParcelable("poruka")
        }

        (findViewById<View>(R.id.tvOd) as TextView).text = remoteMessage!!.from
        (findViewById<View>(R.id.tvPoruka) as TextView).text = remoteMessage!!.notification!!.body
    }
}