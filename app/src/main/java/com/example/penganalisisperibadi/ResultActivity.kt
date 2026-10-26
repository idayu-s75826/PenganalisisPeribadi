package com.example.penganalisisperibadi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val textNama = findViewById<TextView>(R.id.textNama)
        val textTarikh = findViewById<TextView>(R.id.textTarikh)

        // Get data passed from MainActivity
        val nama = intent.getStringExtra("EXTRA_NAMA") ?: ""
        val tarikh = intent.getStringExtra("EXTRA_TARIKH") ?: ""

        // Process and display results
        textNama.text = processNameAnalysis(nama)
        textTarikh.text = processDateAnalysis(tarikh)
    }

    private fun processNameAnalysis(fullName: String): String {
        val cleanedName = fullName.replace(" ", "")
        val totalChars = cleanedName.length
        val vowels = "aeiou"
        val vowelCount = fullName.lowercase().filter { it in vowels }.count()

        val repeatedChars = cleanedName.lowercase()
            .groupBy { it }
            .filter { it.value.size > 1 }
            .keys
            .joinToString(", ")

        return "Nama: $fullName\n" +
                "Jumlah abjad: $totalChars\n" +
                "Bilangan vokal: $vowelCount\n" +
                "Abjad berulang: ${if (repeatedChars.isEmpty()) "Tiada" else repeatedChars}"
    }

    private fun processDateAnalysis(dobString: String): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dob = LocalDate.parse(dobString, formatter)
            val today = LocalDate.now()

            val age = Period.between(dob, today).years
            // Malay locale formatting for the day of the week
            val dayOfWeek = dob.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ms", "MY"))
                .replaceFirstChar { it.uppercase() }

            "Umur anda: $age tahun\n" +
                    "Dilahirkan pada: $dayOfWeek"
        } catch (e: Exception) {
            "Format tarikh tidak sah. Sila guna YYYY-MM-DD."
        }
    }
}