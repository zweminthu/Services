package com.example.services.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.services.R
import com.example.services.service.DownloadIntentService
import com.example.services.service.DownloadJobIntentService
import com.example.services.service.ForegroundService
import com.example.services.workmanager.DownloadWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))

        btnStartService.setOnClickListener{
            ForegroundService.startService(this, "Testing Service")
        }

        btnEndService.setOnClickListener(){
            ForegroundService.stopService(this)
        }

        btnDownloadIntentImg.setOnClickListener(){
            val intent = Intent(this, DownloadIntentService::class.java)
            intent.putExtra("image_path","https://upload.wikimedia.org/wikipedia/commons/a/a6/Downs_Link._end_of_Spring_Lane_-_geograph.org.uk_-_2447513.jpg" )
            startService(intent)
        }

        btnDownloadJobIntentImg.setOnClickListener(){
            val intent = Intent(this, DownloadJobIntentService::class.java)
            intent.putExtra("image_path","https://upload.wikimedia.org/wikipedia/commons/a/a6/Downs_Link._end_of_Spring_Lane_-_geograph.org.uk_-_2447513.jpg" )
            DownloadJobIntentService.startWork(this, intent)
        }

        btnDownloadWorkManagerImg.setOnClickListener(){
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_ROAMING)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()

            val downloadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager
                .getInstance(applicationContext)
                .enqueue(downloadWorkRequest)
        }
    }
}