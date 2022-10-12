package be.bf.android.recetteapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.api.dto.Category
import be.bf.android.recetteapp.api.dto.MealByCategory
import be.bf.android.recetteapp.databinding.PopularMealsBinding
import com.bumptech.glide.Glide

class PopularMealAdpater(): RecyclerView.Adapter<PopularMealAdpater.MyViewHolder>() {

     lateinit var onItemClick:((MealByCategory) -> Unit)

    private var mealByCategoryList = emptyList<MealByCategory>()



    class MyViewHolder(val binding: PopularMealsBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(get: Category){
//            binding.mealCatgName.text = get.strMeal
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PopularMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealByCategoryList[position].strMealThumb)
            .into(holder.binding.imgPopular)
        holder.binding.mealCatgName.text = mealByCategoryList[position].strMeal


        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealByCategoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealByCategoryList.size
    }

    fun updateCategory(mealByCategoryList: List<MealByCategory>){
        this.mealByCategoryList = mealByCategoryList
        notifyDataSetChanged()
    }
}