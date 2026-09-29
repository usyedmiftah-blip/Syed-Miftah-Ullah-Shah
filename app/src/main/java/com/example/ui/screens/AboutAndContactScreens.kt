package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MediCareViewModel
import com.example.ui.theme.MediBlue
import com.example.ui.theme.MediBorder
import com.example.ui.theme.MediClinicalBg
import com.example.ui.theme.MediEmeraldDark
import com.example.ui.theme.MediRxRed
import com.example.ui.theme.MediTeal
import com.example.ui.theme.MediTealContainer
import com.example.ui.theme.MediTealDark

@Composable
fun AboutUsScreen(viewModel: MediCareViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("About MediCare Online Pharmacy", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MediTealDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.LocalPharmacy, contentDescription = null, tint = MediTealDark, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("MediCare Pharmacy", color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
                                Text("Licensed & Verified Digital Medical Store", color = Color(0xFFCCFBF1), fontSize = 11.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            "MediCare is dedicated to bridging patients with genuine pharmaceutical medications, clinically proven OTC treatments, and professional pharmacy oversight. We operate under strict state pharmacy board regulations and good distribution practices.",
                            color = Color.White,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Our Core Healthcare Standards", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        HorizontalDivider(color = MediBorder)
                        StandardRow("100% Genuine Medicines", "Direct sourcing from FDA-registered pharmaceutical manufacturers.")
                        StandardRow("Licensed RPh Verification", "Every prescription order is audited by a registered Doctor of Pharmacy.")
                        StandardRow("Temperature-Controlled Delivery", "Insulated courier packs ensure cold-chain integrity for sensitive drugs.")
                        StandardRow("Patient Privacy & HIPAA", "Strict confidentiality of all electronic prescriptions and medical histories.")
                    }
                }
            }
        }
    }
}

@Composable
fun ContactUsScreen(viewModel: MediCareViewModel) {
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var senderEmail by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Contact MediCare Support", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("24/7 Pharmacy Customer Care", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Our customer service and pharmacist on-duty team are available to answer all questions regarding orders, dosages, and prescriptions.", fontSize = 11.sp, color = Color(0xFF64748B))
                        HorizontalDivider(color = MediBorder)
                        ContactItem(Icons.Default.Phone, "Toll-Free Helpline", "+1 (800) 555-MEDCARE")
                        ContactItem(Icons.Default.Email, "Email Inquiries", "support@medicare-pharmacy.example")
                        ContactItem(Icons.Default.LocationOn, "Headquarters & Central Dispensary", "500 Healthcare Blvd, Suite 100, Springfield, OR")
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Send a Message to Pharmacy Staff", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        OutlinedTextField(
                            value = senderEmail,
                            onValueChange = { senderEmail = it },
                            label = { Text("Your Email") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        OutlinedTextField(
                            value = subject,
                            onValueChange = { subject = it },
                            label = { Text("Subject (Order # or Medicine Inquiry)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            label = { Text("Your Message") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            minLines = 3
                        )
                        Button(
                            onClick = {
                                if (senderEmail.isNotBlank() && message.isNotBlank()) {
                                    viewModel.showMessage("Message sent! A pharmacist will reply shortly.")
                                    senderEmail = ""
                                    subject = ""
                                    message = ""
                                } else {
                                    viewModel.showMessage("Please fill in email and message.")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().testTag("send_contact_msg_btn")
                        ) {
                            Text("Send Message", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StandardRow(title: String, desc: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MediEmeraldDark, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))
            Text(desc, fontSize = 11.sp, color = Color(0xFF64748B))
        }
    }
}

@Composable
private fun ContactItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MediTeal, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(label, fontSize = 10.sp, color = Color(0xFF64748B))
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
        }
    }
}
