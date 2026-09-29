package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.BabyChangingStation
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Medicine
import com.example.data.model.UserRole
import com.example.ui.MediCareViewModel
import com.example.ui.Screen
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

// =========================================================================
// 1. COMPREHENSIVE HEADER & NAVIGATION
// =========================================================================

@Composable
fun HomeHeader(
    cartCount: Int,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenAbout: () -> Unit,
    onOpenContact: () -> Unit,
    currentRole: UserRole,
    onOpenRoleSwitcher: () -> Unit
) {
    var isMobileMenuOpen by remember { mutableStateOf(false) }

    Surface(
        color = Color.White,
        shadowElevation = 3.dp,
        modifier = Modifier.fillMaxWidth().testTag("home_header")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Main Navigation Bar Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Mobile Menu Button + Brand
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { isMobileMenuOpen = true },
                        modifier = Modifier.size(36.dp).testTag("mobile_menu_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open navigation menu",
                            tint = Color(0xFF1E293B)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Brand Logo
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onNavigate(Screen.Home) }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MediTeal),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalPharmacy,
                                contentDescription = "MediCare",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Medi",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = MediTealDark
                                )
                                Text(
                                    text = "Care",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = MediBlue
                                )
                            }
                            Text(
                                text = "Online Pharmacy",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }

                // Desktop / Tablet Quick Nav Links (Horizontal)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Account / Login Button
                    IconButton(
                        onClick = { onNavigate(Screen.Profile) },
                        modifier = Modifier.size(36.dp).testTag("header_account_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Account or Login",
                            tint = Color(0xFF334155),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Cart Icon with Badge
                    IconButton(
                        onClick = { onNavigate(Screen.Cart) },
                        modifier = Modifier.size(36.dp).testTag("header_cart_btn")
                    ) {
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) {
                                    Badge(
                                        containerColor = MediTeal,
                                        contentColor = Color.White
                                    ) {
                                        Text(cartCount.toString(), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Shopping Cart with $cartCount items",
                                tint = Color(0xFF334155),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Role Switcher Chip
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MediTealContainer,
                        modifier = Modifier
                            .clickable { onOpenRoleSwitcher() }
                            .padding(start = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = currentRole.displayName,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnMediTealContainer
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = "Switch dashboard",
                                modifier = Modifier.size(12.dp),
                                tint = OnMediTealContainer
                            )
                        }
                    }
                }
            }

            // Quick Nav Links Row: Home | Medicines | Categories | About | Contact
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8FAFC))
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NavTextLink("Home", true) { onNavigate(Screen.Home) }
                NavTextLink("Medicines", false) { onNavigate(Screen.Medicines) }
                NavTextLink("Categories", false) { onNavigate(Screen.Categories) }
                NavTextLink("About Us", false) { onOpenAbout() }
                NavTextLink("Contact", false) { onOpenContact() }
            }

            // Large Medicine Search Bar
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = {
                        Text(
                            "Search medicines, active salts, brands, healthcare products...",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MediTeal,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search",
                                    tint = Color(0xFF94A3B8),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("large_medicine_search_bar")
                )
            }
        }
    }

    // Mobile Hamburger Navigation Drawer / BottomSheet
    if (isMobileMenuOpen) {
        AlertDialog(
            onDismissRequest = { isMobileMenuOpen = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(28.dp).clip(RoundedCornerShape(6.dp)).background(MediTeal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.LocalPharmacy, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MediCare Navigation", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DrawerNavItem("Home", Icons.Default.LocalPharmacy) {
                        isMobileMenuOpen = false
                        onNavigate(Screen.Home)
                    }
                    DrawerNavItem("Medicines Catalog", Icons.Default.Medication) {
                        isMobileMenuOpen = false
                        onNavigate(Screen.Medicines)
                    }
                    DrawerNavItem("Healthcare Categories", Icons.Default.MedicalServices) {
                        isMobileMenuOpen = false
                        onNavigate(Screen.Categories)
                    }
                    DrawerNavItem("Prescription Services", Icons.Default.FileUpload) {
                        isMobileMenuOpen = false
                        onNavigate(Screen.Prescriptions)
                    }
                    DrawerNavItem("My Orders", Icons.Default.ReceiptLong) {
                        isMobileMenuOpen = false
                        onNavigate(Screen.Orders)
                    }
                    DrawerNavItem("Patient Profile & Account", Icons.Default.Person) {
                        isMobileMenuOpen = false
                        onNavigate(Screen.Profile)
                    }
                    HorizontalDivider(color = MediBorder)
                    DrawerNavItem("About MediCare", Icons.Default.Info) {
                        isMobileMenuOpen = false
                        onOpenAbout()
                    }
                    DrawerNavItem("Contact Support", Icons.Default.Phone) {
                        isMobileMenuOpen = false
                        onOpenContact()
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { isMobileMenuOpen = false }) {
                    Text("Close", color = MediTeal)
                }
            }
        )
    }
}

