package com.example.madlevel5task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game
import java.text.DateFormat

class GamesAdapter(private val gamesList: List<Game>) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            val date = DateFormat.getDateInstance().format(game.date.time)
            itemView.findViewById<TextView>(R.id.TV_title).text = game.title
            itemView.findViewById<TextView>(R.id.TV_platform).text = game.platform
            itemView.findViewById<TextView>(R.id.TV_date).text =
                itemView.context.resources.getString(R.string.last_updated, date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(gamesList[position])

    override fun getItemCount(): Int = gamesList.size

}