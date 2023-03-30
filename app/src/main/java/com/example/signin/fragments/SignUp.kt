package com.example.signin.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import com.example.signin.API
import com.example.signin.R
import com.example.signin.Register
import com.example.signin.RegisterResponse
import com.example.signin.databinding.FragmentSignUpBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUp : Fragment() {

    private var emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+\$"
    private var passPattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*_=+-]).{8,}\$"

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var myButton: ImageButton
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?)
    : View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val okHttpClient=OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://restapi.adequateshop.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()

        val api = retrofit.create(API::class.java)

//        api.registerUser(userData).enqueue(object : Callback<Register> {
//            override fun onResponse(call: Call<Register>, response: Response<Register>) {
//                if (response.isSuccessful) {
//                    val user = response.body()
//                    val transaction = parentFragmentManager.beginTransaction()
//                    transaction.replace(R.id.fragment_container, Home())
//                    transaction.addToBackStack(null)
//                    transaction.commit()
//
//                    // Handle successful registration
//                } else {
//                    // Handle unsuccessful registration
//                }
//            }
//
//            override fun onFailure(call: Call<Register>, t: Throwable) {
//                // Handle registration failure
//            }
//        })

        val fragment = ForgetPassword()
        binding.nextBtn.setOnClickListener {
            if(binding.username.text.toString().isEmpty()){
                binding.username.error="Name required"
            }
            if(binding.user.text.toString().isEmpty()){
                binding.user.error="Enter email"
            }
            if(binding.pass4.text.toString().isEmpty()){
                binding.pass4.error="Password required"
            }
            if(binding.repass1.text.toString().isEmpty()){
                binding.repass1.error="Password required"
            }
            else{
                if(binding.user.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())
                    &&binding.pass4.text.toString().trim{it<=' '}.matches(passPattern.toRegex())
                    &&binding.repass1.text.toString().trim{it<=' '}.matches(passPattern.toRegex())){
                    if((binding.pass4.text.toString()==binding.repass1.text.toString())){
                        showInvalidCredentialsDialog()
                        val email=binding.user.text.toString()
                        val name=binding.username.text.toString()
                        val password=binding.pass4.text.toString()
                        val userData = Register(email,name, password)
                        getData(userData)




                    }
                    else{
                        Toast.makeText(context,"Password doesn't match",Toast.LENGTH_SHORT).show()
                    }


                }
                else {
                    Toast.makeText(context, "Invalid email address or password",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.back.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    fun getData(userData: Register){
        RetrofitClient.apiInterface.registerUser(userData)
            .enqueue(object : Callback<RegisterResponse?> {
                override fun onResponse(call: Call<RegisterResponse?>, response: Response<RegisterResponse?>) {
                    if(response.isSuccessful){
                        Log.d("hi", "onResponse: ${response.body()}")
                        val preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        preferences.edit().putBoolean("isLoggedIn", true).apply()
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container, signin())
                        transaction.addToBackStack(null)
                        transaction.commit()

                    }else{
                        Toast.makeText(context,response.errorBody()?.string(),Toast.LENGTH_LONG).show()
                        response.errorBody()?.string()?.let { Log.d("hdhd", it) }
                    }


                }

                override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
//            RetrofitClient.apiInterface.getInfo().enqueue(object : Callback<RegisterResponse?> {
//                override fun onResponse(call: Call<RegisterResponse?>,
//                                        response: Response<RegisterResponse?>) {
//                    if (response.isSuccessful) {
//                        val users = response.body()
//                        savePostsToSharedPreferences(users)
//                        displayPosts(users)
//                        // Save the list of posts to Shared Preferences
//                    } else {
//                        Toast.makeText(context,response.errorBody()?.string(),Toast.LENGTH_LONG).show()
//                        // Handle unsuccessful API call
//                    }
//                }
//                override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
//
//                }
//            })
    }

    private fun showInvalidCredentialsDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("ALERT")
        builder.setMessage("Confirm Password")
        builder.setPositiveButton("OK", null)
        builder.setNegativeButton("Cancel",null)
        builder.show()
    }
    private fun savePostsToSharedPreferences(users: RegisterResponse?) {
        if (users != null) {
            val sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val json = Gson().toJson(users)
            editor.putString("users", json)
            editor.apply()
        }
    }
    private fun displayPosts(users: RegisterResponse?) {
        if (users != null) {
            val sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val json = sharedPreferences.getString("users", "")
            val type = object : TypeToken<RegisterResponse>() {}.type
            val savedPosts = Gson().fromJson<RegisterResponse>(json, type)
            // Display the list of posts in your app's UI
        }
    }

}