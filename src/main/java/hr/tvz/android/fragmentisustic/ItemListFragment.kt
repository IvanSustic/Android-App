package hr.tvz.android.fragmentisustic

import android.R
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.room.Room
import hr.tvz.android.fragmentisustic.baze.BazaPodataka
import hr.tvz.android.fragmentisustic.baze.Pivo
import hr.tvz.android.fragmentisustic.baze.PivoDAO
import java.util.stream.Collectors
import kotlin.streams.toList

class ItemListFragment : ListFragment() {
    lateinit var pivoDAO: PivoDAO
    lateinit var db : BazaPodataka
    private val STATE_ACTIVATED_POSITION = "activated_position"
    var mActivatedPosition = ListView.INVALID_POSITION
    lateinit var todoList: MutableList<Pivo>
    interface Callbacks {
        fun onItemSelected(id: String?)
    }

    private val sDummyCallbacks: Callbacks =
        object : Callbacks {
            override fun onItemSelected(id: String?) {}
        }

    var mCallbacks: Callbacks = sDummyCallbacks


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

        todoList = pivoDAO.getAll()
        var pivoList: MutableList<String> = arrayListOf()
        todoList.forEach { pivo -> pivoList.add(pivo.naslov) }
        listAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.simple_list_item_activated_1,
            R.id.text1,
            pivoList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore the previously serialized activated item position
        if(savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION))
        }
    }

    private fun setActivatedPosition(position: Int) {
        if (position == ListView.INVALID_POSITION) {
            listView.setItemChecked(mActivatedPosition, false)
        } else {
            listView.setItemChecked(position, true)
        }

        mActivatedPosition = position
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Activities containing this fragment must implement its callbacks
        check(context is Callbacks) { "Activity must implement fragment's callbacks." }

        mCallbacks = context
    }

    override fun onDetach() {
        super.onDetach()
        mCallbacks = sDummyCallbacks
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        mCallbacks.onItemSelected(todoList.get(position).id.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition)
        }
    }

    fun setActivateOnItemClick(activateOnItemClick: Boolean) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        listView.choiceMode =
            if (activateOnItemClick) ListView.CHOICE_MODE_SINGLE else ListView.CHOICE_MODE_NONE
    }
}