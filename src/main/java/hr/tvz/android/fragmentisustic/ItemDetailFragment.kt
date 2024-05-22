package hr.tvz.android.fragmentisustic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import hr.tvz.android.fragmentisustic.baze.BazaPodataka
import hr.tvz.android.fragmentisustic.baze.Pivo
import hr.tvz.android.fragmentisustic.baze.PivoDAO


class ItemDetailFragment: Fragment() {
    lateinit var pivoDAO: PivoDAO
    lateinit var db : BazaPodataka
    val ARG_ITEM_ID = "item_id"
    val TWO_PANE = "bool"
    var mItem: Pivo? = null
    var bool:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = context?.let {
            Room.databaseBuilder(
                it,
                BazaPodataka::class.java, "database-name"
            )
                .allowMainThreadQueries()
                .build()
        }!!

        pivoDAO = db.pivoDao()
        if(arguments?.containsKey(ARG_ITEM_ID) == true) {
           mItem= requireArguments().getString(ARG_ITEM_ID)?.let { pivoDAO.getById(it.toInt()) }

        }
        if(arguments?.containsKey(TWO_PANE) == true) {
            bool = requireArguments().getBoolean(TWO_PANE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.activity_detalji, container, false)
        if (mItem != null) {
            rootView.context.setTheme(mItem!!.tema)

            (rootView.findViewById<View>(R.id.naslov) as TextView).text = mItem!!.naslov
            (rootView.findViewById<View>(R.id.detalji) as TextView).text = mItem!!.detalji

            val slika : ImageView = rootView.findViewById(R.id.slika) as ImageView
            if (mItem!!.slika=="kozel.jpg"){
                slika.setImageResource(R.drawable.kozel)
            }
            if (mItem!!.slika=="kozelcrn.jpg"){
                slika.setImageResource(R.drawable.kozelcrn)
            }
            if (mItem!!.slika=="kozelpol.jpg"){
                slika.setImageResource(R.drawable.kozelpol)
            }
            slika.setOnClickListener { v ->
                val nextFrag = Slika()
                if (bool){
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.item_detail_container, nextFrag, "findThisFragment")
                        ?.addToBackStack(null)
                        ?.commit()
                } else{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.item_list_container, nextFrag, "findThisFragment")
                        ?.addToBackStack(null)
                        ?.commit()
                }



            }

            val link: Button = rootView.findViewById(R.id.link) as Button
            link.setOnClickListener({


                val url = mItem!!.link
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            })
        }

        return rootView
    }
}