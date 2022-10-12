package be.bf.android.recetteapp.api.dto

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.bf.android.recetteapp.dal.DbHelper

class RoomViewModelFactory (val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RoomViewModel(DbHelper.getInstance(context)) as T
    }
}








