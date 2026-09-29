package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Medicine
import com.example.data.model.OrderStatus
import com.example.data.model.PrescriptionStatus
import com.example.data.model.UserRole
import com.example.ui.Screen
import com.example.ui.theme.MediAmber
import com.example.ui.theme.MediAmberLight
import com.example.ui.theme.MediBlue
import com.example.ui.theme.MediBlueContainer
import com.example.ui.theme.MediBorder
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
fun MediCareTopBar(
    currentRole: UserRole,
    cartCount: Int,
    onRoleClick: () -> Unit,
    onCartClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand logo & title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onRoleClick() }
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MediTeal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalPharmacy,
                            contentDescription = "MediCare Logo",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Medi",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MediTealDark
                                )
                            )
                            Text(
                                text = "Care",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MediBlue
                                )
                            )
                        }
                        Text(
                            text = "Licensed Medical & Pharmacy",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = Color(0xFF64748B)
                            )
                        )
                    }
                }

                // Right actions: Search, Cart (if customer), and Role Switcher chip
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier.testTag("search_icon_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search medicines",
                            tint = Color(0xFF334155)
                        )
                    }

                    if (currentRole == UserRole.CUSTOMER) {
                        IconButton(
                            onClick = onCartClick,
                            modifier = Modifier.testTag("cart_icon_button")
                        ) {
                            BadgedBox(
                                badge = {
                                    if (cartCount > 0) {
                                        Badge(
                                            containerColor = MediTeal,
                                            contentColor = Color.White
                                        ) {
                                            Text(cartCount.toString(), fontSize = 10.sp)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Shopping Cart",
                                    tint = Color(0xFF334155)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Role pill button
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = when (currentRole) {
                            UserRole.CUSTOMER -> MediTealContainer
                            UserRole.PHARMACIST -> Color(0xFFEDE9FE)
                            UserRole.STORE_STAFF -> Color(0xFFFEF3C7)
                            UserRole.DELIVERY_STAFF -> MediBlueContainer
                            UserRole.ADMIN -> Color(0xFFFEE2E2)
                        },
                        modifier = Modifier
                            .clickable { onRoleClick() }
                            .testTag("role_switcher_chip")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = currentRole.displayName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = when (currentRole) {
                                        UserRole.CUSTOMER -> OnMediTealContainer
                                        UserRole.PHARMACIST -> Color(0xFF5B21B6)
                                        UserRole.STORE_STAFF -> Color(0xFF92400E)
                                        UserRole.DELIVERY_STAFF -> OnMediBlueContainer
                                        UserRole.ADMIN -> Color(0xFF991B1B)
                                    }
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = "Switch role",
                                modifier = Modifier.size(14.dp),
                                tint = Color(0xFF475569)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineCard(
    medicine: Medicine,
    onMedicineClick: () -> Unit,
    onAddToCart: () -> Unit,
    isWishlisted: Boolean = false,
    onToggleWishlist: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMedicineClick() }
            .testTag("medicine_card_${medicine.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header: Rx badge, stock status & Wishlist button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (medicine.prescriptionRequired) {
                        RxBadge()
                    } else {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MediEmeraldContainer
                        ) {
                            Text(
                                text = "OTC",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MediEmeraldDark,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    if (medicine.badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFFEF3C7)
                        ) {
                            Text(
                                text = medicine.badge,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF92400E),
                                    fontSize = 9.sp
                                ),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onToggleWishlist,
                    modifier = Modifier.size(28.dp).testTag("wishlist_btn_${medicine.id}")
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) MediRxRed else Color(0xFF94A3B8),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Medicine visual placeholder banner with dosage form icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        when (medicine.dosageForm) {
                            "Capsule" -> Color(0xFFE0F2FE)
                            "Tablet" -> Color(0xFFCCFBF1)
                            "Inhaler" -> Color(0xFFEDE9FE)
                            else -> Color(0xFFF1F5F9)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = when {
                            medicine.dosageForm.contains("Tablet", true) -> Icons.Default.Medication
                            medicine.dosageForm.contains("Device", true) -> Icons.Default.MedicalServices
                            else -> Icons.Default.LocalPharmacy
                        },
                        contentDescription = medicine.dosageForm,
                        tint = MediTealDark,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = medicine.dosageForm.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            color = Color(0xFF475569)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Medicine Name and Strength
            Text(
                text = medicine.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${medicine.genericName} • ${medicine.strength}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF64748B),
                    fontSize = 11.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Pack size
            Text(
                text = medicine.packSize,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF94A3B8),
                    fontSize = 10.sp
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "${medicine.rating} (${medicine.reviewCount})",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        color = Color(0xFF475569)
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Price and Add to cart button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$${String.format("%.2f", medicine.price)}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MediTealDark
                            )
                        )
                        if (medicine.originalPrice > medicine.price) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$${String.format("%.2f", medicine.originalPrice)}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF94A3B8),
                                    textDecoration = TextDecoration.LineThrough
                                )
                            )
                        }
                    }
                    Text(
                        text = if (medicine.inStock) "In Stock (${medicine.stockCount})" else "Out of Stock",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (medicine.inStock) MediEmeraldDark else MediRxRed
                        )
                    )
                }

                Button(
                    onClick = onAddToCart,
                    enabled = medicine.inStock,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MediTeal,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("add_to_cart_${medicine.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun RxBadge() {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MediRxRedLight
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = "Rx",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = MediRxRed,
                    fontSize = 10.sp
                )
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Required",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MediRxRed,
                    fontSize = 9.sp
                )
            )
        }
    }
}

