package secretymus.id.cermaticodingtest.search

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import secretymus.id.cermaticodingtest.R
import secretymus.id.cermaticodingtest.model.User
import secretymus.id.cermaticodingtest.network.ApiInterface

class SearchListAdapter(
    private val context: Context,
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
        return if (masterList.lastIndex == position && masterList.lastIndex >= ApiInterface.PAGE_SIZE) {
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
        if (list.isNotEmpty()) {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                if (masterList.size >= ApiInterface.PAGE_SIZE) {
                    masterList.apply {
                        removeAt(masterList.lastIndex)
                    }
                }
                masterList.addAll(list)
                if (masterList.size >= ApiInterface.PAGE_SIZE) {
                    masterList.add(User())
                }
                notifyDataSetChanged()
            }, 2000)
        } else {
            Toast.makeText(context, context.getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = masterList[position]
        if (holder is UserViewHolder) {
            holder.apply {
                username.text = item.login
                Glide.with(context)
                    .load(item.avatar_url)
                    .into(avatar)
            }
        }
    }

}

