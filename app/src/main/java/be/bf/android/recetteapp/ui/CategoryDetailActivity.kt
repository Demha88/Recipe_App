package be.bf.android.recetteapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.recetteapp.databinding.ActivityCategoryDetailBinding
import be.bf.android.recetteapp.recyclerview.DetailCategoryAdapter
import be.bf.android.recetteapp.ui.fragments.NavFragments.CategoryFragment
import be.bf.android.recetteapp.ui.fragments.NavFragments.HomeFragment
import be.bf.android.recetteapp.ui.fragments.NavFragments.SettingViewModel
import be.bf.android.recetteapp.ui.fragments.NavFragments.SettingViewModelFactory

class CategoryDetailActivity : AppCompatActivity() {

    companion object{
        const val MEAL_ID = "MEAL_ID"
        const val MEAL_NAME = "MEAL_NAME"
        const val MEAL_PIC = "MEAL_PIC"
        const val MEAL_CAT = "MEAL_CAT"

    }


    private var _binding: ActivityCategoryDetailBinding?=null
    private val binding get() = _binding!!

    private lateinit var detailCategoryAdapter: DetailCategoryAdapter

    private val categoryDetailViewModel : SettingViewModel by viewModels() { SettingViewModelFactory(this@CategoryDetailActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_category_detail)
        _binding = ActivityCategoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailCategoryRecyclerView()

        goToDetailMeal()

        val actionBar = supportActionBar
        actionBar!!.title="Recipes by category"

        val name = intent.getStringExtra(CategoryFragment.CATEG_NAME)

         categoryDetailViewModel.getMealByCategory(intent.getStringExtra(CategoryFragment.CATEG_NAME)!!)
         categoryDetailViewModel.getMealByCategory.observe(this){
             binding.titreCateg.text = name
             binding.categoryNum.text = "(${it.size})"
             detailCategoryAdapter.updateDetailCategoryAdapter(it)
//            it.forEach { catg ->
//                Log.d("test", catg.strMeal)
//            }
        }

    }

    private fun goToDetailMeal() {
        detailCategoryAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, it.strMeal)
            intent.putExtra(HomeFragment.MEAL_PIC, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun detailCategoryRecyclerView() {
        detailCategoryAdapter = DetailCategoryAdapter()
        binding.detailCategoryRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter=detailCategoryAdapter
        }
    }
}