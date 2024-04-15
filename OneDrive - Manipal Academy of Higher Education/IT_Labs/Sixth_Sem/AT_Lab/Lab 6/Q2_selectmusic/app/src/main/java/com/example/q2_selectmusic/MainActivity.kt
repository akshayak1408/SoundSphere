package com.example.q2_selectmusic

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    lateinit var fabplay : com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var fabpause: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var fabstop: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var seek_bar: SeekBar
    lateinit var sunflower: Button
    lateinit var happy: Button
    lateinit var rock: Button
    lateinit var pop: Button
    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.sunflower, R.raw.happy, R.raw.rock, R.raw.pop)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabplay = findViewById(R.id.fab_play)
        fabpause = findViewById(R.id.fab_pause)
        fabstop = findViewById(R.id.fab_stop)
        seek_bar = findViewById(R.id.seekbar)
        sunflower = findViewById(R.id.Sunflower)
        happy = findViewById(R.id.Happy)
        rock = findViewById(R.id.Rock)
        pop = findViewById(R.id.Pop)

        sunflower.setOnClickListener {
            controlSound(currentSong[0])
        }
        happy.setOnClickListener {
            controlSound(currentSong[1])
        }
        rock.setOnClickListener {
            controlSound(currentSong[2])
        }
        pop.setOnClickListener {
            controlSound(currentSong[3])
        }

    }


    private fun controlSound(id: Int){


        fabplay.setOnClickListener{

            if(mp == null)
            {
                mp = MediaPlayer.create(this,id)
                Log.d("MainActivity", "ID : ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            mp?.start()
            Log.d("MainActivity", "Duration: ${mp!!.duration/1000} seconds")

        }

        fabpause.setOnClickListener{
            if(mp != null) mp?.pause()
            Log.d("MainActivity", "Paused at: ${mp!!.currentPosition/1000} seconds")

        }

        fabstop.setOnClickListener{
            if(mp !== null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    private fun initialiseSeekBar(){
        seek_bar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable{
            override fun run() {
                try {
                    seek_bar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                }catch(e: Exception){
                    seek_bar.progress = 0
                }
            }
        }, 0)
    }
}