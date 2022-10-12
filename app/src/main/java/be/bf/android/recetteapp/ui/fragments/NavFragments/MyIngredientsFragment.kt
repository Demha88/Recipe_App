package be.bf.android.recetteapp.ui.fragments.NavFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.recetteapp.R
import be.bf.android.recetteapp.api.dto.RoomViewModel
import be.bf.android.recetteapp.api.dto.RoomViewModelFactory
import be.bf.android.recetteapp.databinding.FragmentMyIngredientsBinding
import be.bf.android.recetteapp.recyclerview.IngredientAdapter


class MyIngredientsFragment : Fragment() {

    private var _binding: FragmentMyIngredientsBinding?=null
    private val binding get() =_binding!!


    private val myIngViewModel : RoomViewModel by activityViewModels { RoomViewModelFactory(requireContext()) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_ingredients, container, false)
        _binding = FragmentMyIngredientsBinding.inflate(inflater, container, false)

        val idMeal = arguments?.getString("idMeal")

        val adapater = IngredientAdapter()
        val recyclerView = binding.ingredientRecycler
        recyclerView.adapter=adapater
        recyclerView.layoutManager=LinearLayoutManager(requireContext())



            myIngViewModel.readIngredient.observe(viewLifecycleOwner) {
                Log.d("ingredients", it.toString())
                adapater.updateIngredient(it)
            }




        if (idMeal != null) {
            myIngViewModel.readIngredientById(idMeal)
        }

//        myIngViewModel.readMealWithIngredient.observe(viewLifecycleOwner){
//            adapater.updateIngredient(it)
//        }





        return binding.root
    }


}