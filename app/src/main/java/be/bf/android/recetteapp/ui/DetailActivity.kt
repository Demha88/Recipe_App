package be.bf.android.recetteapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import be.bf.android.recetteapp.R
import be.bf.android.recetteapp.api.dto.*
import be.bf.android.recetteapp.databinding.ActivityDetailBinding
import be.bf.android.recetteapp.ui.fragments.NavFragments.HomeFragment
import be.bf.android.recetteapp.ui.fragments.NavFragments.SettingViewModel
import be.bf.android.recetteapp.ui.fragments.NavFragments.SettingViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding?=null
    private val binding get() = _binding!!


    private val detailViewModel : SettingViewModel by viewModels() { SettingViewModelFactory(this@DetailActivity) }

    private val dataViewModel : RoomViewModel by viewModels() {RoomViewModelFactory(this)}
    //private val detailDbViewModel : UserViewModel  by viewModels() { UserViewModelFactory(this@DetailActivity)  }

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealPic : String
    //private lateinit var mealCat : String
    private  lateinit var videoLink: String
    private var favMeal: Meal?=null

    //private lateinit var dataViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title="Recipe details"
        //val appContext = C.applicationContext
        val prefs = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val name = prefs.getString("addUserName", "default_value")
        Log.d("UserNameDetailActivityPREF", name.toString())


//        val prefs2 = getSharedPreferences("sharedIntPref", Context.MODE_PRIVATE)
//        val gson = Gson()
//        val json: String = prefs2.getString("addUserID", "{}")!!
//        val obj: User = gson.fromJson(json, User::class.java)
//        Log.d("UserIDNAMEPREF", obj.userId.toString())



//        val prefsInt = getSharedPreferences("sharedIntPref", Context.MODE_PRIVATE)
//        val num = prefs.getInt("addUserId", -1)
//        Log.d("UserIDDETAILACTIVITYPREF", num.toString())


//        val database = DbHelper.getInstance(this)
//        val viewModelFactory = RoomViewModelFactory(database)
//        dataViewModel = ViewModelProvider(this, viewModelFactory)[RoomViewModel::class.java]

        getInfoMeal()
        setInfoInDetailsActivity()

        detailViewModel.getMealById(mealId)

        detailViewModel.currentMealId.observe(this){
                val mealDetail = it
                favMeal = mealDetail
                binding.catName.text= "Category : ${mealDetail.strCategory}"
                binding.areaName.text = "Origin : ${mealDetail.strArea}"

                favMeal = mealDetail

                var ingredient =
                    "${mealDetail.strIngredient1} : ${mealDetail.strMeasure1}\n"+
                    "${mealDetail.strIngredient2} : ${mealDetail.strMeasure2}\n"+
                    "${mealDetail.strIngredient3} : ${mealDetail.strMeasure3}\n"+
                    "${mealDetail.strIngredient4} : ${mealDetail.strMeasure4}\n"+
                    "${mealDetail.strIngredient5} : ${mealDetail.strMeasure5}\n"+
                    "${mealDetail.strIngredient6} : ${mealDetail.strMeasure6}\n"+
                    "${mealDetail.strIngredient7} : ${mealDetail.strMeasure7}\n"


                binding.ingredients.text = ingredient

                val img = binding.imageCalorie
                when (mealDetail.strCategory){
                    "Chicken" -> img.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.chicken
                    ))
                    "Dessert" -> img.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.dessert
                    ))
                    "Seafood" -> img.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.fish
                    ))
                    "Pasta" -> img.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.potatoes
                    ))
                    "Vegan" -> img.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.vegan
                    ))
                    "Vegetarian" -> img.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.vegan
                    ))
                    else -> img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.chicken))
                }


                binding.instructions.text = mealDetail.strInstructions
            if (mealDetail.strYoutube != ""){
                videoLink = mealDetail.strYoutube!!
            } else {
                binding.imgYoutube.visibility = View.GONE
            }



        }

        getToYoutubeVideo()

        saveFavoris()

