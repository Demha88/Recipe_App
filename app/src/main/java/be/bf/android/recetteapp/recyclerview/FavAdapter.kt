package be.bf.android.recetteapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.api.dto.*
import be.bf.android.recetteapp.databinding.FavMealsBinding
import com.bumptech.glide.Glide

class FavAdapter () : RecyclerView.Adapter<FavAdapter.MyViewHolder>() {


    private var favMeals: List<Meal> = ArrayList()
   // var onItemClick:((List<Meal>) -> Unit)? = null
    //var onItemLongClick:((List<Meal>) -> Unit)? = null
    private lateinit var onFavClick: OnFavClick
    private lateinit var onFavLongClick: OnFavLongClick

    interface OnFavClick {
        fun onClick(meal: Meal)
    }

    interface OnFavLongClick {
        fun onLongCLick(meal: Meal)
    }

    class MyViewHolder(val binding : FavMealsBinding) : RecyclerView.ViewHolder(binding.root){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FavMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val pos  = position
       holder.binding.apply {
           Glide.with(holder.itemView)
               .load(favMeals[position].strMealThumb)
               .into(holder.binding.favImage)
         holder.binding.favName.text = favMeals[position].strMeal
         holder.binding.favCategory.text = "Category: ${favMeals[position].strCategory}"
         holder.binding.favArea.text = "Origin: ${favMeals[position].strArea}"


           val ingredients = mutableListOf<Ingredient>()

//        for (prop in Meal::class.memberProperties) {
//            val attributeValue = prop.get(favMeal)
//            val attributeName = prop.name
//            if (attributeName.contains("strIngredient")) {
//                if (attributeValue != "") {
//                    val newIngr = Ingredient(id = 0,
//                        name = attributeValue as String,
//                        qtity = "",
//                        idMeal = favMeal.idMeal,
//                        isChecked = false)
//
//                    ingredients.add(newIngr)
//                }
//            }
//        }
//
//        var i = 0
//
//        for (prop in Meal::class.memberProperties) {
//            val attributeValue = prop.get(favMeal)
//            val attributeName = prop.name
//            if (attributeName.contains("strMeasure")) {
//                if (attributeValue != " " && attributeValue != "") {
//                    ingredients[i].qtity = attributeValue as String
//                    i++
//                }
//            }
//        }


       }

        holder.itemView.setOnClickListener {
            onFavClick.onClick(favMeals[position])
        }

        holder.itemView.setOnLongClickListener {
            onFavLongClick.onLongCLick(favMeals[pos])
            true
        }
    }


    override fun getItemCount(): Int {
       return favMeals.size
    }

    fun updateFavMealAdapter(favMeals : List<Meal>){
        this.favMeals = favMeals
        notifyDataSetChanged()
    }

    fun getFavPosition(position: Int):Meal{
        return favMeals[position]
    }

    fun onFavMealClick(onFavClick: OnFavClick) {
        this.onFavClick = onFavClick
    }

    fun onFavLongClick(onFavLongClick: OnFavLongClick) {
        this.onFavLongClick = onFavLongClick
    }


}