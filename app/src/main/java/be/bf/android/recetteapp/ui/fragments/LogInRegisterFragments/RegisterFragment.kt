package be.bf.android.recetteapp.ui.fragments.LogInRegisterFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import be.bf.android.recetteapp.R
import be.bf.android.recetteapp.dal.entities.User
import be.bf.android.recetteapp.databinding.FragmentRegisterBinding
import com.google.gson.Gson


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding?=null
    private val binding get() = _binding!!

    private val userViewModel : UserViewModel by activityViewModels() { UserViewModelFactory(requireContext())  }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_register, container, false)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)


        userViewModel.CurrentUser.observe(viewLifecycleOwner) {
            Log.d("Register frag", it.toString())
            if (it != null) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        binding.registerButton.setOnClickListener {
            addUser()
        }


        return binding.root
    }

    private fun addUser(){
        userViewModel.registerUser(User(0,binding.emailTextField.text.toString(),binding.userNameTextField.text.toString(), binding.passwordTextField.text.toString()))

//        val user = User(0,binding.emailTextField.text.toString(),binding.userNameTextField.text.toString(), binding.passwordTextField.text.toString())
//        val sharedIntPref = requireContext().getSharedPreferences("sharedIntPref", Context.MODE_PRIVATE)
//        var edit = sharedIntPref?.edit()
//        val gson = Gson()
//        val json = gson.toJson(user)
//        edit!!.putString("addUserID", json)
//        edit.commit()


    }

//    private fun addUser(){
//        userViewModel.registerUser(User(0,binding.emailTextField.text.toString(),binding.userNameTextField.text.toString(), binding.passwordTextField.text.toString())){
//            if (it == "ERROR"){
//
//                Toast.makeText(requireContext(), "Error RegFrag", Toast.LENGTH_LONG).show()
//            }
//            else {
//                Toast.makeText(requireContext(), "REGISTERED", Toast.LENGTH_LONG).show()
//                //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//            }
//        }
//    }

//    private fun navigateLogin() {
//        userViewModel.registerUser(binding.emailTextField.text.toString(),binding.userNameTextField.text.toString(), binding.passwordTextField.text.toString()) {
//            if (it == "ERROR") {
//               // try {
//                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
//               //}  catch (ex: Exception) {
//                 //   Log.e("MyApp", "Error", ex)
//               //}
//            }
//            else {
//                //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//               // findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
//                Toast.makeText(requireContext(), "REGISTERED", Toast.LENGTH_LONG).show()
//            }
//        }
//    }


//        userViewModel.registerUser {
//
//            if (it == "ERROR"){
//                Toast.makeText(requireContext(), "Error RegFrag", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(requireContext(), "REGISTERED", Toast.LENGTH_LONG).show()
//            }
//        }
    //}





}