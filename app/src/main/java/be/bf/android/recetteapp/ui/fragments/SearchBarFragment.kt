package be.bf.android.recetteapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.recetteapp.ui.DetailActivity
import be.bf.android.recetteapp.databinding.FragmentSearchBarBinding
import be.bf.android.recetteapp.recyclerview.SearchAdapter
import be.bf.android.recetteapp.ui.fragments.NavFragments.HomeFragment
import be.bf.android.recetteapp.ui.fragments.NavFragments.SettingViewModel
import be.bf.android.recetteapp.ui.fragments.NavFragments.SettingViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchBarFragment : Fragment() {

    private var _binding: FragmentSearchBarBinding?=null
    private val binding get() = _binding!!

    private val searchViewModel : SettingViewModel by activityViewModels() { SettingViewModelFactory(requireContext()) }
    private lateinit var searchAdapter : SearchAdapter

    companion object{
        const val MEAL_ID = "MEAL_ID"
        const val MEAL_NAME = "MEAL_NAME"
        const val MEAL_PIC = "MEAL_PIC"
        const val MEAL_CAT = "MEAL_CAT"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // searchAdapter = SearchAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search_bar, container, false)
        _binding = FragmentSearchBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Search recipes"

        searchRecyclerview()

        binding.searchImgForward.setOnClickListener {
            val searchName = binding.searchText.text.toString()
            if (searchName.isNotEmpty()){
                searchViewModel.searchMeals(searchName)
            }
        }

        searchViewModel.getSearchMeal.observe(viewLifecycleOwner) {
            searchAdapter.differDiffUtil.submitList(it)
        }

        var jobSearch: Job? = null
        binding.searchText.addTextChangedListener {
            jobSearch?.cancel()
            jobSearch = lifecycleScope.launch {
                delay(400)
                searchViewModel.searchMeals(it.toString())
            }
        }

        onClickSearch()


    }

    private fun onClickSearch() {
        searchAdapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, it.strMeal)
            intent.putExtra(HomeFragment.MEAL_PIC, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun searchRecyclerview() {
        searchAdapter = SearchAdapter()
        binding.searchRecycler.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }


}