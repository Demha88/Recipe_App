package be.bf.android.recetteapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.recetteapp.api.dto.Ingredient
import be.bf.android.recetteapp.api.dto.MealByCategory
import be.bf.android.recetteapp.api.dto.MealWithIngredient
import be.bf.android.recetteapp.databinding.CategoryItemsBinding
import be.bf.android.recetteapp.databinding.FavItemBinding

class IngredientAdapter(): RecyclerView.Adapter<IngredientAdapter.MyViewHolder>()  {

    private var ingredientList = emptyList<Ingredient>()

    //private var ingreList = emptyList<MealWithIngredient>()

    class MyViewHolder(val binding: FavItemBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var pos = ingredientList[position]
        //var pos = ingreList[position]
        //var pos2 = ingreList[position].ingredients[position]
        holder.binding.ingredientName.text = ingredientList[position].name
        //holder.binding.ingredientName.text = ingreList[position].ingredients[position].name
        holder.binding.ingredientMeasure.text = ingredientList[position].qtity
        //holder.binding.ingredientMeasure.text = ingreList[position].ingredients[position].qtity



        holder.itemView.apply {
            //holder.binding.ingredientName.text = ingredientList[position].name
            //holder.binding.ingredientMeasure.text = ingredientList[position].qtity

            holder.binding.checkBox.isChecked = pos.isChecked
           // holder.binding.checkBox.isChecked = pos2.isChecked

            holder.binding.checkBox.setOnClickListener {
                if (holder.binding.checkBox.isChecked){
                    detailNameStrikeThrough(holder.binding.ingredientName, true)
                    detailNameStrikeThrough(holder.binding.ingredientMeasure, true)
                } else {
                    detailNameStrikeThrough(holder.binding.ingredientName, false)
                    detailNameStrikeThrough(holder.binding.ingredientMeasure, false )
                }
                pos.isChecked = !pos.isChecked
            }

        }

    }

    override fun getItemCount(): Int {
        return ingredientList.size
     //   return ingreList.size
    }

    fun updateIngredient(ingredientList : List<Ingredient>){
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }
//fun updateIngredient(ingreList : List<MealWithIngredient>){
//    this.ingreList = ingreList
//    notifyDataSetChanged()
//}

    private fun detailNameStrikeThrough(textDetail: TextView, isChecked: Boolean){
        if(isChecked){
            textDetail.paintFlags = textDetail.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textDetail.paintFlags = textDetail.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

}