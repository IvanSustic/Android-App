package hr.tvz.android.fragmentisustic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import hr.tvz.android.fragmentisustic.baze.BazaPodataka
import hr.tvz.android.fragmentisustic.baze.Pivo
import hr.tvz.android.fragmentisustic.baze.PivoDAO


class MainActivity : AppCompatActivity(), ItemListFragment.Callbacks {
    var MOJ_KANAL = "mojKanal"
    var mTwoPane = false
    lateinit var pivoDAO: PivoDAO
    lateinit var db : BazaPodataka
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        db = Room.databaseBuilder(
            applicationContext,
            BazaPodataka::class.java, "database-name"
        )
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .build()
       // pivoDAO = db.pivoDao();
        //pivoDAO.insertOne(Pivo(1,"Kozel","Svijetli kozel","kozel.jpg",
         //   "https://www.velkopopovickykozel.com/#our-beers/premium-lager",
          // R.style.Base_Theme_LIGHT)
       // )
        //pivoDAO.insertOne(Pivo(2,"Crni kozel","Svijetli kozel","kozelcrn.jpg",
         //   "https://www.velkopopovickykozel.com/#our-beers/dark",
          //  R.style.Base_Theme_DARK)
        //)
        //pivoDAO.insertOne(Pivo(3,"Polutamni kozel","Svijetli kozel","kozelpol.jpg",
          //  "https://www.velkopopovickykozel.com/#our-beers/semi-dark",
           // R.style.Base_Theme_MIX)
       // )
        registerReceiver(AplikacijskiReciver(), IntentFilter("hr.android.broadcast.testbc"), RECEIVER_EXPORTED)

        if(findViewById<FrameLayout>(R.id.item_detail_container) != null) {
            mTwoPane = true
        } else {
            mTwoPane = false
            var itemListFragment = ItemListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.item_list_container, itemListFragment)
                .commit()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MOJ_KANAL,
                "Ime kanala",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Opis kanala"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Main activity", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("Main activity token:", token)
            Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()
        })


    }

    override fun onItemSelected(id: String?) {

        var arguments = Bundle()
        arguments.putString(ItemDetailFragment().ARG_ITEM_ID, id)
        arguments.putBoolean(ItemDetailFragment().TWO_PANE, mTwoPane)
        var detailFragment = ItemDetailFragment()
        detailFragment.arguments = arguments
        var fragmentTransaction = supportFragmentManager.beginTransaction()

        if (mTwoPane) {
            fragmentTransaction
                .replace(R.id.item_detail_container, detailFragment)
                .commit()
        } else {
            fragmentTransaction
                .addToBackStack(null)
                .replace(R.id.item_list_container, detailFragment)
                .commit()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_menu5, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_share -> {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("Želiš li poslati broadcast").setPositiveButton("Da"){
                        dialog, which ->
                    val uniqueActionString = "hr.android.broadcast.testbc"
                    val broadcastIntent = Intent()
                    broadcastIntent.action = uniqueActionString
                    sendBroadcast(broadcastIntent)
                }.setNegativeButton("Ne"){
                        dialog,which->
                }.create().show()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}