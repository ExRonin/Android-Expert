package exronin.mycinemov.core.ui

import androidx.recyclerview.widget.DiffUtil
import exronin.mycinemov.core.domain.model.Mov

class MovDiffCallback(
    private val oldList: List<Mov>,
    private val newList: List<Mov>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