@Composable
private fun NavTextLink(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
        color = if (isSelected) MediTealDark else Color(0xFF475569),
        modifier = Modifier.clickable { onClick() }
    )
}

@Composable
private fun DrawerNavItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MediTeal, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
    }
}

// =========================================================================
// 2. HERO SECTION
// =========================================================================

@Composable
fun HeroSection(
    onShopMedicines: () -> Unit,
    onUploadPrescription: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MediTealDark),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("hero_section")
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Trust & Service Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x33FFFFFF)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Licensed Pharmacy", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x33FFFFFF)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.LocalShipping, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Express 2-Hr Delivery", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main heading
            Text(
                text = "Your Trusted Online Medical Store",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    lineHeight = 28.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Medicines, healthcare products and pharmacy services delivered to your doorstep.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFCCFBF1),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Professional Pharmacy Visual Card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HealthAndSafety,
                                contentDescription = null,
                                tint = MediTealDark,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("100% Authentic Pharma", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("Direct manufacturer distribution", color = Color(0xFF99F6E4), fontSize = 10.sp)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0x40FFFFFF)
                    ) {
                        Text(
                            text = "RPh Verified",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onShopMedicines,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MediTealDark
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).testTag("hero_shop_medicines_btn")
                ) {
                    Icon(Icons.Default.Medication, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Shop Medicines", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = onUploadPrescription,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).testTag("hero_upload_rx_btn")
                ) {
                    Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Upload Prescription", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }
    }
}

// =========================================================================
// 3. QUICK SERVICE CARDS (4 cards)
// =========================================================================

data class ServiceInfo(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun QuickServiceCards() {
    val services = listOf(
        ServiceInfo(
            title = "Genuine Products",
            description = "Authentic healthcare products from trusted sources.",
            icon = Icons.Default.Shield,
            color = MediTeal
        ),
        ServiceInfo(
            title = "Fast Delivery",
            description = "Convenient delivery to your selected address.",
            icon = Icons.Default.LocalShipping,
            color = MediBlue
        ),
        ServiceInfo(
            title = "Pharmacist Support",
            description = "Prescription orders reviewed by authorized pharmacy staff.",
            icon = Icons.Default.VerifiedUser,
            color = Color(0xFF7C3AED)
        ),
        ServiceInfo(
            title = "Secure Ordering",
            description = "Secure account, order and payment experience.",
            icon = Icons.Default.Lock,
            color = MediEmeraldDark
        )
    )

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ServiceCard(services[0], Modifier.weight(1f))
            ServiceCard(services[1], Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ServiceCard(services[2], Modifier.weight(1f))
            ServiceCard(services[3], Modifier.weight(1f))
        }
    }
}

@Composable
fun ServiceCard(info: ServiceInfo, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(info.color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = info.icon,
                    contentDescription = null,
                    tint = info.color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = info.title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = info.description,
                fontSize = 10.sp,
                color = Color(0xFF64748B),
                lineHeight = 14.sp
            )
        }
    }
}

// =========================================================================
// 4. HEALTHCARE CATEGORIES (8 cards)
// =========================================================================

data class HomeCategoryItem(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val filterTarget: String
)

