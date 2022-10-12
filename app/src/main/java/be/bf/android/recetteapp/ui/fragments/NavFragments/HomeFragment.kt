package be.bf.android.recetteapp.ui.fragments.NavFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.recetteapp.ui.DetailActivity
import be.bf.android.recetteapp.R
import be.bf.android.recetteapp.databinding.FragmentHomeBinding
import be.bf.android.recetteapp.recyclerview.AllMealAdapter
import be.bf.android.recetteapp.recyclerview.PopularMealAdpater
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val homeViewModel : SettingViewModel by activityViewModels() {SettingViewModelFactory(requireContext())}

    //private lateinit var randomeMeal:Meal
    //private lateinit var categMeal : List<MealByCategory>

    private lateinit var popularAdapter: PopularMealAdpater
    private lateinit var allMealAdapter: AllMealAdapter

    private lateinit var imageSlider : ImageSlider


    companion object{
            const val MEAL_ID = "MEAL_ID"
            const val MEAL_NAME = "MEAL_NAME"
            const val MEAL_PIC = "MEAL_PIC"
            const val MEAL_CAT = "MEAL_CAT"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        popularAdapter = PopularMealAdpater()
        allMealAdapter = AllMealAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularMealRecyclerView()
        allMealRecyclerView()
       // homeViewModel.randomMeal()
        homeViewModel.randomMeals()
        homeViewModel.getMealsOfTheDay()

        val appContext = requireContext().applicationContext
        val prefs = appContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val name = prefs.getString("addUserName", "default_value")
        val idUser = prefs.getInt("addUserID", -1)
        Log.d("UserNAMEPREF", name.toString())
        Log.d("UserIDPREF", idUser.toString())

//        val appContext2 = requireContext().applicationContext
//        val prefs2 = appContext2.getSharedPreferences("sharedIntPref", Context.MODE_PRIVATE)
//        val gson = Gson()
//        val json: String = prefs2.getString("addUserID", "{}")!!
//        val obj = gson.fromJson(json, User::class.java)
//        Log.d("UserIDNAMEPREF", obj.userId.toString())

        homeViewModel.getMealsOfTheDay.observe(viewLifecycleOwner){
            imageSlider = view.findViewById(R.id.imageSlider)!!
           val imageList = ArrayList<SlideModel>()

            for (i in 0..2){
                imageList.add(SlideModel(it[i].strMealThumb, it[i].strMeal))
            }
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            //imageSlider.stopSliding()

            binding.imageSlider.setItemClickListener(object : ItemClickListener{
                override fun onItemSelected(position: Int) {

                    val intent =  Intent(activity, DetailActivity::class.java)
                    intent.putExtra(MEAL_ID, it[position].idMeal)
                    intent.putExtra(MEAL_NAME, it[position].strMeal)
                    intent.putExtra(MEAL_PIC, it[position].strMealThumb)
                    startActivity(intent)
                    //Toast.makeText(requireContext(), "Click on the Slider image ${it[position].strMeal}", Toast.LENGTH_LONG).show()
                }
            })
        }



//        homeViewModel.currentRandomMeal.observe(viewLifecycleOwner) {
//
//           // val imageSlider = (ImageSlider) findViewById<ImageSlider>(R.id.imageSlider)
//            imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)!!
//            val imageList = ArrayList<SlideModel>()
//
//            //val imageslider : List<String>
//
//            //imageslider
//
//            imageList.add(SlideModel(it.strMealThumb, it.strMeal))
//            imageList.add(SlideModel(it.strMealThumb, it.strMeal))
//            imageList.add(SlideModel(it.strMealThumb, it.strMeal))
//            //imageList.add(SlideModel("https://img.cinemablend.com/filter:scale/quill/3/7/0/0/8/e/37008e36e98cd75101cf1347396eac8534871a19.jpg?mw=600","Jumanji"))
//            //imageList.add(SlideModel("https://www.adgully.com/img/800/201711/spider-man-homecoming-banner.jpg","Spider Man"))
//            //imageList.add(SlideModel("https://live.staticflickr.com/1980/29996141587_7886795726_b.jpg","Venom"))
//
//            imageSlider.setImageList(imageList, ScaleTypes.FIT)
////            Glide.with(requireContext())
////                .load(it!!.strMealThumb)
////                .into(binding.randomMeal)
////            binding.dishName.text = it.strMeal
//
//            randomeMeal = it
//
//            goToDetailMeal()
//
//        }





        homeViewModel.getPopularMeal()
        homeViewModel.getAllPopularMeal.observe(viewLifecycleOwner){
            popularAdapter.updateCategory(it)
        }

        homeViewModel.randomMeals()
        homeViewModel.getAllRandomMeal.observe(viewLifecycleOwner){
            allMealAdapter.updateMealAdapter(it)
        }


        onClickPopularMeal()
        onClickAllMeal()

        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchBarFragment)
        }



//        val api = RetrofitClient.client.create(MealDBApi::class.java)
//        api.randomMeal().enqueue(object : Callback<MealList>{
//            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        val randomMeal: Meal = response.body()!!.meals[0]
//                        Log.d("retroRep", "meal id ${randomMeal.idMeal}")
//                        Glide.with(this@HomeFragment)
//                            .load(randomMeal.strMealThumb)
//                            .into(binding.randomMeal)
//                    }
//
//                }else {
//                        return
//                }
//            }
//
//            override fun onFailure(call: Call<MealList>, t: Throwable) {
//                Log.d("HomeFragment", t.message.toString())
//            }
//
//        })
    }



    private fun onClickAllMeal() {
        allMealAdapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_PIC, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onClickPopularMeal() {
       popularAdapter.onItemClick = {
           val intent = Intent(activity, DetailActivity::class.java)
           intent.putExtra(MEAL_ID, it.idMeal)
           intent.putExtra(MEAL_NAME, it.strMeal)
           intent.putExtra(MEAL_PIC, it.strMealThumb)
           startActivity(intent)

       }
    }


    private fun popularMealRecyclerView() {
        binding.popularRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter

        }
    }

    private fun allMealRecyclerView() {
        binding.allmealRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = allMealAdapter
        }
    }

//    private fun goToDetailMeal(){
//        binding.imageSlider.setItemClickListener(object : ItemClickListener{
//            override fun onItemSelected(position: Int) {
//                val intent =   Intent(activity, DetailActivity::class.java)
//                intent.putExtra(MEAL_ID, randomeMeal.idMeal)
//                intent.putExtra(MEAL_NAME, randomeMeal.strMeal)
//                intent.putExtra(MEAL_PIC, randomeMeal.strMealThumb)
//                intent.putExtra(MEAL_CAT, randomeMeal.strCategory)
//                startActivity(intent)
//                //Toast.makeText(requireContext(), "Click on the Slider image ${it.strMeal}", Toast.LENGTH_LONG).show()
//            }
//        })
//    }





}