package be.bf.android.recetteapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.api.dto.Meal
import be.bf.android.recetteapp.api.dto.MealByCategory
import be.bf.android.recetteapp.databinding.AllMealsBinding
import be.bf.android.recetteapp.databinding.AllsearchBinding
import com.bumptech.glide.Glide

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>()  {

    private var searchList = emptyList<Meal>()
    lateinit var onItemClick:((Meal) -> Unit)

    class MyViewHolder(val binding : AllsearchBinding) : RecyclerView.ViewHolder(binding.root){}


    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differDiffUtil = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AllsearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val searchMeal = differDiffUtil.currentList[position]
        Glide.with(holder.itemView)
            .load(searchMeal.strMealThumb)
            .into(holder.binding.imageItemSearch)
        holder.binding.categorySearchName.text = searchMeal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(differDiffUtil.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differDiffUtil.currentList.size
    }

}