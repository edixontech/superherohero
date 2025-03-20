package com.example.superherohero.activities

import android.icu.text.CaseMap.Lower
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superherohero.data.SuperheroService
import com.example.superherohero.R
import com.example.superherohero.data.Superhero
import com.example.superherohero.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SUPERHERO_ID = "SUPERHERO_ID"
    }

    lateinit var binding: ActivityDetailBinding

    lateinit var superhero: Superhero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("SUPERHERO_ID")!!
        getSuperheroById(id)

        binding.navigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_biography -> {
                    binding.appearanceContent.root.visibility = View.GONE
                    binding.statsContent.root.visibility = View.GONE
                    binding.biographyContent.root.visibility = View.VISIBLE
                }

                R.id.menu_appearance -> {
                    binding.statsContent.root.visibility = View.GONE
                    binding.biographyContent.root.visibility = View.GONE
                    binding.appearanceContent.root.visibility = View.VISIBLE
                }

                R.id.menu_stats -> {
                    binding.biographyContent.root.visibility = View.GONE
                    binding.appearanceContent.root.visibility = View.GONE
                    binding.statsContent.root.visibility = View.VISIBLE
                }
            }
            true
        }

        binding.navigationBar.selectedItemId = R.id.biographyContent
    }

    fun loadData() {
        Picasso.get().load(superhero.image.url).into(binding.avatarImageView)

        supportActionBar?.title = superhero.name
        supportActionBar?.subtitle = superhero.biography.realName

        // Biography
        binding.biographyContent.publisherTextView.text = superhero.biography.publisher
        binding.biographyContent.placeOfBirthTextView.text = superhero.biography.placeOfBirth
        binding.biographyContent.alignmentTextView.text = superhero.biography.alignment
        //if(superhero.biography.alignment.lowercase() == "good")
        binding.biographyContent.alignmentTextView.setTextColor(getColor(superhero.getAlignmentColor()))



        binding.biographyContent.occupationTextView.text = superhero.work.occupation
        binding.biographyContent.baseTextView.text = superhero.work.base

        // Appearance
        with(superhero.appearance) {
            binding.appearanceContent.raceTextView.text = race
            binding.appearanceContent.genderTextView.text = gender
            binding.appearanceContent.eyeColorTextView.text = eyeColor
            binding.appearanceContent.hairColorTextView.text = hairColor
            binding.appearanceContent.weightTextView.text = getWeightKg()
            binding.appearanceContent.heightTextView.text = getHeightCm()


            // Stats

            binding.statsContent.intelligenceTextView.text = superhero.stats.intelligence.toString()
            binding.statsContent.intelligenceProgressBar.progress =
                superhero.stats.intelligence.toIntOrNull() ?: 0
            binding.statsContent.strengthTextView.text = superhero.stats.strength.toString()
            binding.statsContent.strengthProgressBar.progress =
                superhero.stats.strength.toIntOrNull() ?: 0
            binding.statsContent.speedTextView.text = superhero.stats.speed.toString()
            binding.statsContent.speedProgressBar.progress =
                superhero.stats.speed.toIntOrNull() ?: 0
            binding.statsContent.durabilityTextView.text = superhero.stats.durability.toString()
            binding.statsContent.durabilityProgressBar.progress =
                superhero.stats.durability.toIntOrNull() ?: 0
            binding.statsContent.powerTextView.text = superhero.stats.durability.toString()
            binding.statsContent.powerProgressBar.progress =
                superhero.stats.durability.toIntOrNull() ?: 0
            binding.statsContent.combatTextView.text = superhero.stats.durability.toString()
            binding.statsContent.combatProgressBar.progress =
                superhero.stats.durability.toIntOrNull() ?: 0
        }
    }

    fun getRetrofit(): SuperheroService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.superheroapi.com/api.php/7252591128153666/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SuperheroService::class.java)
    }

    fun getSuperheroById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                superhero = service.findSuperheroById(id)

                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}