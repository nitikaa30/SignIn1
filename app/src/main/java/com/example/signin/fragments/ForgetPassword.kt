package com.example.signin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.signin.R
import com.example.signin.databinding.FragmentForgetPasswordBinding

class ForgetPassword : Fragment() {
    private lateinit var binding: FragmentForgetPasswordBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentForgetPasswordBinding.inflate(inflater,container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back3.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, signin())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.nextBtn3.setOnClickListener {
            if(binding.user2.text.toString().isEmpty()){
                binding.user2.error="Email Address required"
            }
            else{
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, SignUp())
                transaction.addToBackStack(null)
                transaction.commit()

            }
        }
    }

}