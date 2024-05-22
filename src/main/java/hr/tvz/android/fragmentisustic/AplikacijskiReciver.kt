package hr.tvz.android.fragmentisustic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AplikacijskiReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context,
            "Volim pivo",
            Toast.LENGTH_LONG
        ).show()
    }
}