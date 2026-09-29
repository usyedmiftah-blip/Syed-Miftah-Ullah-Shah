package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.MockData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("MediCare", appName)
  }

  @Test
  fun `verify initial clinical mock data integrity`() {
    assertTrue(MockData.initialMedicines.isNotEmpty())
    val amox = MockData.initialMedicines.find { it.id == "med_amox" }
    assertNotNull(amox)
    assertTrue(amox!!.prescriptionRequired)
    assertEquals("Antibiotics", amox.category)

    val otc = MockData.initialMedicines.find { it.id == "med_paracet" }
    assertNotNull(otc)
    assertTrue(!otc!!.prescriptionRequired)
  }

  @Test
  fun `verify all required healthcare categories have demo items`() {
    val categories = listOf(
      "Antibiotics",
      "Vitamins & Supplements",
      "First Aid",
      "Baby Care",
      "Personal Care",
      "Diabetes Care",
      "Medical Devices",
      "Skin Care"
    )
    categories.forEach { cat ->
      val found = MockData.initialMedicines.any { it.category.equals(cat, ignoreCase = true) }
      assertTrue("Category $cat should have at least one product in MockData", found)
    }
  }

  @Test
  fun `verify healthcare equipment products exist`() {
    val bpMonitor = MockData.initialMedicines.find { it.id == "med_bp_monitor" }
    assertNotNull(bpMonitor)
    val glucometer = MockData.initialMedicines.find { it.id == "med_glucometer" }
    assertNotNull(glucometer)
    val thermometer = MockData.initialMedicines.find { it.id == "med_thermometer" }
    assertNotNull(thermometer)
  }
}
