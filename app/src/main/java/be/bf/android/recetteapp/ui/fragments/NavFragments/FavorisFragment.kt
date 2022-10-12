package be.bf.android.recetteapp.ui.fragments.NavFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.ui.DetailActivity
import be.bf.android.recetteapp.R
import be.bf.android.recetteapp.api.dto.*
import be.bf.android.recetteapp.databinding.FragmentFavorisBinding
import be.bf.android.recetteapp.recyclerview.FavAdapter
import com.google.android.material.snackbar.Snackbar


class FavorisFragment : Fragment() {

    private var _binding: FragmentFavorisBinding?=null
    private val binding get() = _binding!!

    private val favViewModel : RoomViewModel by activityViewModels { RoomViewModelFactory(requireContext()) }

    private lateinit var favAdapter : FavAdapter

    companion object{
//        const val INGRED_NAME = "INGRED_NAME"
//        const val INGRED_QTITY = "INGRED_QTITY"


            const val MEAL_ID = "MEAL_ID"
            const val MEAL_NAME = "MEAL_NAME"
            const val MEAL_PIC = "MEAL_PIC"
            const val MEAL_CAT = "MEAL_CAT"


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favAdapter = FavAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_favoris, container, false)
        _binding = FragmentFavorisBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favRecyclerView()

        //onFavClickListener()

//        favAdapter.onFavMealClick(object : FavAdapter.OnFavClick{
//            override fun onItemClick(position: Int) {
//                    val meal = favAdapter.getFavPosition(position)
//                //val bundle = bundleOf("pos" to favAdapter.differDiffUtil.currentList[position-1].idMeal.toInt())
//                findNavController().navigate(R.id.action_favorisFragment_to_myIngredientsFragment)
//            }
//           })

        favAdapter.onFavMealClick(object : FavAdapter.OnFavClick{
            override fun onClick(meal: Meal) {

                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_NAME, meal.strMeal)
                intent.putExtra(MEAL_PIC, meal.strMealThumb)
                startActivity(intent)

               // findNavController().navigate(R.id.action_favorisFragment_to_myIngredientsFragment)
                //Toast.makeText(requireContext(), meal.idMeal, Toast.LENGTH_SHORT).show()
            }
        })


        favAdapter.onFavLongClick(object : FavAdapter.OnFavLongClick{
            override fun onLongCLick(meal: Meal) {
                val bundle = bundleOf("idMeal" to meal.idMeal)
                findNavController().navigate(R.id.action_favorisFragment_to_myIngredientsFragment, bundle)
                //Toast.makeText(requireContext(), meal.strMeal, Toast.LENGTH_SHORT).show()
//                val intent = Intent(activity, DetailActivity::class.java)
//                intent.putExtra(MEAL_ID, meal.idMeal)
//                intent.putExtra(MEAL_NAME, meal.strMeal)
//                intent.putExtra(MEAL_PIC, meal.strMealThumb)
//                startActivity(intent)
            }
        })

//        favAdapter.onItemLongClick={
//            Toast.makeText( requireContext(),"Long click", Toast.LENGTH_SHORT).show()
////            val intent = Intent(activity, DetailActivity::class.java)
////            intent.putExtra(HomeFragment.MEAL_ID, it[position].idMeal)
////            intent.putExtra(HomeFragment.MEAL_NAME, it.strMeal)
////            intent.putExtra(HomeFragment.MEAL_PIC, it.strMealThumb)
////            startActivity(intent)
//        }


        favViewModel.readAllMeal.observe(viewLifecycleOwner) {
            //favAdapter.differDiffUtil.submitList(it)
            favAdapter.updateFavMealAdapter(it)
//                items ->
//                items.forEach {
//                Log.d("List Item", it.idMeal)
//            }
        }


        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean { return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favAdapter.getFavPosition(position)
                favViewModel.deleteFavoris(meal)
                favViewModel.clearInstruction(meal.idMeal)
                favViewModel.clearIngredient(meal.idMeal)




                Snackbar.make(requireView(), "Favourite Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        favViewModel.insertFavoris(meal)
                        //favViewModel.insertInstructions(Instructions(0,meal.strInstructions!!,meal.idMeal )
                    }
                ).show()
            }
        }
        ItemTouchHelper(swipeToDelete).attachToRecyclerView(binding.favRecycler)


    }



//    private fun onFavClickListener() {
//        favAdapter.onItemClick = {
//            Log.d("FavFrag", it.toString())
//            val intent = Intent(activity, FavIngredientActivity::class.java)
//            intent.putExtra(INGRED_NAME, it[it.size-1].name)
//            intent.putExtra(INGRED_QTITY, it[it.size-1].qtity)
//            Log.d("IntentFavFrag",(intent.putExtra(INGRED_NAME, it[it.size-1].name)).getStringExtra(
//                INGRED_NAME).toString() )
//            startActivity(intent)
//        }
//    }

    private fun favRecyclerView() {
        favAdapter = FavAdapter()
        binding.favRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favAdapter
        }
    }

}