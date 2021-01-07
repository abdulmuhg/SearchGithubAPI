package secretymus.id.cermaticodingtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListAdapter(
    val masterList: List<User>
): RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.textView)
    }

    override fun getItemCount(): Int = masterList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = masterList[position]
        holder.username.text = item.login
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

}

