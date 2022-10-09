package com.merttoptas.datapersistencetutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.merttoptas.datapersistencetutorial.data.local.ClientPreferences
import com.merttoptas.datapersistencetutorial.data.local.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var clientPreferences: ClientPreferences
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var etUserName: EditText
    private lateinit var etUserAge: EditText
    private lateinit var tvUserName: TextView
    private lateinit var tvUserAge: TextView
    private lateinit var btnSubmit: Button

    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init
        etUserAge = findViewById(R.id.etUserAge)
        etUserName = findViewById(R.id.etUserName)
        btnSubmit = findViewById(R.id.btnSubmit)
        tvUserName = findViewById(R.id.tvUsername)
        tvUserAge = findViewById(R.id.tvUserAge)

        clientPreferences = ClientPreferences(this)
        dataStoreManager = DataStoreManager(this)

        // setUserData()
        setDataStoreData()
        getUserDataStore()
    }

    private fun setUserData() {
        btnSubmit.setOnClickListener {
            if (etUserAge.text.toString().trim().isNotEmpty()) {
                clientPreferences.setUserAge(etUserAge.text.toString().toInt())
            }

            if (etUserName.text.toString().trim().isNotEmpty()) {
                clientPreferences.setUserName(etUserName.text.toString())
            }
        }
        readUserData()
    }

    private fun setDataStoreData() {
        btnSubmit.setOnClickListener {
            if (etUserName.text.toString().trim().isNotEmpty()) {
                scope.launch {
                    dataStoreManager.setUserName(etUserName.text.toString())
                }
            }
        }
    }

    private fun getUserDataStore() {
        scope.launch {
            dataStoreManager.getUserName.collect { userName ->
                userName?.let {
                    tvUserName.text = it
                }
            }
        }
    }

    private fun readUserData() {
        val userName = clientPreferences.getUserName()
        val userAge = clientPreferences.getUserAge()

        tvUserName.text = userName
        tvUserAge.text = userAge.toString()

    }
}