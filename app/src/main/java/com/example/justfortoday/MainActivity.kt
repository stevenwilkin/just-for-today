package com.example.justfortoday

import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var entries: EntryDao
    private lateinit var textTitle: TextView
    private lateinit var textDate: TextView
    private lateinit var textExcerptSource: TextView
    private lateinit var textContent: TextView
    private lateinit var textSummary: TextView

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
        textTitle = findViewById(R.id.textTitle)
        textDate = findViewById(R.id.textDate)
        textExcerptSource = findViewById(R.id.textExcerptSource)
        textContent = findViewById(R.id.textContent)
        textSummary = findViewById(R.id.textSummary)
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
            val now = LocalDate.now()
            val entry = entries.findByMonthAndDay(now.month.ordinal + 1, now.dayOfMonth)

            runOnUiThread {
                textTitle.text = entry.title.toString()
                textContent.text = entry.content.toString()

                val formatter = DateTimeFormatter.ofPattern("MMMM d")
                val date = LocalDate.of(LocalDate.now().year, entry.month ?: 1, entry.day ?: 1)
                textDate.text = formatter.format(date)

                val excerptSource = String.format("%s <i>%s</i>", entry.excerpt.toString(), entry.source.toString())
                textExcerptSource.text = Html.fromHtml(excerptSource, Html.FROM_HTML_MODE_COMPACT)

                val summary = String.format("<b>Just For Today:</b> %s", entry.summary.toString())
                textSummary.text = Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT)
            }
        }

    }
}