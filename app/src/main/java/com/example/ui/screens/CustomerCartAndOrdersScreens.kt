package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AddressEntity
import com.example.data.model.CartItemWithDetails
import com.example.data.model.OrderStatus
import com.example.data.model.OrderWithItems
import com.example.data.model.PrescriptionStatus
import com.example.ui.MediCareViewModel
import com.example.ui.Screen
import com.example.ui.components.OrderStatusBadge
import com.example.ui.components.PrescriptionStatusBadge
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
import com.example.ui.theme.OnMediTealContainer

@Composable
fun CartScreen(viewModel: MediCareViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()
    val promoDiscount by viewModel.promoDiscount.collectAsState()
    val promoCodeInput by viewModel.promoCode.collectAsState()

    val subtotal = cartItems.sumOf { it.medicine.price * it.quantity }
    val freeDeliveryThreshold = 35.0
    val deliveryFee = if (subtotal >= freeDeliveryThreshold || subtotal == 0.0) 0.0 else 3.99
    val total = (subtotal + deliveryFee - promoDiscount).coerceAtLeast(0.0)
    val hasRxItem = cartItems.any { it.medicine.prescriptionRequired }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        // App bar
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Medical Prescription Basket (${cartItems.sumOf { it.quantity }})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                if (cartItems.isNotEmpty()) {
                    TextButton(onClick = { viewModel.clearCart() }) {
                        Text("Clear", color = MediRxRed, fontSize = 12.sp)
                    }
                }
            }
        }

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        tint = Color(0xFFCBD5E1),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Your prescription basket is empty",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Explore our verified catalogue of medicines and healthcare devices.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B)),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.navigateTo(Screen.Medicines) },
                        colors = ButtonDefaults.buttonColors(containerColor = MediTeal)
                    ) {
                        Text("Browse Catalog")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Rx items notice
                if (hasRxItem) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MediRxRedLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = MediRxRed, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Contains Prescription Required Items (Rx). You will attach your prescription during checkout.",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF7F1D1D)
                                )
                            }
                        }
                    }
                }

                // Delivery progress threshold indicator
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DeliveryDining, contentDescription = null, tint = MediTeal, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                if (subtotal >= freeDeliveryThreshold) {
                                    Text("You qualify for FREE Medical Express Delivery!", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MediEmeraldDark)
                                } else {
                                    Text(
                                        "Add $${String.format("%.2f", freeDeliveryThreshold - subtotal)} more for FREE Delivery",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = MediTealDark
                                    )
                                }
                            }
                        }
                    }
                }

                // Items list
                items(cartItems) { item ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth().testTag("cart_item_${item.medicine.id}")
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (item.medicine.prescriptionRequired) {
                                        RxBadge()
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Text(
                                        text = item.medicine.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                                Text(
                                    text = "${item.medicine.strength} • ${item.medicine.dosageForm}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF64748B)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$${String.format("%.2f", item.medicine.price)} each",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MediTealDark
                                )
                            }

                            // Stepper
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MediBorder),
                                color = Color(0xFFF8FAFC)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { viewModel.updateCartQuantity(item.medicine.id, item.quantity - 1) },
                                        modifier = Modifier.size(32.dp).testTag("cart_minus_${item.medicine.id}")
                                    ) {
                                        Icon(
                                            imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
                                            contentDescription = "Minus",
                                            modifier = Modifier.size(14.dp),
                                            tint = if (item.quantity == 1) MediRxRed else Color(0xFF334155)
                                        )
                                    }
                                    Text(
                                        text = item.quantity.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )
                                    IconButton(
                                        onClick = { viewModel.updateCartQuantity(item.medicine.id, item.quantity + 1) },
                                        modifier = Modifier.size(32.dp).testTag("cart_plus_${item.medicine.id}")
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Plus", modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Coupon code card
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Health Discount Coupon", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedTextField(
                                    value = promoCodeInput,
                                    onValueChange = { viewModel.promoCode.value = it },
                                    placeholder = { Text("Use MEDICARE10 for $5 off", fontSize = 11.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = { viewModel.applyPromo(promoCodeInput) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Apply", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                // Bill summary
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Order Summary", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            BillRow("Items Subtotal", "$${String.format("%.2f", subtotal)}")
                            BillRow(
                                "Delivery Fee",
                                if (deliveryFee == 0.0) "FREE" else "$${String.format("%.2f", deliveryFee)}",
                                isGreen = deliveryFee == 0.0
                            )
                            if (promoDiscount > 0.0) {
                                BillRow("Promo Coupon Discount", "-$${String.format("%.2f", promoDiscount)}", isGreen = true)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MediBorder)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total Amount", fontWeight = FontWeight.Black, fontSize = 15.sp)
                                Text(
                                    text = "$${String.format("%.2f", total)}",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = MediTealDark
                                )
                            }
                        }
                    }
                }
            }

            // Bottom checkout button
            Surface(color = Color.White, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Total to Pay", fontSize = 11.sp, color = Color(0xFF64748B))
                        Text(
                            text = "$${String.format("%.2f", total)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = MediTealDark
                        )
                    }
                    Button(
                        onClick = { viewModel.navigateTo(Screen.Checkout) },
                        colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("proceed_to_checkout_btn")
                    ) {
                        Text("Proceed to Checkout", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BillRow(label: String, value: String, isGreen: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 12.sp, color = Color(0xFF475569))
        Text(
            value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isGreen) MediEmeraldDark else Color(0xFF0F172A)
        )
    }
}

