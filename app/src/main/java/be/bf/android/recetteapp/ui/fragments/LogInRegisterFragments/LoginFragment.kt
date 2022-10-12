package be.bf.android.recetteapp.ui.fragments.LogInRegisterFragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import be.bf.android.recetteapp.R
import be.bf.android.recetteapp.dal.entities.User
import be.bf.android.recetteapp.databinding.FragmentLoginBinding
import com.google.gson.Gson


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!






    //private val loginViewModel : LoginViewModel by activityViewModels() {LoginViewModelFactory(requireContext())}
    private val userViewModel : UserViewModel by activityViewModels() { UserViewModelFactory(requireContext())  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login, container, false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        val binding : FragmentLoginBinding = DataBindingUtil.inflate( inflater,
//            R.layout.fragment_login, container, false)

//        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { isOK ->
//            if (isOK == true){
//                Log.i("MonTag","dans observe")
//                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//                loginViewModel.doneNavigatingRegister()
//            }
//        } )
//
//        loginViewModel.navigatetoHome.observe(viewLifecycleOwner, Observer { isOK ->
//            if (isOK == true){
//                Log.i("MonTag","dans observe")
//                navigateHome()
//                loginViewModel.doneNavigatingRegister()
//            }
//        } )
//
//
//
//        loginViewModel.errortoast.observe(viewLifecycleOwner, Observer { isError->
//            if (isError==true){
//                Toast.makeText(requireContext(), "Fill all fields please!", Toast.LENGTH_LONG).show()
//                loginViewModel.donetoast()
//            }
//        })
//
//        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { isError->
//            if(isError==true){
//                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_LONG).show()
//                loginViewModel.donetoastInvalidPassword()
//            }
//        })




      binding.loginButton.setOnClickListener {
                   navigateHome()
        }

      binding.SignUpButton.setOnClickListener {
          findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
      }
        return binding.root
    }

    private fun navigateHome() {
        userViewModel.loginUser(binding.userNameTextField.text.toString(), binding.passwordTextField.text.toString()) { it, userId ->
            if (it == "ERROR") {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
            else {
                view?.post {
                    val sharedPref = requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
                    var edit = sharedPref?.edit()
                    edit!!.putString("addUserName", binding.userNameTextField.text.toString())
                    edit.putInt("addUserID", userId!!)
                    edit.apply()

//                    val user : User
//                    val sharedIntPref = requireContext().getSharedPreferences("sharedIntPref", Context.MODE_PRIVATE)
//                    var edit = sharedIntPref?.edit()
//                    val gson = Gson()
//                    val json = gson.toJson(user)
//                    edit!!.putString("addUserID", json)
//                    edit.commit()

//                    val appContext = requireContext().applicationContext
//                    val prefs = appContext.getSharedPreferences("sharedIntPref", Context.MODE_PRIVATE)
//                    val gson = Gson()
//                    val json: String = prefs.getString("addUserID", "{}")!!
//                    val obj: User = gson.fromJson(json, User::class.java)


                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }

            }
        }
    }



}