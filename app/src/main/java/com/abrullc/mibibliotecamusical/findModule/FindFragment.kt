package com.abrullc.mibibliotecamusical.findModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.databinding.FragmentFindBinding
import com.abrullc.mibibliotecamusical.databinding.FragmentHomeBinding

class FindFragment : Fragment() {
    private lateinit var mBinding: FragmentFindBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFindBinding.inflate(inflater, container, false)

        return mBinding.root
    }
}