val homeCategories = listOf(
    HomeCategoryItem("Medicines", "Browse medicines & Rx drugs", Icons.Default.Medication, "Antibiotics"),
    HomeCategoryItem("Vitamins & Supplements", "Immune boosters & vitamins", Icons.Default.HealthAndSafety, "Vitamins & Supplements"),
    HomeCategoryItem("First Aid", "Wound care, kits & ointments", Icons.Default.LocalHospital, "First Aid"),
    HomeCategoryItem("Baby Care", "Pediatric drops & infant care", Icons.Default.BabyChangingStation, "Baby Care"),
    HomeCategoryItem("Personal Care", "Antiseptic & hand wellness", Icons.Default.Spa, "Personal Care"),
    HomeCategoryItem("Diabetes Care", "Glucose meters & oral meds", Icons.Default.MedicalInformation, "Diabetes Care"),
    HomeCategoryItem("Medical Devices", "Monitors, oximeters & tools", Icons.Default.MedicalServices, "Medical Devices"),
    HomeCategoryItem("Skin Care", "Derm-tested barrier creams", Icons.Default.Face, "Skin Care")
)

@Composable
fun HealthcareCategoriesSection(
    onSelectCategory: (String) -> Unit,
    onViewAll: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Shop by Category",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Explore trusted pharmaceutical & daily wellness ranges",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
                )
            }
            TextButton(onClick = onViewAll) {
                Text("View All >", color = MediTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 2x4 grid layout
        for (i in 0 until homeCategories.size step 2) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryGridCard(homeCategories[i], onSelectCategory, Modifier.weight(1f))
                if (i + 1 < homeCategories.size) {
                    CategoryGridCard(homeCategories[i + 1], onSelectCategory, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun CategoryGridCard(
    cat: HomeCategoryItem,
    onSelectCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .clickable { onSelectCategory(cat.filterTarget) }
            .testTag("category_card_${cat.name}")
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(MediTealContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = cat.icon,
                    contentDescription = cat.name,
                    tint = MediTealDark,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cat.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color(0xFF0F172A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = cat.description,
                    fontSize = 9.sp,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// =========================================================================
// 5. PRESCRIPTION UPLOAD SECTION
// =========================================================================

@Composable
fun PrescriptionBannerSection(
    onUploadPrescription: () -> Unit,
    onViewPrescriptions: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("prescription_banner_section")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFEF2F2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FileUpload,
                        contentDescription = "Upload",
                        tint = MediRxRed,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Have a Prescription?",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        RxBadge()
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Upload your prescription and let our pharmacy team review it before your eligible order is processed.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF64748B),
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onUploadPrescription,
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).testTag("btn_upload_prescription_cta")
                ) {
                    Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Upload Prescription", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onViewPrescriptions,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MediTeal),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).testTag("btn_view_prescriptions_cta")
                ) {
                    Text("View My Prescriptions", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Mandatory Pharmacy Regulatory Notice
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF2F2),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Notice",
                        tint = MediRxRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Prescription medicines may require verification by authorized pharmacy staff before fulfillment.",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF991B1B)
                    )
                }
            }
        }
    }
}

// =========================================================================
// 6. POPULAR PRODUCTS (Carousel)
// =========================================================================

@Composable
fun PopularProductsSection(
    medicines: List<Medicine>,
    onMedicineClick: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onViewAll: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Popular Products",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Most trusted and frequently requested health items",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
                )
            }
            TextButton(onClick = onViewAll) {
                Text("View All Products", color = MediTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal Carousel
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(medicines) { med ->
                PopularProductCard(
                    medicine = med,
                    onClick = { onMedicineClick(med.id) },
                    onAddToCart = { onAddToCart(med.id) }
                )
            }
        }
    }
}

