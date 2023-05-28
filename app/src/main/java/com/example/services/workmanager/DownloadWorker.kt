package com.example.services.workmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters



class DownloadWorker(private val context: Context, workParams: WorkerParameters): Worker(context, workParams) {
    override fun doWork(): Result {
        try {
            val request = DownloadManager.Request((Uri.parse("https://upload.wikimedia.org/wikipedia/commons/a/a6/Downs_Link._end_of_Spring_Lane_-_geograph.org.uk_-_2447513.jpg")))
            request.apply{
                setTitle("Download Image")
                setDescription("Downloading")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloadImage")

                val downloadManager : DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)

            }
                return Result.success()
        }catch (e : Exception){
            return Result.failure()
        }

    }
}