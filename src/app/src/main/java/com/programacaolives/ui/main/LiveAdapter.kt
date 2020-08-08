package com.programacaolives.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.programacaolives.R
import com.programacaolives.domain.entity.Live
import com.programacaolives.extension.load
import com.programacaolives.log.Log
import com.programacaolives.utils.date.DateUtils
import kotlinx.android.synthetic.main.item_live.view.*
import java.text.SimpleDateFormat
import java.util.*

class LiveAdapter(
    private val lives: List<Live>
) :
    RecyclerView.Adapter<LiveAdapter.ViewHolder>() {

    private val simpleDateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_live, parent, false)
        view.card_live.item_container.setOnClickListener(OnItemClick())
        return ViewHolder(view)
    }

    class OnItemClick : View.OnClickListener {
        override fun onClick(itemView: View?) {
            (itemView as ViewGroup).run {
                val link = tag as String
                if (link.isNotBlank()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    val activity = (context as MainActivity)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        activity.startActivity(intent)
                    } else {
                        val message = context.getString(R.string.link_error_message)
                        Log.log(message)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = (holder.view as ViewGroup).context

        val live = lives[position]
        val cardView = holder.view.card_live

        cardView.item_container.tag = live.link

        cardView.txt_live_name.text = live.name
        cardView.txt_live_date.text = extractFormattedDate(context, live.date)

        if (live.noSetTime) {
            cardView.txt_live_hour.text = context.getString(R.string.time_to_be_defined)
        } else {
            cardView.txt_live_hour.text = DateUtils.extractFormattedHour(live.date)
        }

        cardView.image_artist.load(live.imageName)
    }

    private fun extractFormattedDate(context: Context, date: Date) =
        DateUtils.extractFormattedDate(context, simpleDateFormat, date)

    override fun getItemCount() = lives.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}