@Composable
fun OrderStatusBadge(status: OrderStatus) {
    val (bgColor, textColor) = when (status) {
        OrderStatus.PLACED -> Color(0xFFE0F2FE) to Color(0xFF0369A1)
        OrderStatus.PRESCRIPTION_VERIFIED -> Color(0xFFEDE9FE) to Color(0xFF5B21B6)
        OrderStatus.PACKING -> Color(0xFFFEF3C7) to Color(0xFF92400E)
        OrderStatus.OUT_FOR_DELIVERY -> Color(0xFFCCFBF1) to Color(0xFF0F766E)
        OrderStatus.DELIVERED -> MediEmeraldContainer to MediEmeraldDark
        OrderStatus.CANCELLED -> MediRxRedLight to MediRxRed
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor
    ) {
        Text(
            text = status.title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 11.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun PrescriptionStatusBadge(status: PrescriptionStatus) {
    val (bgColor, textColor) = when (status) {
        PrescriptionStatus.PENDING_REVIEW -> MediAmberLight to MediAmber
        PrescriptionStatus.APPROVED -> MediEmeraldContainer to MediEmeraldDark
        PrescriptionStatus.REJECTED -> MediRxRedLight to MediRxRed
        PrescriptionStatus.DISPENSED -> MediBlueContainer to Color(0xFF0369A1)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor
    ) {
        Text(
            text = status.label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 11.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun CustomerBottomNavBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    cartCount: Int
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 6.dp
    ) {
        NavigationBarItem(
            selected = currentScreen is Screen.Home,
            onClick = { onNavigate(Screen.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MediTealDark,
                selectedTextColor = MediTealDark,
                indicatorColor = MediTealContainer
            ),
            modifier = Modifier.testTag("nav_home")
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Medicines || currentScreen is Screen.Categories,
            onClick = { onNavigate(Screen.Medicines) },
            icon = { Icon(Icons.Default.Medication, contentDescription = "Medicines") },
            label = { Text("Medicines", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MediTealDark,
                selectedTextColor = MediTealDark,
                indicatorColor = MediTealContainer
            ),
            modifier = Modifier.testTag("nav_medicines")
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Cart,
            onClick = { onNavigate(Screen.Cart) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartCount > 0) {
                            Badge(containerColor = MediTeal, contentColor = Color.White) {
                                Text(cartCount.toString(), fontSize = 10.sp)
                            }
                        }
                    }
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
            },
            label = { Text("Cart", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MediTealDark,
                selectedTextColor = MediTealDark,
                indicatorColor = MediTealContainer
            ),
            modifier = Modifier.testTag("nav_cart")
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Prescriptions || currentScreen is Screen.UploadPrescription,
            onClick = { onNavigate(Screen.Prescriptions) },
            icon = { Icon(Icons.Outlined.Description, contentDescription = "Prescriptions") },
            label = { Text("Rx", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MediTealDark,
                selectedTextColor = MediTealDark,
                indicatorColor = MediTealContainer
            ),
            modifier = Modifier.testTag("nav_prescriptions")
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Orders,
            onClick = { onNavigate(Screen.Orders) },
            icon = { Icon(Icons.Default.ReceiptLong, contentDescription = "Orders") },
            label = { Text("Orders", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MediTealDark,
                selectedTextColor = MediTealDark,
                indicatorColor = MediTealContainer
            ),
            modifier = Modifier.testTag("nav_orders")
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Profile || currentScreen is Screen.Addresses || currentScreen is Screen.Wishlist,
            onClick = { onNavigate(Screen.Profile) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Account") },
            label = { Text("Account", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MediTealDark,
                selectedTextColor = MediTealDark,
                indicatorColor = MediTealContainer
            ),
            modifier = Modifier.testTag("nav_account")
        )
    }
}

@Composable
fun RoleSwitcherDialog(
    currentRole: UserRole,
    onRoleSelected: (UserRole) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = null,
                    tint = MediTeal
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Switch MediCare Workspace", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Select any of the 5 dedicated operational dashboards:",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B))
                )
                Spacer(modifier = Modifier.height(4.dp))

                UserRole.values().forEach { role ->
                    val isSelected = currentRole == role
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MediTealContainer else Color(0xFFF8FAFC),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onRoleSelected(role)
                                onDismiss()
                            }
                            .border(
                                width = 1.dp,
                                color = if (isSelected) MediTeal else MediBorder,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .testTag("select_role_${role.name}")
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    onRoleSelected(role)
                                    onDismiss()
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = role.displayName,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) OnMediTealContainer else Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = when (role) {
                                        UserRole.CUSTOMER -> "Shop medicines, upload Rx, track orders, manage addresses"
                                        UserRole.PHARMACIST -> "Review & verify doctor prescriptions, drug interaction checks"
                                        UserRole.STORE_STAFF -> "Inventory batch counts, low-stock alerts, pack orders"
                                        UserRole.DELIVERY_STAFF -> "Active delivery runs, patient address navigation, mark delivered"
                                        UserRole.ADMIN -> "Revenue analytics, catalog control, audit logs, store settings"
                                    },
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = MediTeal)
            }
        }
    )
}
