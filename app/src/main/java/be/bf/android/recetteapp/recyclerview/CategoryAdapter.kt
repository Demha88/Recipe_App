package be.bf.android.recetteapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.api.dto.Category
import be.bf.android.recetteapp.api.dto.Meal
import be.bf.android.recetteapp.api.dto.MealByCategory
import be.bf.android.recetteapp.databinding.CategoryAdpaterBinding
import com.bumptech.glide.Glide


class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.MyViewHolder>()  {

    private var categoryList = emptyList<Category>()

    var onItemClick:((Category) -> Unit)? = null

    class MyViewHolder(val binding: CategoryAdpaterBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CategoryAdpaterBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imageCategory)
        holder.binding.categoryName.text = categoryList[position].strCategory
        holder.binding.categoryNumber.text = "#${categoryList[position].idCategory}"


        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun updateCategoryAdapter(categoryList : List<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

}