//        binding.favButton.setOnClickListener {
//            favMeal.let {
//                //detailDbViewModel.insertFavoris(it)
//                detailDbViewModel.insertFavoris(it)
//
//                //val ingredient = Ingredient(0, it.strIngredient1!!, it.strMeasure1!!, it.idMeal)
//                detailDbViewModel.insertIngredient(Ingredient(0, it.strIngredient1!!, it.strMeasure1!!, it.idMeal))
//                //val instruction = Instructions(0,it.strInstructions!!, it.idMeal)
//                detailDbViewModel.insertInstructions(Instructions(0,it.strInstructions!!, it.idMeal))
//            }
//
//
//            Toast.makeText(this, "Favorite meal saved", Toast.LENGTH_SHORT).show()
//
//        }
    }

    private fun saveFavoris() {



        binding.favButton.setOnClickListener {
            favMeal?.let {
                //dataViewModel.insertFavoris(it)

                val meal = Meal (it.dateModified, it.idMeal, 1, it.strArea, it.strCategory, it.strCreativeCommonsConfirmed, it.strDrinkAlternate, it.strImageSource, it.strIngredient1, it.strIngredient10, it.strIngredient11, it.strIngredient12, it.strIngredient13,
                it.strIngredient14, it.strIngredient15, it.strIngredient16, it.strIngredient17, it.strIngredient18, it.strIngredient19, it.strIngredient2, it.strIngredient20,
                it.strIngredient3, it.strIngredient4, it.strIngredient5, it.strIngredient6, it.strIngredient7, it.strIngredient8, it.strIngredient9, it.strInstructions,
                it.strMeal, it.strMealThumb, it.strMeasure1, it.strMeasure10, it.strMeasure11, it.strMeasure12, it.strMeasure13, it.strMeasure14, it.strMeasure15, it.strMeasure16,
                it.strMeasure17, it.strMeasure18, it.strMeasure19, it.strMeasure2, it.strMeasure20, it.strMeasure3, it.strMeasure4, it.strMeasure5, it.strMeasure6, it.strMeasure7,
                it.strMeasure8, it.strMeasure9, it.strSource, it.strTags, it.strYoutube)

                val ingredient1 = Ingredient(0, it.strIngredient1!!, it.strMeasure1!!, it.idMeal)
                val ingredient2 = Ingredient(0, it.strIngredient2!!, it.strMeasure2!!, it.idMeal)
                val ingredient3 = Ingredient(0, it.strIngredient3!!, it.strMeasure3!!, it.idMeal)
                val ingredient4 = Ingredient(0, it.strIngredient4!!, it.strMeasure4!!, it.idMeal)
                val ingredient5 = Ingredient(0, it.strIngredient5!!, it.strMeasure5!!, it.idMeal)
                val ingredient6 = Ingredient(0, it.strIngredient6!!, it.strMeasure6!!, it.idMeal)
                val ingredient7 = Ingredient(0, it.strIngredient7!!, it.strMeasure7!!, it.idMeal)

                dataViewModel.insertIngredient(ingredient1)
                dataViewModel.insertIngredient(ingredient2)
                dataViewModel.insertIngredient(ingredient3)
                dataViewModel.insertIngredient(ingredient4)
                dataViewModel.insertIngredient(ingredient5)
                dataViewModel.insertIngredient(ingredient6)
                dataViewModel.insertIngredient(ingredient7)

                dataViewModel.insertFavoris(meal)


                dataViewModel.insertInstructions(Instructions(0,it.strInstructions!!,it.idMeal))
            }
            Toast.makeText(this, "Favorite meal saved", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getToYoutubeVideo() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
            startActivity(intent)
        }
    }

    private fun setInfoInDetailsActivity() {
        Glide.with(applicationContext)
            .load(mealPic)
            .into(binding.imgDetail)

        binding.collapse.title = mealName
        binding.collapse.setCollapsedTitleTextColor(ContextCompat.getColor(this,
            R.color.light_white
        ))
        binding.collapse.setExpandedTitleColor(ContextCompat.getColor(this, R.color.light_white))
        //binding.catName.text = mealCat
    }

    private fun getInfoMeal() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealPic = intent.getStringExtra(HomeFragment.MEAL_PIC)!!
       // mealCat = intent.getStringExtra(HomeFragment.MEAL_CAT)!!

    }




}