package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.data.local.AddressEntity
import com.example.data.model.PrescriptionStatus
import com.example.data.model.UserRole
import com.example.ui.MediCareViewModel
import com.example.ui.Screen
import com.example.ui.components.MedicineCard
import com.example.ui.components.PrescriptionStatusBadge
import com.example.ui.components.RoleSwitcherDialog
import com.example.ui.theme.MediBorder
import com.example.ui.theme.MediClinicalBg
import com.example.ui.theme.MediEmerald
import com.example.ui.theme.MediEmeraldContainer
import com.example.ui.theme.MediEmeraldDark
import com.example.ui.theme.MediRxRed
import com.example.ui.theme.MediRxRedLight
import com.example.ui.theme.MediTeal
import com.example.ui.theme.MediTealContainer
import com.example.ui.theme.MediTealDark

@Composable
fun PrescriptionUploadScreen(viewModel: MediCareViewModel) {
    var patientName by remember { mutableStateOf("Sarah Jenkins") }
    var patientAge by remember { mutableStateOf("34") }
    var doctorName by remember { mutableStateOf("Dr. Robert Vance, MD") }
    var doctorLicense by remember { mutableStateOf("MD-984210") }
    var diagnosis by remember { mutableStateOf("Acute Bronchitis & Wheezing") }
    var notes by remember { mutableStateOf("Amoxicillin 500mg (1 capsule TID for 7 days) & Salbutamol Inhaler (1-2 puffs PRN)") }
    var isFileAttached by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Upload Doctor's Prescription", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MediTealContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = MediTealDark, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Certified Pharmacist Verification", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MediTealDark)
                            Text(
                                "Our registered pharmacists will review this prescription, check contraindications, and approve for fulfillment.",
                                fontSize = 11.sp,
                                color = Color(0xFF0F766E)
                            )
                        }
                    }
                }
            }

            // Prescription Document Upload Box
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth().testTag("rx_upload_zone")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF1F5F9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.FileUpload, contentDescription = null, tint = MediTeal, modifier = Modifier.size(30.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Prescription Document Attached", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("IMG_Rx_2026_Bronchitis.pdf (2.4 MB)", fontSize = 11.sp, color = Color(0xFF64748B))

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = { isFileAttached = true },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Re-upload / Change File", fontSize = 11.sp)
                        }
                    }
                }
            }

            // Form inputs
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Patient & Prescriber Details", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                        OutlinedTextField(
                            value = patientName,
                            onValueChange = { patientName = it },
                            label = { Text("Patient Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = patientAge,
                            onValueChange = { patientAge = it },
                            label = { Text("Patient Age") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = doctorName,
                            onValueChange = { doctorName = it },
                            label = { Text("Prescribing Physician") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = doctorLicense,
                            onValueChange = { doctorLicense = it },
                            label = { Text("Doctor License / Registration #") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = diagnosis,
                            onValueChange = { diagnosis = it },
                            label = { Text("Clinical Diagnosis / Indication") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text("Prescribed Medicines & Dosage Instructions") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            minLines = 3
                        )
                    }
                }
            }
        }

        Surface(color = Color.White, shadowElevation = 8.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        val ageInt = patientAge.toIntOrNull() ?: 30
                        viewModel.uploadPrescription(patientName, ageInt, doctorName, doctorLicense, diagnosis, notes)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("submit_prescription_btn")
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submit for Pharmacist Review", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun PrescriptionListScreen(viewModel: MediCareViewModel) {
    val prescriptions by viewModel.prescriptions.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Prescription Records", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Button(
                    onClick = { viewModel.navigateTo(Screen.UploadPrescription) },
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Upload", fontSize = 12.sp)
                }
            }
        }

        if (prescriptions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No prescriptions uploaded yet.", color = Color(0xFF64748B))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(prescriptions) { rx ->
                    val statusEnum = try { PrescriptionStatus.valueOf(rx.status) } catch (e: Exception) { PrescriptionStatus.PENDING_REVIEW }
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Description, contentDescription = null, tint = MediTeal, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Prescription #${rx.id}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                                PrescriptionStatusBadge(statusEnum)
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Patient: ${rx.patientName} (${rx.patientAge} yrs)", fontSize = 12.sp, color = Color(0xFF334155))
                            Text("Doctor: ${rx.doctorName} • ${rx.doctorLicense}", fontSize = 12.sp, color = Color(0xFF64748B))
                            Text("Diagnosis: ${rx.diagnosis}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MediTealDark)

                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFF8FAFC), modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = rx.notes,
                                    fontSize = 11.sp,
                                    color = Color(0xFF334155),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            if (rx.pharmacistNotes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Pharmacist Note: ${rx.pharmacistNotes}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (statusEnum == PrescriptionStatus.APPROVED) MediEmeraldDark else MediRxRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddressesScreen(viewModel: MediCareViewModel) {
    val addresses by viewModel.addresses.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    var newTitle by remember { mutableStateOf("Apartment") }
    var newRecipient by remember { mutableStateOf("Sarah Jenkins") }
    var newPhone by remember { mutableStateOf("+1 (555) 349-8821") }
    var newStreet by remember { mutableStateOf("500 Medical Center Blvd, Suite 210") }
    var newCity by remember { mutableStateOf("Springfield") }
    var newState by remember { mutableStateOf("OR") }
    var newZip by remember { mutableStateOf("97477") }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Text("Saved Delivery Addresses", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                TextButton(onClick = { showAddDialog = true }) {
                    Text("+ Add New", color = MediTeal, fontWeight = FontWeight.Bold)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(addresses) { addr ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MediTeal)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(addr.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                if (addr.isDefault) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(shape = RoundedCornerShape(4.dp), color = MediTealContainer) {
                                        Text("Default", fontSize = 10.sp, color = MediTealDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                    }
                                }
                            }

                            if (!addr.isDefault) {
                                TextButton(onClick = { viewModel.setDefaultAddress(addr.id) }) {
                                    Text("Make Default", fontSize = 11.sp, color = MediTeal)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(addr.recipientName, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        Text("${addr.street}, ${addr.city}, ${addr.state} ${addr.zipCode}", fontSize = 12.sp, color = Color(0xFF64748B))
                        Text("Phone: ${addr.phone}", fontSize = 11.sp, color = Color(0xFF94A3B8))
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Delivery Address") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = newTitle, onValueChange = { newTitle = it }, label = { Text("Title (Home, Office, etc.)") })
                    OutlinedTextField(value = newStreet, onValueChange = { newStreet = it }, label = { Text("Street Address") })
                    OutlinedTextField(value = newCity, onValueChange = { newCity = it }, label = { Text("City") })
                    OutlinedTextField(value = newZip, onValueChange = { newZip = it }, label = { Text("ZIP Code") })
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val addr = AddressEntity(
                            id = "addr_" + System.currentTimeMillis().toString().takeLast(5),
                            title = newTitle,
                            recipientName = newRecipient,
                            phone = newPhone,
                            street = newStreet,
                            city = newCity,
                            state = newState,
                            zipCode = newZip,
                            isDefault = false
                        )
                        viewModel.saveAddress(addr)
                        showAddDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal)
                ) {
                    Text("Save Address")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun WishlistScreen(viewModel: MediCareViewModel) {
    val wishlist by viewModel.wishlistMedicines.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Saved Medicines & Wishlist (${wishlist.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        if (wishlist.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No medicines saved in wishlist.", color = Color(0xFF64748B))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(wishlist) { med ->
                    MedicineCard(
                        medicine = med,
                        onMedicineClick = { viewModel.navigateTo(Screen.MedicineDetail(med.id)) },
                        onAddToCart = { viewModel.addToCart(med.id) },
                        isWishlisted = true,
                        onToggleWishlist = { viewModel.toggleWishlist(med.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerProfileScreen(viewModel: MediCareViewModel) {
    val currentRole by viewModel.currentRole.collectAsState()
    var showRoleModal by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Patient Profile & Health Portal", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Patient details card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(MediTealContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = MediTealDark, modifier = Modifier.size(32.dp))
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text("Sarah Jenkins", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("Patient ID: MC-PT-88910", fontSize = 11.sp, color = MediTealDark, fontWeight = FontWeight.SemiBold)
                                Text("sarah.jenkins@example.com • +1 (555) 349-8821", fontSize = 11.sp, color = Color(0xFF64748B))
                            }
                        }
                    }
                }
            }

            // Clinical Emergency & Medical info
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MedicalInformation, contentDescription = null, tint = MediTeal)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Health & Insurance Details", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        HorizontalDivider(color = MediBorder)
                        DetailItem("Blood Group", "O Positive (O+)")
                        DetailItem("Drug Allergies", "Penicillin (Mild Rash), Shellfish", isRed = true)
                        DetailItem("Insurance Policy", "BlueCross RX-889021 (Co-pay 10%)")
                        DetailItem("Emergency Contact", "Mark Jenkins (+1 555-019-3320)")
                    }
                }
            }

            // Quick Portal Actions
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        PortalLinkItem("Prescription Documents", Icons.Default.Description) { viewModel.navigateTo(Screen.Prescriptions) }
                        PortalLinkItem("Order History & Tracking", Icons.Default.ReceiptLong) { viewModel.navigateTo(Screen.Orders) }
                        PortalLinkItem("Saved Delivery Addresses", Icons.Default.LocationOn) { viewModel.navigateTo(Screen.Addresses) }
                        PortalLinkItem("Wishlist & Saved Medicines", Icons.Default.LocalPharmacy) { viewModel.navigateTo(Screen.Wishlist) }
                    }
                }
            }

            // Switch Workspace Card (Customer -> Pharmacist -> Store Staff -> Delivery -> Admin)
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    modifier = Modifier.fillMaxWidth().testTag("switch_workspace_card")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = Color(0xFF334155))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Operational Dashboards", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "MediCare includes specialized portals for Pharmacists, Store Staff, Delivery Partners, and Administrators.",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { showRoleModal = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().testTag("profile_switch_role_btn")
                        ) {
                            Text("Switch Workspace (Current: ${currentRole.displayName})")
                        }
                    }
                }
            }
        }
    }

    if (showRoleModal) {
        RoleSwitcherDialog(
            currentRole = currentRole,
            onRoleSelected = { viewModel.switchRole(it) },
            onDismiss = { showRoleModal = false }
        )
    }
}

@Composable
fun DetailItem(label: String, value: String, isRed: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 12.sp, color = Color(0xFF64748B))
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (isRed) MediRxRed else Color(0xFF0F172A))
    }
}

@Composable
fun PortalLinkItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MediTeal, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
    }
}
