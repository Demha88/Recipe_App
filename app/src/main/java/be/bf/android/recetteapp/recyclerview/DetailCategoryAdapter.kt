package be.bf.android.recetteapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.api.dto.Category
import be.bf.android.recetteapp.api.dto.MealByCategory
import be.bf.android.recetteapp.databinding.CategoryItemsBinding
import com.bumptech.glide.Glide

class DetailCategoryAdapter(): RecyclerView.Adapter<DetailCategoryAdapter.MyViewHolder>()  {

    private var detailCategoryList = emptyList<MealByCategory>()

    lateinit var onItemClick:((MealByCategory) -> Unit)

    class MyViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(detailCategoryList[position].strMealThumb)
            .into(holder.binding.imageItemCategory)
        holder.binding.categoryItemName.text = detailCategoryList[position].strMeal
        holder.binding.categoryItemNumber.text = "#${detailCategoryList[position].idMeal}"


        holder.itemView.setOnClickListener {
            onItemClick.invoke(detailCategoryList[position])
        }
    }

    override fun getItemCount(): Int {
return detailCategoryList.size
    }

    fun updateDetailCategoryAdapter(detailCategoryList : List<MealByCategory>){
        this.detailCategoryList = detailCategoryList
        notifyDataSetChanged()
    }


}