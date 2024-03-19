package com.example.justfortoday

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var entries: EntryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initDB()
    }

    override fun onResume() {
        super.onResume()
        displayCurrentEntry()
    }

    private fun initDB() {
        val db = Room
            .databaseBuilder(applicationContext, EntryDatabase::class.java, "jft")
            .createFromAsset("nevercured.db")
            .build()

        entries = db.entryDao()
    }

    private fun displayCurrentEntry() {
        CoroutineScope(Dispatchers.IO).launch {
            val entry = entries.getAll().first()
            Log.d("jft", entry.summary.toString())
        }

    }
}