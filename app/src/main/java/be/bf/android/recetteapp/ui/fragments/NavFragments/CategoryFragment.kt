package be.bf.android.recetteapp.ui.fragments.NavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.recetteapp.ui.CategoryDetailActivity
import be.bf.android.recetteapp.databinding.FragmentCategoryBinding
import be.bf.android.recetteapp.recyclerview.CategoryAdapter


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding?=null
    private val binding get() = _binding!!



    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryViewModel: SettingViewModel by activityViewModels() {SettingViewModelFactory(requireContext())}

    companion object{
        const val CATEG_NAME = "CATEG_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryAdapter = CategoryAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_category, container, false)

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryRecyclerView()

//        val appContext = requireContext().applicationContext
//        val prefs = appContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//        val name = prefs.getString("addUserName", "default_value")
//        Log.d("UserIDCATEGPREF", name.toString())

        categoryViewModel.getCategories()
        categoryViewModel.getAllCategory.observe(viewLifecycleOwner){
            categoryAdapter.updateCategoryAdapter(it)
        }

        onCategoryDetailClickListener()
       // onCategoryClickListener()
//        categoryViewModel.getAllCategory.observe(viewLifecycleOwner){
//            it.forEach { category ->
//                Log.d("TestCategory", category.strCategory)
//            }
//        }



    }

    private fun onCategoryDetailClickListener() {
        categoryAdapter.onItemClick = {
            val intent = Intent(activity, CategoryDetailActivity::class.java)
            intent.putExtra(CATEG_NAME, it.strCategory)
            startActivity(intent)
        }
    }


    private fun categoryRecyclerView() {
       //categoryAdapter = CategoryAdapter()
        binding.categoryRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter=categoryAdapter
        }
    }



}