@Composable
fun PopularProductCard(
    medicine: Medicine,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(180.dp)
            .clickable { onClick() }
            .testTag("popular_product_${medicine.id}")
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Dosage visual
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Medication,
                    contentDescription = null,
                    tint = MediTealDark,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = medicine.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = medicine.strength,
                fontSize = 10.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("${medicine.rating}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(6.dp))
                Text("✓ In Stock", fontSize = 9.sp, color = MediEmeraldDark, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${String.format("%.2f", medicine.price)}",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    color = MediTealDark
                )

                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text("+ Add", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// =========================================================================
// 7. WHY CHOOSE MEDICARE (4 Feature Blocks)
// =========================================================================

@Composable
fun WhyChooseMediCareSection() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("why_choose_medicare_section")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Why Choose MediCare?",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Patient-centric pharmacy care with rigorous pharmaceutical standards.",
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            FeatureBlock(
                number = "01",
                title = "Trusted Products",
                description = "Healthcare products managed through the pharmacy catalog from certified pharma manufacturers."
            )
            HorizontalDivider(color = MediBorder, modifier = Modifier.padding(vertical = 8.dp))

            FeatureBlock(
                number = "02",
                title = "Professional Review",
                description = "Prescription orders can be reviewed by authorized pharmacy personnel before fulfillment."
            )
            HorizontalDivider(color = MediBorder, modifier = Modifier.padding(vertical = 8.dp))

            FeatureBlock(
                number = "03",
                title = "Convenient Ordering",
                description = "Search, order and track your purchases online seamlessly from home."
            )
            HorizontalDivider(color = MediBorder, modifier = Modifier.padding(vertical = 8.dp))

            FeatureBlock(
                number = "04",
                title = "Reliable Delivery",
                description = "Track your order from confirmation through delivery in temperature-safe packaging."
            )
        }
    }
}

@Composable
private fun FeatureBlock(number: String, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MediTealContainer
        ) {
            Text(
                text = number,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                color = OnMediTealContainer,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))
            Spacer(modifier = Modifier.height(2.dp))
            Text(description, fontSize = 11.sp, color = Color(0xFF64748B), lineHeight = 16.sp)
        }
    }
}

// =========================================================================
// 8. HOW IT WORKS (4 Steps + Prescription Workflow)
// =========================================================================

@Composable
fun HowItWorksSection() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("how_it_works_section")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "How It Works",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "A simple, compliant healthcare ordering journey",
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Standard Ordering Steps (1 to 4)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                StepPill("1. Search", "Find product", Modifier.weight(1f))
                StepPill("2. Add to Cart", "Select quantity", Modifier.weight(1f))
                StepPill("3. Checkout", "Address & pay", Modifier.weight(1f))
                StepPill("4. Receive", "Track delivery", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Visual Prescription Flow Diagram
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8FAFC),
                border = androidx.compose.foundation.BorderStroke(1.dp, MediBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RxBadge()
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Prescription Medication Flow", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val rxSteps = listOf(
                        "Search Medicine",
                        "Prescription Upload",
                        "Pharmacist Review",
                        "Order Processing",
                        "Safe Delivery"
                    )

                    rxSteps.forEachIndexed { index, step ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(MediTeal),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${index + 1}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(step, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
                        }
                        if (index < rxSteps.size - 1) {
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.padding(start = 4.dp).size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepPill(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF1F5F9),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 10.sp, textAlign = TextAlign.Center)
            Text(subtitle, fontSize = 8.sp, color = Color(0xFF64748B), textAlign = TextAlign.Center)
        }
    }
}

// =========================================================================
// 9. HEALTHCARE ESSENTIALS & EQUIPMENT SECTION
// =========================================================================

data class EquipmentItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)

