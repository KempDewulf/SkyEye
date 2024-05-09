package com.howest.skyeye.test

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.testing.TestWorkerBuilder
import com.howest.skyeye.workers.ReminderWorker
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.time.delay
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Duration

@RunWith(AndroidJUnit4::class)
class ReminderWorkerTest {

    private lateinit var worker: ReminderWorker
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        worker = TestWorkerBuilder.from(context, ReminderWorker::class.java).build()
    }

    @Test
    fun testDoWork() {
        val result = worker.doWork()
        Assert.assertEquals(result, androidx.work.ListenableWorker.Result.success())
    }
}