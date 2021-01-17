package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.FragmentAddGameBinding
import kotlinx.android.synthetic.main.fragment_add_game.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {
    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: FragmentAddGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fab_save_game.setOnClickListener {
            saveNote()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // If Up button is clicked, pop the fragment off the Backstack
        activity?.findViewById<Toolbar>(R.id.toolbar)!!.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun saveNote() {
        val title = et_title.text.toString()
        val platform = et_platform.text.toString()
        val day = et_day.text.toString()
        val month = et_month.text.toString()
        val year = et_year.text.toString()

        if (isAllValid(title, platform, day, month, year)) {
            val cal = Calendar.getInstance()
            cal.set(parseInteger(year), parseInteger(month), parseInteger(day))
            viewModel.saveGame(
                title, platform, cal.time
            )
            findNavController().navigate(R.id.action_addNoteFragment_to_notePadFragment)
        } else {
            Toast.makeText(context, "Please fill in all fields properly", Toast.LENGTH_SHORT).show()
        }
    }

    fun isAllValid(vararg args: String?): Boolean {

        //checking all strings passed and if a single string is not valid returning false.
        args.forEach {
            if (isNotValid(it))
                return false
        }
        return true
    }

    fun isNotValid(string: String?): Boolean {
        return isValid(string).not()
    }

    fun isValid(string: String?): Boolean {
        return string != null && string.isNotEmpty() && string.isNotBlank()
    }

    fun parseInteger(string: String): Int {
        return Integer.parseInt(string)
    }

}