package be.bf.android.recetteapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.api.dto.MealByCategory
import be.bf.android.recetteapp.databinding.AllMealsBinding
import com.bumptech.glide.Glide


class AllMealAdapter(): RecyclerView.Adapter<AllMealAdapter.MyViewHolder>() {

    private var mealList = emptyList<MealByCategory>()
    lateinit var onItemClick:((MealByCategory) -> Unit)



    class MyViewHolder(val binding: AllMealsBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AllMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgRandomMeal)
        holder.binding.mealAllName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun updateMealAdapter(mealList : List<MealByCategory>){
        this.mealList = mealList
        notifyDataSetChanged()
    }


}