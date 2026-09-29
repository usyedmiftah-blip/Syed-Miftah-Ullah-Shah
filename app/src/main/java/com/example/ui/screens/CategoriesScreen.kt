package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MediCareViewModel
import com.example.ui.Screen
import com.example.ui.theme.MediClinicalBg
import com.example.ui.theme.MediTeal
import com.example.ui.theme.MediTealContainer
import com.example.ui.theme.MediTealDark

data class CategoryMeta(
    val title: String,
    val description: String,
    val isRxPrevalent: Boolean = false
)

val categoryMetas = listOf(
    CategoryMeta("Antibiotics", "Infection control, penicillin, broad-spectrum oral therapy", true),
    CategoryMeta("Pain & Fever", "Analgesics, antipyretics, NSAIDs & rapid headache relief", false),
    CategoryMeta("Diabetes Care", "Oral hypoglycemics, glucose management, insulin support", true),
    CategoryMeta("Cardiovascular", "Blood pressure regulation, cholesterol statins & heart care", true),
    CategoryMeta("Allergy & Respiratory", "Antihistamines, bronchodilators, nebulizer & inhalers", false),
    CategoryMeta("Digestive Health", "Antacids, proton pump inhibitors (PPI), gut probiotics", false),
    CategoryMeta("Vitamins & Supplements", "Immune boosters, zinc, vitamin D3, calcium & multivitamins", false),
    CategoryMeta("Medical Devices", "Blood pressure monitors, oximeters, glucose meters & strips", false)
)

@Composable
fun CategoriesScreen(viewModel: MediCareViewModel) {
    val medicines by viewModel.rawMedicines.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Healthcare & Medicine Categories", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categoryMetas) { cat ->
                val count = medicines.count { it.category.equals(cat.title, ignoreCase = true) }
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectedCategory.value = cat.title
                            viewModel.navigateTo(Screen.Medicines)
                        }
                        .testTag("category_card_${cat.title}")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MediTealContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (cat.isRxPrevalent) Icons.Default.HealthAndSafety else Icons.Default.Medication,
                                    contentDescription = null,
                                    tint = MediTealDark,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(cat.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    if (cat.isRxPrevalent) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFFEE2E2)) {
                                            Text("Rx Focus", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                        }
                                    }
                                }
                                Text(cat.description, fontSize = 11.sp, color = Color(0xFF64748B), maxLines = 1)
                                Text("$count products available", fontSize = 10.sp, color = MediTealDark, fontWeight = FontWeight.SemiBold)
                            }
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF94A3B8))
                    }
                }
            }
        }
    }
}
