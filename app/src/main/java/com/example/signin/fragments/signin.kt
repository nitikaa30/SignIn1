package com.example.signin.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.signin.R
import com.example.signin.databinding.FragmentSigninBinding

class signin : Fragment() {



    private var emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+\$"
    private var passPattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*_=+-]).{8,}\$"
    private lateinit var binding: FragmentSigninBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val fragment = ForgetPassword()
        binding.FP.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.signbtn.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, SignUp())
            transaction.addToBackStack(null)
            transaction.commit()

        }
        binding.nextBtn1.setOnClickListener {
            if(binding.user1.text.toString().isEmpty()){
                binding.user1.error="Email required"
            }
            if(binding.pass1.text.toString().isEmpty()){
                binding.pass1.error="Password Required"
            }
            else{
                if(binding.user1.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())
                    &&binding.pass1.text.toString().trim{it<=' '}.matches(passPattern.toRegex())){
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, SignUp())
                    transaction.addToBackStack(null)
                    transaction.commit()
                    showInvalidCredentialsDialog()
                    val preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    preferences.edit().putBoolean("isLoggedIn", true).apply()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Home())
                        .commit()


                }
                else {
                    Toast.makeText(context, "Invalid email address or password", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }
    private fun showInvalidCredentialsDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("ALERT")
        builder.setMessage("Logged in successfully")
        builder.setPositiveButton("OK", null)
        builder.show()
    }



}