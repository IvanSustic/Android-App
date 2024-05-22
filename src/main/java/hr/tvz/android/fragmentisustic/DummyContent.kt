package hr.tvz.android.fragmentisustic

import hr.tvz.android.fragmentisustic.baze.Pivo
import java.util.ArrayList
import java.util.HashMap

class DummyContent() {

    var ITEMS: MutableList<Pivo> = ArrayList<Pivo>()

    var ITEM_MAP: MutableMap<String, Pivo> = HashMap<String, Pivo>()

    init {
        createDummyContent()
    }

    private fun createDummyContent() {




        for (item in ITEMS) {
            ITEM_MAP.put(item.id.toString(), item)
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    class DummyItem internal constructor(var id: String, var content: String) {
        override fun toString(): String {
            return content
        }
    }
}
