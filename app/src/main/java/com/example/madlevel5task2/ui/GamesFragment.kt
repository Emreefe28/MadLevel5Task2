package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.FragmentGamesBinding
import com.example.madlevel5task2.model.Game
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_games.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GamesFragment : Fragment() {
    private lateinit var binding: FragmentGamesBinding

    private val viewModel: GameViewModel by viewModels()

    private val gamesList = arrayListOf<Game>()
    private val gamesAdapter = GamesAdapter(gamesList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        binding = FragmentGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        observeGameList()

        binding.fabAddGame.setOnClickListener {
            findNavController().navigate(R.id.action_notepadFragment_to_addNoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_delete_all).isVisible = true
        requireActivity().title = getString(R.string.game_list)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                viewModel.deleteAllGames()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun observeGameList() {
        // Make an observer which will get notified by the LiveData observable data holder class state has changed
        viewModel.games.observe(viewLifecycleOwner) { gamesList ->
            this@GamesFragment.gamesList.clear()
            this@GamesFragment.gamesList.addAll(gamesList)
            gamesAdapter.notifyDataSetChanged()
        }
    }

    private fun initializeRecyclerView() {
        val viewManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.rvGames.apply {
            setHasFixedSize(true) // true because the ViewHolder's do not affect the RecyclerView's size. This is done for optimization purposes
            layoutManager = viewManager
            adapter = gamesAdapter
        }

        createItemTouchHelperSwipe().attachToRecyclerView(binding.rvGames)
    }

    private fun createItemTouchHelperSwipe(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = gamesList[position]
                viewModel.deleteGame(gameToDelete)
                Snackbar.make(
                    view!!,
                    getString(R.string.deleted),
                    Snackbar.LENGTH_LONG
                )

                    .show()
            }
        }
        return ItemTouchHelper(callback)
    }
}