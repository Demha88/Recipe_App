package be.bf.android.recetteapp.ui.fragments.LogInRegisterFragments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.bf.android.recetteapp.dal.DbHelper

class UserViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(DbHelper.getInstance(context).registerDatabaseDao()) as T
    }
}