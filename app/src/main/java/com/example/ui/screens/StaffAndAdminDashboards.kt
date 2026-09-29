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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PrescriptionEntity
import com.example.data.model.Medicine
import com.example.data.model.OrderStatus
import com.example.data.model.PrescriptionStatus
import com.example.data.model.UserRole
import com.example.ui.MediCareViewModel
import com.example.ui.Screen
import com.example.ui.components.OrderStatusBadge
import com.example.ui.components.PrescriptionStatusBadge
import com.example.ui.components.RoleSwitcherDialog
import com.example.ui.components.RxBadge
import com.example.ui.theme.MediAmber
import com.example.ui.theme.MediAmberLight
import com.example.ui.theme.MediBlue
import com.example.ui.theme.MediBlueContainer
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
import com.example.ui.theme.OnMediBlueContainer

// =========================================================================
// 1. PHARMACIST DASHBOARD
// =========================================================================

@Composable
fun PharmacistDashboardScreen(viewModel: MediCareViewModel) {
    val prescriptions by viewModel.prescriptions.collectAsState()
    val orders by viewModel.orders.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Pending Verifications, 1: Approved & Dispensed
    var reviewingRx by remember { mutableStateOf<PrescriptionEntity?>(null) }
    var reviewNote by remember { mutableStateOf("") }
    var showRoleModal by remember { mutableStateOf(false) }

    val pendingRx = prescriptions.filter { it.status == PrescriptionStatus.PENDING_REVIEW.name }
    val approvedRx = prescriptions.filter { it.status != PrescriptionStatus.PENDING_REVIEW.name }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        // Clinical Top Header
        Surface(color = Color.White, shadowElevation = 2.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalPharmacy, contentDescription = null, tint = Color(0xFF6D28D9), modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Pharmacist Portal",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, color = Color(0xFF4C1D95))
                            )
                        }
                        Text("Licensed RPh: Dr. Michael Chang (Lic #RPH-77192)", fontSize = 11.sp, color = Color(0xFF64748B))
                    }

                    Button(
                        onClick = { showRoleModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDE9FE)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Switch Role", color = Color(0xFF6D28D9), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Pharmacist Metrics Row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricCard("Pending Rx", "${pendingRx.size}", Color(0xFFFEF3C7), Color(0xFFB45309), Modifier.weight(1f))
                    MetricCard("Verified", "${approvedRx.size}", MediEmeraldContainer, MediEmeraldDark, Modifier.weight(1f))
                    MetricCard("Safety Checks", "100%", MediBlueContainer, Color(0xFF0369A1), Modifier.weight(1f))
                }
            }
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFF6D28D9)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Pending Review (${pendingRx.size})", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Verified Log (${approvedRx.size})", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
            )
        }

        val displayList = if (selectedTab == 0) pendingRx else approvedRx

        if (displayList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    if (selectedTab == 0) "No prescriptions waiting for verification!" else "No verified prescriptions yet.",
                    color = Color(0xFF64748B)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(displayList) { rx ->
                    val statusEnum = try { PrescriptionStatus.valueOf(rx.status) } catch (e: Exception) { PrescriptionStatus.PENDING_REVIEW }
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth().testTag("rx_pharmacist_card_${rx.id}")
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Prescription ID: ${rx.id}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Patient: ${rx.patientName}, ${rx.patientAge} yrs", fontSize = 12.sp, color = MediTealDark, fontWeight = FontWeight.SemiBold)
                                }
                                PrescriptionStatusBadge(statusEnum)
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Doctor: ${rx.doctorName} (${rx.doctorLicense})", fontSize = 11.sp, color = Color(0xFF64748B))
                            Text("Clinical Indication: ${rx.diagnosis}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))

                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFF8FAFC),
                                modifier = Modifier.fillMaxWidth().border(1.dp, MediBorder, RoundedCornerShape(8.dp))
                            ) {
                                Text(
                                    text = "Rx Order: ${rx.notes}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF334155),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                            if (rx.pharmacistNotes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Verified by: ${rx.verifiedBy} • Notes: ${rx.pharmacistNotes}",
                                    fontSize = 11.sp,
                                    color = MediEmeraldDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            if (statusEnum == PrescriptionStatus.PENDING_REVIEW) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            reviewingRx = rx
                                            reviewNote = "Requires doctor clarification regarding dosage interval."
                                        },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MediRxRed),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Reject / Clarify", fontSize = 11.sp)
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            reviewingRx = rx
                                            reviewNote = "Verified patient history and physician credentials. Safe to dispense."
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MediEmeraldDark),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Review & Approve", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Review Modal Dialog
    if (reviewingRx != null) {
        val targetRx = reviewingRx!!
        AlertDialog(
            onDismissRequest = { reviewingRx = null },
            title = {
                Text("Pharmacist Verification Review: ${targetRx.id}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Patient: ${targetRx.patientName}, Age ${targetRx.patientAge}")
                    Text("Prescription: ${targetRx.notes}", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Drug Interaction & Safety Audit:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Surface(shape = RoundedCornerShape(6.dp), color = MediEmeraldContainer, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "✓ No lethal drug-drug interactions detected\n✓ Dosage within therapeutic index\n✓ Verified against active patient allergy profile",
                            fontSize = 11.sp,
                            color = MediEmeraldDark,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = reviewNote,
                        onValueChange = { reviewNote = it },
                        label = { Text("Official Pharmacist Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.reviewPrescription(
                            targetRx.id,
                            PrescriptionStatus.APPROVED,
                            reviewNote
                        )
                        reviewingRx = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MediEmeraldDark)
                ) {
                    Text("Sign & Approve (RPh)")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        viewModel.reviewPrescription(
                            targetRx.id,
                            PrescriptionStatus.REJECTED,
                            reviewNote
                        )
                        reviewingRx = null
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MediRxRed)
                ) {
                    Text("Reject")
                }
            }
        )
    }

    if (showRoleModal) {
        RoleSwitcherDialog(
            currentRole = UserRole.PHARMACIST,
            onRoleSelected = { viewModel.switchRole(it) },
            onDismiss = { showRoleModal = false }
        )
    }
}