@Composable
fun HealthEquipmentSection(
    onExploreEquipment: () -> Unit
) {
    val items = listOf(
        EquipmentItem("Blood Pressure Monitors", "Digital upper arm monitors", Icons.Default.HealthAndSafety),
        EquipmentItem("Glucose Meters", "Instant diabetes diagnostic strips", Icons.Default.MedicalInformation),
        EquipmentItem("Thermometers", "Infrared ear & forehead probes", Icons.Default.MedicalServices),
        EquipmentItem("First Aid Kits", "120-pc trauma & wound emergency sets", Icons.Default.LocalHospital),
        EquipmentItem("Nebulizers", "Compressor aerosol mist machines", Icons.Default.Medication),
        EquipmentItem("Medical Accessories", "Oximeters, cuffs & sanitizers", Icons.Default.Spa)
    )

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Healthcare Essentials",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Medical-grade equipment for home diagnostics",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 3x2 cards
        for (i in 0 until items.size step 2) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EquipmentCard(items[i], onExploreEquipment, Modifier.weight(1f))
                if (i + 1 < items.size) {
                    EquipmentCard(items[i + 1], onExploreEquipment, Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onExploreEquipment,
            colors = ButtonDefaults.buttonColors(containerColor = MediBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().testTag("explore_healthcare_equipment_btn")
        ) {
            Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Explore Healthcare Equipment", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun EquipmentCard(item: EquipmentItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(MediBlueContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(item.icon, contentDescription = null, tint = Color(0xFF0369A1), modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(item.title, fontWeight = FontWeight.Bold, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(item.subtitle, fontSize = 9.sp, color = Color(0xFF64748B), maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

// =========================================================================
// 10. PROMOTIONAL BANNER
// =========================================================================

@Composable
fun PromotionalBanner(onShopNow: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("promotional_banner")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Take Care of Your Health",
                fontWeight = FontWeight.Black,
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Explore everyday healthcare essentials from MediCare with fast home delivery.",
                fontSize = 11.sp,
                color = Color(0xFF94A3B8)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onShopNow,
                colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text("Shop Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
            }
        }
    }
}

// =========================================================================
// 11. CUSTOMER ACCOUNT CTA
// =========================================================================

@Composable
fun CustomerAccountCTA(
    onCreateAccount: () -> Unit,
    onLogin: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("customer_account_cta")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MediTealContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = MediTealDark, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Manage Your Healthcare Orders Easily",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            val bulletPoints = listOf(
                "Track orders in real time",
                "Save multiple delivery addresses",
                "View and manage prescriptions",
                "Access complete order history",
                "Manage clinical patient profile & allergies"
            )

            bulletPoints.forEach { pt ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MediTeal, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(pt, fontSize = 11.sp, color = Color(0xFF334155))
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onCreateAccount,
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).testTag("btn_create_account_cta")
                ) {
                    Text("Create Account", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onLogin,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).testTag("btn_login_cta")
                ) {
                    Text("Login", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// =========================================================================
// 12. NEWSLETTER / UPDATES
// =========================================================================

@Composable
fun NewsletterSection(
    email: String,
    onEmailChange: (String) -> Unit,
    isSubscribed: Boolean,
    onSubscribe: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("newsletter_section")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, contentDescription = null, tint = MediTeal, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Stay Updated", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Receive updates about healthcare products, offers and MediCare services.",
                fontSize = 11.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (isSubscribed) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MediEmeraldContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MediEmeraldDark, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "You are subscribed to MediCare health updates!",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MediEmeraldDark
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        placeholder = { Text("Enter your email address", fontSize = 11.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(46.dp).testTag("newsletter_input")
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onSubscribe(email) },
                        colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(46.dp).testTag("newsletter_subscribe_btn")
                    ) {
                        Text("Subscribe", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// =========================================================================
// 13. COMPREHENSIVE FOOTER
// =========================================================================

@Composable
fun HomeFooter(
    onNavigate: (Screen) -> Unit,
    onOpenAbout: () -> Unit,
    onOpenContact: () -> Unit
) {
    Surface(
        color = Color(0xFF0F172A),
        modifier = Modifier.fillMaxWidth().testTag("home_footer")
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Brand
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MediTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LocalPharmacy, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("MediCare", fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your trusted online medical store for medicines and healthcare essentials.",
                fontSize = 11.sp,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFF334155))
            Spacer(modifier = Modifier.height(14.dp))

            // Quick Links
            Text("Quick Links", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            FooterLink("Home") { onNavigate(Screen.Home) }
            FooterLink("Medicines Catalog") { onNavigate(Screen.Medicines) }
            FooterLink("Healthcare Categories") { onNavigate(Screen.Categories) }
            FooterLink("About Us") { onOpenAbout() }
            FooterLink("Contact Support") { onOpenContact() }

            Spacer(modifier = Modifier.height(12.dp))

            // Customer Support
            Text("Customer Support", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            FooterLink("Help Center & FAQ") { onOpenContact() }
            FooterLink("Delivery Information (2-Hr Express)") { onNavigate(Screen.Orders) }
            FooterLink("Order Tracking") { onNavigate(Screen.Orders) }
            FooterLink("Returns & Refunds Policy") { onOpenAbout() }

            Spacer(modifier = Modifier.height(12.dp))

            // Pharmacy Services
            Text("Pharmacy Services", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            FooterLink("Prescription Upload") { onNavigate(Screen.UploadPrescription) }
            FooterLink("Prescription Information") { onNavigate(Screen.Prescriptions) }
            FooterLink("Licensed Pharmacist Support") { onOpenAbout() }

            Spacer(modifier = Modifier.height(12.dp))

            // Legal & Compliance
            Text("Regulatory & Legal", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Text("• Prescription Policy: Valid physician script mandatory for Schedule items", fontSize = 10.sp, color = Color(0xFF64748B))
            Text("• Privacy & HIPAA: Patient confidentiality strictly protected", fontSize = 10.sp, color = Color(0xFF64748B))

            Spacer(modifier = Modifier.height(12.dp))

            // Contact placeholder
            Text("Contact Information", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Phone: +1 (800) 555-MEDCARE", fontSize = 10.sp, color = Color(0xFF94A3B8))
            Text("Email: support@medicare-pharmacy.example", fontSize = 10.sp, color = Color(0xFF94A3B8))
            Text("Address: 500 Healthcare Boulevard, Suite 100, Springfield, OR", fontSize = 10.sp, color = Color(0xFF94A3B8))

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFF334155))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "© 2026 MediCare Inc. All rights reserved. Demo pharmacy application for evaluation purposes.",
                fontSize = 9.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FooterLink(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        fontSize = 11.sp,
        color = Color(0xFF5EEAD4),
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 3.dp)
    )
}

// =========================================================================
// 14. LOADING & ERROR STATES
// =========================================================================

@Composable
fun SkeletonProductCard() {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().height(160.dp).padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(modifier = Modifier.fillMaxWidth(0.3f).height(14.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFFE2E8F0)))
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth(0.8f).height(18.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFFE2E8F0)))
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.fillMaxWidth(0.5f).height(12.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFFF1F5F9)))
            Spacer(modifier = Modifier.height(14.dp))
            Box(modifier = Modifier.fillMaxWidth().height(36.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFF1F5F9)))
        }
    }
}

