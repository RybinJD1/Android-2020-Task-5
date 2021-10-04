package org.rsschool.cats

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.rsschool.cats.model.Image
import org.rsschool.cats.network.CatApi
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest(private val catApi: CatApi) {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.rsschool.cats", appContext.packageName)
    }

    @Test
    fun testGetListCat() {
        val a = MutableLiveData<Response<List<Image>>>()
        GlobalScope.launch(Dispatchers.Main) {
            a.value = catApi.getImages(20, 1)
            assertEquals(20, a.value?.body()?.size)
        }
    }
}