// =========================================================================
// 2. STORE STAFF / INVENTORY DASHBOARD
// =========================================================================

@Composable
fun StaffDashboardScreen(viewModel: MediCareViewModel) {
    val medicines by viewModel.rawMedicines.collectAsState()
    val orders by viewModel.orders.collectAsState()
    var showRoleModal by remember { mutableStateOf(false) }
    var showAddMedicineModal by remember { mutableStateOf(false) }

    val lowStockCount = medicines.count { it.stockCount < 30 }
    val packingOrders = orders.filter { it.status == OrderStatus.PACKING || it.status == OrderStatus.PLACED }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 2.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Inventory, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Store Inventory & Fulfillment",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, color = Color(0xFF92400E))
                            )
                        }
                        Text("Springfield Central Pharmacy Dispatch Store #12", fontSize = 11.sp, color = Color(0xFF64748B))
                    }

                    Button(
                        onClick = { showRoleModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEF3C7)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Switch Role", color = Color(0xFF92400E), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricCard("Total SKUs", "${medicines.size}", Color(0xFFF1F5F9), Color(0xFF334155), Modifier.weight(1f))
                    MetricCard("Low Stock (<30)", "$lowStockCount", Color(0xFFFEE2E2), MediRxRed, Modifier.weight(1f))
                    MetricCard("Orders to Pack", "${packingOrders.size}", Color(0xFFFEF3C7), Color(0xFFB45309), Modifier.weight(1f))
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Orders to Pack Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Order Fulfillment & Packing Queue", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Surface(shape = RoundedCornerShape(10.dp), color = Color(0xFFFEF3C7)) {
                        Text("${packingOrders.size} Ready", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
            }

            if (packingOrders.isEmpty()) {
                item {
                    Text("No pending orders requiring packing at this moment.", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            } else {
                items(packingOrders) { ord ->
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
                                Text("Order ${ord.orderNumber}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                OrderStatusBadge(ord.status)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Items: ${ord.items.joinToString { "${it.medicineName} (x${it.quantity})" }}", fontSize = 12.sp, color = Color(0xFF334155))
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    viewModel.updateOrderStatus(ord.id, OrderStatus.OUT_FOR_DELIVERY)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Mark Packed & Hand to Courier", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Inventory Stock List
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Pharmacy Medicine Inventory", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Button(
                        onClick = { showAddMedicineModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Product", fontSize = 11.sp)
                    }
                }
            }

            items(medicines) { med ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (med.prescriptionRequired) {
                                    RxBadge()
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(med.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Text("${med.genericName} • ${med.strength} • $${String.format("%.2f", med.price)}", fontSize = 11.sp, color = Color(0xFF64748B))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Stock: ${med.stockCount} units (${if (med.stockCount < 30) "LOW STOCK" else "Normal"})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (med.stockCount < 30) MediRxRed else MediEmeraldDark
                            )
                        }

                        // Restock quick buttons
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedButton(
                                onClick = { viewModel.updateStock(med.id, med.stockCount + 10) },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("+10", fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Button(
                                onClick = { viewModel.updateStock(med.id, med.stockCount + 50) },
                                colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("+50", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddMedicineModal) {
        var name by remember { mutableStateOf("") }
        var generic by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("Pain & Fever") }
        var price by remember { mutableStateOf("12.50") }
        var strength by remember { mutableStateOf("500mg") }
        var stock by remember { mutableStateOf("100") }
        var isRx by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showAddMedicineModal = false },
            title = { Text("Add Medicine to Inventory", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Brand Name") })
                    OutlinedTextField(value = generic, onValueChange = { generic = it }, label = { Text("Generic / Salt Name") })
                    OutlinedTextField(value = strength, onValueChange = { strength = it }, label = { Text("Strength (e.g. 500mg)") })
                    OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price ($)") })
                    OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Initial Stock") })
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        androidx.compose.material3.Checkbox(checked = isRx, onCheckedChange = { isRx = it })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Requires Doctor Prescription (Rx)", fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val p = price.toDoubleOrNull() ?: 10.0
                        val s = stock.toIntOrNull() ?: 50
                        val med = Medicine(
                            id = "med_" + System.currentTimeMillis().toString().takeLast(5),
                            name = name.ifBlank { "New Clinical Medicine" },
                            genericName = generic.ifBlank { "Active Compound" },
                            category = category,
                            price = p,
                            originalPrice = p * 1.2,
                            inStock = true,
                            stockCount = s,
                            prescriptionRequired = isRx,
                            manufacturer = "MediCare Pharmaceuticals",
                            dosageForm = "Tablet",
                            strength = strength,
                            packSize = "10 Tablets / Strip",
                            description = "Hospital pharmacy grade preparation.",
                            usageInstructions = "As directed by attending physician.",
                            sideEffects = "Consult clinical pharmacist if symptoms occur."
                        )
                        viewModel.addCustomMedicine(med)
                        showAddMedicineModal = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal)
                ) {
                    Text("Add Product")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddMedicineModal = false }) { Text("Cancel") }
            }
        )
    }

    if (showRoleModal) {
        RoleSwitcherDialog(
            currentRole = UserRole.STORE_STAFF,
            onRoleSelected = { viewModel.switchRole(it) },
            onDismiss = { showRoleModal = false }
        )
    }
}

// =========================================================================
// 3. DELIVERY STAFF DASHBOARD
// =========================================================================

@Composable
fun DeliveryDashboardScreen(viewModel: MediCareViewModel) {
    val orders by viewModel.orders.collectAsState()
    var showRoleModal by remember { mutableStateOf(false) }

    val activeRuns = orders.filter { it.status == OrderStatus.OUT_FOR_DELIVERY }
    val deliveredHistory = orders.filter { it.status == OrderStatus.DELIVERED }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 2.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DeliveryDining, contentDescription = null, tint = MediBlue, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Medical Courier Portal",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, color = Color(0xFF0369A1))
                            )
                        }
                        Text("Courier Agent: David Chen • Insulated Thermal Box #4", fontSize = 11.sp, color = Color(0xFF64748B))
                    }

                    Button(
                        onClick = { showRoleModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MediBlueContainer),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Switch Role", color = Color(0xFF0369A1), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricCard("Active Deliveries", "${activeRuns.size}", MediBlueContainer, Color(0xFF0369A1), Modifier.weight(1f))
                    MetricCard("Delivered Today", "${deliveredHistory.size}", MediEmeraldContainer, MediEmeraldDark, Modifier.weight(1f))
                    MetricCard("On-Time Rate", "99.4%", Color(0xFFEDE9FE), Color(0xFF5B21B6), Modifier.weight(1f))
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Assigned Urgent Delivery Runs", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            if (activeRuns.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MediEmeraldDark, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("All scheduled deliveries completed!", fontWeight = FontWeight.Bold)
                            Text("New dispatches will appear here once packed by pharmacy staff.", fontSize = 11.sp, color = Color(0xFF64748B))
                        }
                    }
                }
            } else {
                items(activeRuns) { ord ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth().testTag("delivery_card_${ord.id}")
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Order ${ord.orderNumber}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                OrderStatusBadge(ord.status)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = MediTeal, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(ord.customerName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(ord.customerPhone, fontSize = 12.sp, color = Color(0xFF64748B))
                            }

                            Row(verticalAlignment = Alignment.Top) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MediRxRed, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(ord.deliveryAddress, fontSize = 12.sp, color = Color(0xFF0F172A))
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Payment: ${ord.paymentMethod} ($${String.format("%.2f", ord.total)})", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MediTealDark)

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Button(
                                    onClick = {
                                        viewModel.updateOrderStatus(ord.id, OrderStatus.DELIVERED)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MediEmeraldDark),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("mark_delivered_btn_${ord.id}")
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Confirm Handover / Mark Delivered", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Completed History
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Recently Delivered Deliveries (${deliveredHistory.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            items(deliveredHistory.take(4)) { ord ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${ord.orderNumber} • ${ord.customerName}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(ord.deliveryAddress, fontSize = 11.sp, color = Color(0xFF64748B), maxLines = 1)
                        }
                        OrderStatusBadge(ord.status)
                    }
                }
            }
        }
    }

    if (showRoleModal) {
        RoleSwitcherDialog(
            currentRole = UserRole.DELIVERY_STAFF,
            onRoleSelected = { viewModel.switchRole(it) },
            onDismiss = { showRoleModal = false }
        )
    }
}

