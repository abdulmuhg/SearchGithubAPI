package secretymus.id.cermaticodingtest

import android.content.Context
import android.os.Looper
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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_CONTENT = 1
        const val VIEW_LOADING = 0
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int = masterList.size

    override fun getItemViewType(position: Int): Int {
        return if (masterList.lastIndex == position && masterList.lastIndex >= 12) {
            VIEW_LOADING
        } else {
            VIEW_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_LOADING -> {
                LoadingViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_loading, parent, false)
                )
            }
            else -> {
                UserViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
                )
            }
        }
    }

    fun load(list: List<User>) {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            if (masterList.size >= 12) {
                masterList.apply {
                    removeAt(masterList.lastIndex)
                }
            }
            masterList.addAll(list)
            if (masterList.size >= 12) {
                masterList.add(User())
            }
            notifyDataSetChanged()
        }, 2000)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = masterList[position]
        if (holder is UserViewHolder) {
            holder.username.text = item.login
            Glide.with(context)
                .load(item.avatar_url)
                .into(holder.avatar)
        }
    }

}

