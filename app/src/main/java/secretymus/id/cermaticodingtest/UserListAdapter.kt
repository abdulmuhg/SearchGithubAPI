package secretymus.id.cermaticodingtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserListAdapter(
    val context: Context,
    val masterList: ArrayList<User>
): RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
    }

    override fun getItemCount(): Int = masterList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = masterList[position]
        holder.username.text = item.login
        Glide.with(context)
            .load(item.avatar_url)
            .into(holder.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    fun load(list: List<User>){
        masterList.clear()
        masterList.addAll(list)
        notifyDataSetChanged()
    }

}

