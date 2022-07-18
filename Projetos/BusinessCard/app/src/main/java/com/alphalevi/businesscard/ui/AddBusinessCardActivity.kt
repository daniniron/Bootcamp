package com.alphalevi.businesscard.ui

import android.os.Bundle
import android.widget.Toast

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alphalevi.businesscard.App
import com.alphalevi.businesscard.R
import com.alphalevi.businesscard.data.BusinessCard
import com.alphalevi.businesscard.databinding.ActivityAddBusinessCardBinding
import yuku.ambilwarna.AmbilWarnaDialog

class AddBusinessCardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }
    private var mDefaultColor = -0x100

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
    }
    private fun setColorPicker() {
        val dialog = AmbilWarnaDialog(this@AddBusinessCardActivity, mDefaultColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    //Toast.makeText(applicationContext, "Okay!! Value Color = $color", Toast.LENGTH_SHORT).show()
                    binding.btnCorCard.setColorFilter(color) //Change color ImagemButton
                    mDefaultColor = color
                }

                override fun onCancel(dialog: AmbilWarnaDialog) {
                    Toast.makeText(applicationContext, "cancel", Toast.LENGTH_SHORT).show()
                }
            })
        dialog.show()
    }

    private fun insertListeners() {
        binding.btnCorCard.setOnClickListener { setColorPicker() }
        binding.btnConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                nome = binding.tilNome.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                fundoPersonalizado = mDefaultColor
            )
            mainViewModel.insert(businessCard)
            Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.tilCor.setOnClickListener {
            setColorPicker()
        }
        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}