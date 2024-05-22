package hr.tvz.android.fragmentisustic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import hr.tvz.android.fragmentisustic.baze.BazaPodataka

class Slika : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.activity_slika, container, false)
        val slika : ImageView = rootView.findViewById(R.id.slika1)
        slika.setImageResource(R.drawable.kozel)
        slika.setOnClickListener({
            val animation = AnimationUtils.loadAnimation(rootView.context, R.anim.rotate);
            slika.startAnimation(animation);
        })


        return rootView
    }

}