// =========================================================================
// 4. ADMINISTRATOR DASHBOARD
// =========================================================================

@Composable
fun AdminDashboardScreen(viewModel: MediCareViewModel) {
    val medicines by viewModel.rawMedicines.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val prescriptions by viewModel.prescriptions.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    var showRoleModal by remember { mutableStateOf(false) }

    val totalSales = orders.sumOf { it.total }
    val totalPrescriptions = prescriptions.size

    val tabs = listOf("Overview", "Products", "Orders", "Prescriptions", "Reports", "Settings")

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 2.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Security, contentDescription = null, tint = MediRxRed, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "MediCare Admin Console",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, color = Color(0xFF991B1B))
                            )
                        }
                        Text("Pharmacy Operations, Regulatory Compliance & System Governance", fontSize = 11.sp, color = Color(0xFF64748B))
                    }

                    Button(
                        onClick = { showRoleModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEE2E2)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Switch Role", color = Color(0xFF991B1B), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFF991B1B),
            edgePadding = 16.dp
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    // Overview Tab
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            MetricCard("Total Revenue", "$${String.format("%.2f", totalSales)}", MediEmeraldContainer, MediEmeraldDark, Modifier.weight(1f))
                            MetricCard("Total Orders", "${orders.size}", MediBlueContainer, Color(0xFF0369A1), Modifier.weight(1f))
                        }
                    }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            MetricCard("Catalog SKUs", "${medicines.size}", Color(0xFFF1F5F9), Color(0xFF334155), Modifier.weight(1f))
                            MetricCard("Uploaded Rx", "$totalPrescriptions", Color(0xFFFEF3C7), Color(0xFFB45309), Modifier.weight(1f))
                        }
                    }

                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Pharmacy Regulatory & Health Board Health", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                HorizontalDivider(color = MediBorder)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("FDA Good Pharmacy Practice", fontSize = 12.sp)
                                    Text("COMPLIANT", fontWeight = FontWeight.Bold, color = MediEmeraldDark, fontSize = 12.sp)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Schedule II-V Drug Vault Security", fontSize = 12.sp)
                                    Text("VERIFIED", fontWeight = FontWeight.Bold, color = MediEmeraldDark, fontSize = 12.sp)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Cold Chain Storage Sensors", fontSize = 12.sp)
                                    Text("4.1°C (NORMAL)", fontWeight = FontWeight.Bold, color = MediTealDark, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Products Catalog
                    item {
                        Text("Active Pharmaceutical Products (${medicines.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    items(medicines) { med ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (med.prescriptionRequired) {
                                            RxBadge()
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                        Text(med.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                    Text("${med.category} • $${String.format("%.2f", med.price)} • Stock: ${med.stockCount}", fontSize = 11.sp, color = Color(0xFF64748B))
                                }
                                IconButton(onClick = { viewModel.updateStock(med.id, med.stockCount + 20) }) {
                                    Icon(Icons.Default.Add, contentDescription = "Add stock", tint = MediTeal)
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Orders Management
                    item {
                        Text("Master Orders Log (${orders.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    items(orders) { ord ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("${ord.orderNumber} • ${ord.customerName}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    OrderStatusBadge(ord.status)
                                }
                                Text("Total: $${String.format("%.2f", ord.total)} • Method: ${ord.paymentMethod}", fontSize = 11.sp, color = Color(0xFF64748B))
                            }
                        }
                    }
                }

                3 -> {
                    // Prescriptions Directory
                    item {
                        Text("Prescription Submissions (${prescriptions.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    items(prescriptions) { rx ->
                        val statusEnum = try { PrescriptionStatus.valueOf(rx.status) } catch (e: Exception) { PrescriptionStatus.PENDING_REVIEW }
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Rx #${rx.id} • ${rx.patientName}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    PrescriptionStatusBadge(statusEnum)
                                }
                                Text("Doctor: ${rx.doctorName} • Diagnosis: ${rx.diagnosis}", fontSize = 11.sp, color = Color(0xFF64748B))
                            }
                        }
                    }
                }

                4 -> {
                    // Reports Tab
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Sales & Dispensing Analytics", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                HorizontalDivider(color = MediBorder)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Antibiotics & Anti-Infectives", fontSize = 12.sp)
                                    Text("38% share", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Chronic Care (Diabetes & Heart)", fontSize = 12.sp)
                                    Text("32% share", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Pain, Fever & OTC Relief", fontSize = 12.sp)
                                    Text("18% share", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Medical Diagnostic Devices", fontSize = 12.sp)
                                    Text("12% share", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                5 -> {
                    // Settings Tab
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Pharmacy Configuration", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                HorizontalDivider(color = MediBorder)
                                Text("Operating Hours: 24/7 Digital Rx Order Intake", fontSize = 12.sp)
                                Text("Free Delivery Minimum: $35.00", fontSize = 12.sp)
                                Text("Standard Delivery Fee: $3.99", fontSize = 12.sp)
                                Text("Pharmacist On-Duty Shift: 8:00 AM - 10:00 PM PST", fontSize = 12.sp)
                                Text("Store Emergency Contact: +1 (800) 555-MEDCARE", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showRoleModal) {
        RoleSwitcherDialog(
            currentRole = UserRole.ADMIN,
            onRoleSelected = { viewModel.switchRole(it) },
            onDismiss = { showRoleModal = false }
        )
    }
}

@Composable
fun MetricCard(title: String, value: String, bgColor: Color, textColor: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = textColor)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Black, color = textColor)
        }
    }
}