@Composable
fun ErrorRetryCard(errorMessage: String, onRetry: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = MediRxRed, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text("Unable to load products", fontWeight = FontWeight.Bold, color = Color(0xFF991B1B))
            Text(errorMessage, fontSize = 11.sp, color = Color(0xFF7F1D1D))
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = MediTeal)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Retry")
            }
        }
    }
}

// =========================================================================
// 15. ABOUT US & CONTACT MODALS
// =========================================================================

@Composable
fun AboutUsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = MediTeal)
                Spacer(modifier = Modifier.width(8.dp))
                Text("About MediCare Pharmacy", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "MediCare is a licensed digital pharmacy and healthcare store committed to patient safety, authentic medication dispensing, and fast door-to-door delivery.",
                    fontSize = 12.sp,
                    color = Color(0xFF334155),
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Key Pharmacy Commitments:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("✓ 100% genuine pharmaceutical distribution from certified labs", fontSize = 11.sp, color = Color(0xFF475569))
                Text("✓ Mandatory licensed pharmacist review for all prescription (Rx) orders", fontSize = 11.sp, color = Color(0xFF475569))
                Text("✓ Temperature-controlled insulated courier containers", fontSize = 11.sp, color = Color(0xFF475569))
                Text("✓ Strict privacy and electronic medical record protection", fontSize = 11.sp, color = Color(0xFF475569))
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = MediTeal)) {
                Text("Close")
            }
        }
    )
}

@Composable
fun ContactUsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, contentDescription = null, tint = MediBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Customer & Pharmacy Support", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Need assistance with an order, prescription verification, or medication query? Our certified pharmacy team is here to assist.",
                    fontSize = 12.sp,
                    color = Color(0xFF334155)
                )
                HorizontalDivider(color = MediBorder)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = MediTeal, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Helpline: +1 (800) 555-MEDCARE", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = MediBlue, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Email: support@medicare-pharmacy.example", fontSize = 12.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MediRxRed, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Central Pharmacy: 500 Healthcare Blvd, Springfield, OR", fontSize = 11.sp)
                }
                Text("On-Duty Pharmacist Hours: Mon-Sun, 8:00 AM – 10:00 PM PST", fontSize = 10.sp, color = Color(0xFF64748B))
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = MediBlue)) {
                Text("Done")
            }
        }
    )
}