@Composable
fun CheckoutScreen(viewModel: MediCareViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()
    val addresses by viewModel.addresses.collectAsState()
    val prescriptions by viewModel.prescriptions.collectAsState()
    val selectedAddressId by viewModel.selectedAddressId.collectAsState()
    val selectedPaymentMethod by viewModel.selectedPaymentMethod.collectAsState()
    val attachedPrescriptionId by viewModel.attachedPrescriptionId.collectAsState()
    val promoDiscount by viewModel.promoDiscount.collectAsState()

    val subtotal = cartItems.sumOf { it.medicine.price * it.quantity }
    val deliveryFee = if (subtotal >= 35.0 || subtotal == 0.0) 0.0 else 3.99
    val total = (subtotal + deliveryFee - promoDiscount).coerceAtLeast(0.0)
    val hasRxItem = cartItems.any { it.medicine.prescriptionRequired }

    val paymentOptions = listOf(
        "Health Insurance / HSA Card",
        "Credit / Debit Card",
        "Cash on Delivery",
        "UPI / Net Banking"
    )

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        // App Bar
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Checkout & Medical Verification", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Delivery Address Card
            item {
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
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MediTeal, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Delivery Address", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            TextButton(onClick = { viewModel.navigateTo(Screen.Addresses) }) {
                                Text("Manage", color = MediTeal, fontSize = 12.sp)
                            }
                        }

                        addresses.forEach { addr ->
                            val isSelected = addr.id == selectedAddressId
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) MediTealContainer else Color(0xFFF8FAFC),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) MediTeal else MediBorder),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { viewModel.selectedAddressId.value = addr.id }
                            ) {
                                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { viewModel.selectedAddressId.value = addr.id }
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text("${addr.title} • ${addr.recipientName}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Text("${addr.street}, ${addr.city}, ${addr.state} ${addr.zipCode}", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text("Phone: ${addr.phone}", fontSize = 10.sp, color = Color(0xFF94A3B8))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 2. Prescription verification section
            if (hasRxItem) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth().testTag("checkout_rx_section")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Description, contentDescription = null, tint = MediRxRed, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Doctor's Prescription (Required)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                                TextButton(onClick = { viewModel.navigateTo(Screen.UploadPrescription) }) {
                                    Text("+ Upload New", color = MediTeal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Text(
                                "Your order contains schedule medications. Select an approved/pending prescription on file:",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            if (prescriptions.isEmpty()) {
                                OutlinedButton(
                                    onClick = { viewModel.navigateTo(Screen.UploadPrescription) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.FileUpload, contentDescription = null)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Upload Doctor's Prescription Now")
                                }
                            } else {
                                prescriptions.forEach { rx ->
                                    val isSelected = rx.id == attachedPrescriptionId
                                    val statusEnum = try { PrescriptionStatus.valueOf(rx.status) } catch (e: Exception) { PrescriptionStatus.PENDING_REVIEW }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSelected) MediTealContainer else Color(0xFFF8FAFC),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) MediTeal else MediBorder),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable { viewModel.attachedPrescriptionId.value = rx.id }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                                RadioButton(
                                                    selected = isSelected,
                                                    onClick = { viewModel.attachedPrescriptionId.value = rx.id }
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Column {
                                                    Text("Rx #${rx.id} • ${rx.doctorName}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                                    Text(rx.diagnosis, fontSize = 11.sp, color = Color(0xFF64748B))
                                                }
                                            }
                                            PrescriptionStatusBadge(statusEnum)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 3. Payment Method Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Payment, contentDescription = null, tint = MediBlue, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Payment Method", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        paymentOptions.forEach { method ->
                            val isSelected = method == selectedPaymentMethod
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) MediBlueContainer else Color(0xFFF8FAFC),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) MediBlue else MediBorder),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { viewModel.selectedPaymentMethod.value = method }
                            ) {
                                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { viewModel.selectedPaymentMethod.value = method }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(method, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Order breakdown
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Bill Breakdown", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        BillRow("Total Medicines (${cartItems.size})", "$${String.format("%.2f", subtotal)}")
                        BillRow("Express Temperature-Safe Delivery", if (deliveryFee == 0.0) "FREE" else "$${String.format("%.2f", deliveryFee)}", isGreen = deliveryFee == 0.0)
                        if (promoDiscount > 0.0) {
                            BillRow("Applied Promo Coupon", "-$${String.format("%.2f", promoDiscount)}", isGreen = true)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = MediBorder)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Grand Total", fontWeight = FontWeight.Black, fontSize = 15.sp)
                            Text("$${String.format("%.2f", total)}", fontWeight = FontWeight.Black, fontSize = 16.sp, color = MediTealDark)
                        }
                    }
                }
            }
        }

        // Place Order Action Bar
        Surface(color = Color.White, shadowElevation = 8.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { viewModel.placeCurrentOrder() },
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("confirm_place_order_btn")
                ) {
                    Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Confirm & Place Pharmacy Order ($${String.format("%.2f", total)})", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun OrdersScreen(viewModel: MediCareViewModel) {
    val orders by viewModel.orders.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("My Medicine Orders", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }

        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No orders placed yet.", color = Color(0xFF64748B))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { order ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.navigateTo(Screen.OrderDetail(order.id)) }
                            .testTag("order_card_${order.id}")
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(order.orderNumber, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(order.estimatedDelivery, fontSize = 11.sp, color = Color(0xFF64748B))
                                }
                                OrderStatusBadge(order.status)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = order.items.joinToString(", ") { "${it.medicineName} (x${it.quantity})" },
                                fontSize = 12.sp,
                                color = Color(0xFF334155),
                                maxLines = 2
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = MediBorder)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total: $${String.format("%.2f", order.total)} (${order.paymentMethod})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MediTealDark
                                )

                                TextButton(
                                    onClick = { viewModel.navigateTo(Screen.OrderDetail(order.id)) }
                                ) {
                                    Text("Track Order >", color = MediTeal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderDetailScreen(orderId: String, viewModel: MediCareViewModel) {
    val orders by viewModel.orders.collectAsState()
    val order = orders.find { it.id == orderId }

    if (order == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Order details not found")
        }
        return
    }

    val steps = listOf(
        OrderStatus.PLACED to "Order Placed",
        OrderStatus.PRESCRIPTION_VERIFIED to "Pharmacist Verified",
        OrderStatus.PACKING to "Dispensed & Packed",
        OrderStatus.OUT_FOR_DELIVERY to "Out for Delivery",
        OrderStatus.DELIVERED to "Delivered"
    )

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Order ${order.orderNumber}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Live Status Tracking Timeline
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Delivery Tracking", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            OrderStatusBadge(order.status)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Timeline steps
                        steps.forEachIndexed { index, step ->
                            val isCompleted = order.status.stepIndex >= step.first.stepIndex
                            val isCurrent = order.status == step.first

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isCompleted) MediTeal else Color(0xFFE2E8F0)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isCompleted) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    } else {
                                        Text("${index + 1}", fontSize = 11.sp, color = Color(0xFF64748B))
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        step.second,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp,
                                        color = if (isCurrent) MediTealDark else if (isCompleted) Color(0xFF0F172A) else Color(0xFF94A3B8)
                                    )
                                }
                            }
                            if (index < steps.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .padding(start = 11.dp)
                                        .width(2.dp)
                                        .height(16.dp)
                                        .background(if (order.status.stepIndex > step.first.stepIndex) MediTeal else Color(0xFFE2E8F0))
                                )
                            }
                        }
                    }
                }
            }

            // Courier information
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DeliveryDining, contentDescription = null, tint = MediBlue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Assigned Medical Courier", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(order.deliveryPersonnelName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(order.deliveryPersonnelPhone, fontSize = 11.sp, color = Color(0xFF64748B))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Address: ${order.deliveryAddress}", fontSize = 11.sp, color = Color(0xFF475569))
                    }
                }
            }

            // Prescribed medications
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Prescription Items", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        order.items.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("${item.medicineName} (${item.strength})", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Text("Qty: ${item.quantity} units", fontSize = 11.sp, color = Color(0xFF64748B))
                                }
                                Text("$${String.format("%.2f", item.unitPrice * item.quantity)}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
