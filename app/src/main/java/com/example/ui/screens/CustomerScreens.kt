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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AddressEntity
import com.example.data.model.Medicine
import com.example.data.model.OrderStatus
import com.example.data.model.PrescriptionStatus
import com.example.ui.MediCareViewModel
import com.example.ui.Screen
import com.example.ui.SortOption
import com.example.ui.components.MedicineCard
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
import com.example.ui.theme.OnMediTealContainer

val categoriesList = listOf(
    "All",
    "Antibiotics",
    "Pain & Fever",
    "Diabetes Care",
    "Cardiovascular",
    "Allergy & Respiratory",
    "Digestive Health",
    "Vitamins & Supplements",
    "Medical Devices"
)

@Composable
fun CustomerHomeScreen(
    viewModel: MediCareViewModel
) {
    val medicines by viewModel.rawMedicines.collectAsState()
    val wishlist by viewModel.wishlistMedicines.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentRole by viewModel.currentRole.collectAsState()
    val newsletterEmail by viewModel.newsletterEmail.collectAsState()
    val isSubscribed by viewModel.isSubscribedNewsletter.collectAsState()
    val isLoading by viewModel.isSimulatedLoading.collectAsState()
    val simulateError by viewModel.simulateError.collectAsState()

    var showAboutDialog by remember { mutableStateOf(false) }
    var showContactDialog by remember { mutableStateOf(false) }
    var showRoleModal by remember { mutableStateOf(false) }

    val featuredMedicines = medicines.take(8)
    val popularMedicines = medicines.filter { it.rating >= 4.8 }.take(6)
    val totalCartCount = cartItems.sumOf { it.quantity }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MediClinicalBg).testTag("customer_home_scroll"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // 1. Header / Navigation
        item {
            HomeHeader(
                cartCount = totalCartCount,
                searchQuery = searchQuery,
                onSearchChange = { q ->
                    viewModel.searchQuery.value = q
                    if (q.isNotEmpty()) {
                        viewModel.navigateTo(Screen.Medicines)
                    }
                },
                onNavigate = { viewModel.navigateTo(it) },
                onOpenAbout = { showAboutDialog = true },
                onOpenContact = { showContactDialog = true },
                currentRole = currentRole,
                onOpenRoleSwitcher = { showRoleModal = true }
            )
        }

        // Error State (if any)
        if (simulateError != null) {
            item {
                ErrorRetryCard(
                    errorMessage = simulateError ?: "Unable to load products. Please check network connection.",
                    onRetry = { viewModel.triggerRefresh() }
                )
            }
        }

        // Loading Skeleton State (if loading)
        if (isLoading) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Refreshing MediCare Catalog...", fontWeight = FontWeight.Bold, color = MediTealDark)
                    Spacer(modifier = Modifier.height(10.dp))
                    SkeletonProductCard()
                    SkeletonProductCard()
                }
            }
        } else {
            // 2. Hero Section
            item {
                HeroSection(
                    onShopMedicines = { viewModel.navigateTo(Screen.Medicines) },
                    onUploadPrescription = { viewModel.navigateTo(Screen.UploadPrescription) }
                )
            }

            // 3. Quick Service Cards
            item {
                QuickServiceCards()
            }

            // 4. Healthcare Categories ("Shop by Category")
            item {
                HealthcareCategoriesSection(
                    onSelectCategory = { cat ->
                        viewModel.selectedCategory.value = cat
                        viewModel.navigateTo(Screen.Medicines)
                    },
                    onViewAll = { viewModel.navigateTo(Screen.Categories) }
                )
            }

            // 5. Prescription Upload Section
            item {
                PrescriptionBannerSection(
                    onUploadPrescription = { viewModel.navigateTo(Screen.UploadPrescription) },
                    onViewPrescriptions = { viewModel.navigateTo(Screen.Prescriptions) }
                )
            }

            // 6. Featured Products Section (6-8 items with both Add to Cart & View Details)
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Featured Healthcare Products",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Clinically validated pharmaceuticals & daily wellness essentials",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
                            )
                        }
                        TextButton(onClick = { viewModel.navigateTo(Screen.Medicines) }) {
                            Text("See All >", color = MediTeal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (featuredMedicines.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Medication, contentDescription = null, tint = Color(0xFFCBD5E1), modifier = Modifier.size(40.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No featured products available", fontWeight = FontWeight.Bold)
                            Text("Products will appear here when they are added to the catalog.", fontSize = 11.sp, color = Color(0xFF64748B), textAlign = TextAlign.Center)
                        }
                    }
                }
            } else {
                items(featuredMedicines) { med ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        FeaturedHealthcareProductCard(
                            medicine = med,
                            onViewDetails = { viewModel.navigateTo(Screen.MedicineDetail(med.id)) },
                            onAddToCart = { viewModel.addToCart(med.id) },
                            isWishlisted = wishlist.any { it.id == med.id },
                            onToggleWishlist = { viewModel.toggleWishlist(med.id) }
                        )
                    }
                }
            }

            // 7. Popular Products Section (Horizontal carousel)
            item {
                PopularProductsSection(
                    medicines = popularMedicines,
                    onMedicineClick = { id -> viewModel.navigateTo(Screen.MedicineDetail(id)) },
                    onAddToCart = { id -> viewModel.addToCart(id) },
                    onViewAll = { viewModel.navigateTo(Screen.Medicines) }
                )
            }

            // 8. Why Choose MediCare Section (4 feature blocks)
            item {
                WhyChooseMediCareSection()
            }

            // 9. How It Works Section (4 steps + prescription flow)
            item {
                HowItWorksSection()
            }

            // 10. Health & Medical Equipment Section ("Healthcare Essentials")
            item {
                HealthEquipmentSection(
                    onExploreEquipment = {
                        viewModel.selectedCategory.value = "Medical Devices"
                        viewModel.navigateTo(Screen.Medicines)
                    }
                )
            }

            // 11. Promotional Banner
            item {
                PromotionalBanner(
                    onShopNow = { viewModel.navigateTo(Screen.Medicines) }
                )
            }

            // 12. Customer Account CTA
            item {
                CustomerAccountCTA(
                    onCreateAccount = { viewModel.navigateTo(Screen.Profile) },
                    onLogin = { viewModel.navigateTo(Screen.Profile) }
                )
            }

            // 13. Newsletter / Updates Section
            item {
                NewsletterSection(
                    email = newsletterEmail,
                    onEmailChange = { viewModel.newsletterEmail.value = it },
                    isSubscribed = isSubscribed,
                    onSubscribe = { viewModel.subscribeNewsletter(it) }
                )
            }

            // 14. Comprehensive Footer
            item {
                Spacer(modifier = Modifier.height(12.dp))
                HomeFooter(
                    onNavigate = { viewModel.navigateTo(it) },
                    onOpenAbout = { showAboutDialog = true },
                    onOpenContact = { showContactDialog = true }
                )
            }
        }
    }

    if (showAboutDialog) {
        AboutUsDialog(onDismiss = { showAboutDialog = false })
    }

    if (showContactDialog) {
        ContactUsDialog(onDismiss = { showContactDialog = false })
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
fun FeaturedHealthcareProductCard(
    medicine: Medicine,
    onViewDetails: () -> Unit,
    onAddToCart: () -> Unit,
    isWishlisted: Boolean,
    onToggleWishlist: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onViewDetails() }
            .testTag("featured_product_card_${medicine.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row: Badges & Wishlist
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (medicine.prescriptionRequired) {
                        RxBadge()
                    } else {
                        Surface(shape = RoundedCornerShape(6.dp), color = MediEmeraldContainer) {
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

                    if (medicine.originalPrice > medicine.price) {
                        Spacer(modifier = Modifier.width(6.dp))
                        val pct = ((medicine.originalPrice - medicine.price) / medicine.originalPrice * 100).toInt()
                        Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFFEF3C7)) {
                            Text(
                                text = "$pct% OFF",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFB45309),
                                    fontSize = 9.sp
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onToggleWishlist,
                    modifier = Modifier.size(28.dp).testTag("featured_wishlist_${medicine.id}")
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) MediRxRed else Color(0xFF94A3B8),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Visual Placeholder Box with Dosage Form
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF8FAFC)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = when {
                            medicine.dosageForm.contains("Device", true) -> Icons.Default.MedicalServices
                            medicine.dosageForm.contains("Ointment", true) || medicine.dosageForm.contains("Kit", true) -> Icons.Default.LocalHospital
                            else -> Icons.Default.Medication
                        },
                        contentDescription = null,
                        tint = MediTealDark,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${medicine.dosageForm} • ${medicine.strength}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF475569)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Product Name & Generic
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
                text = "Generic: ${medicine.genericName}",
                fontSize = 11.sp,
                color = MediTealDark,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Mfg: ${medicine.manufacturer} • Pack: ${medicine.packSize}",
                fontSize = 10.sp,
                color = Color(0xFF64748B),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Rating & Stock Status
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("${medicine.rating} (${medicine.reviewCount})", fontSize = 11.sp, color = Color(0xFF475569))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (medicine.inStock) "✓ In Stock" else "Out of Stock",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (medicine.inStock) MediEmeraldDark else MediRxRed
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Price Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$${String.format("%.2f", medicine.price)}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = MediTealDark
                        )
                    )
                    if (medicine.originalPrice > medicine.price) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$${String.format("%.2f", medicine.originalPrice)}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF94A3B8),
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons: View Details & Add to Cart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onViewDetails,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(38.dp).testTag("btn_view_details_${medicine.id}")
                ) {
                    Text("View Details", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onAddToCart,
                    enabled = medicine.inStock,
                    colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(38.dp).testTag("btn_add_to_cart_${medicine.id}")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add to Cart", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


@Composable
fun MedicineListScreen(
    viewModel: MediCareViewModel
) {
    val filteredMeds by viewModel.filteredMedicines.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val rxOnly by viewModel.filterRxOnly.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()
    val wishlist by viewModel.wishlistMedicines.collectAsState()

    var showSortMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MediClinicalBg)
    ) {
        // Search & Filter header
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.searchQuery.value = it },
                    placeholder = { Text("Search medicine, salt, ailment...", fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MediTeal) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                                Icon(Icons.Default.Delete, contentDescription = "Clear", tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("medicine_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categoriesList) { cat ->
                        val isSelected = (cat == "All" && selectedCategory == null) || (selectedCategory == cat)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                viewModel.selectedCategory.value = if (cat == "All") null else cat
                            },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MediTealContainer,
                                selectedLabelColor = OnMediTealContainer
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Prescription toggle & Sort row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FilterChip(
                            selected = rxOnly == true,
                            onClick = {
                                viewModel.filterRxOnly.value = if (rxOnly == true) null else true
                            },
                            label = { Text("Rx Only", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MediRxRedLight,
                                selectedLabelColor = MediRxRed
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        FilterChip(
                            selected = rxOnly == false,
                            onClick = {
                                viewModel.filterRxOnly.value = if (rxOnly == false) null else false
                            },
                            label = { Text("OTC Only", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MediEmeraldContainer,
                                selectedLabelColor = MediEmeraldDark
                            )
                        )
                    }

                    // Sort button
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.clickable {
                            // Cycle sort options
                            val next = when (sortOption) {
                                SortOption.POPULARITY -> SortOption.PRICE_LOW_HIGH
                                SortOption.PRICE_LOW_HIGH -> SortOption.PRICE_HIGH_LOW
                                SortOption.PRICE_HIGH_LOW -> SortOption.RATING
                                SortOption.RATING -> SortOption.POPULARITY
                            }
                            viewModel.sortOption.value = next
                        }
                    ) {
                        Text(
                            text = "Sort: ${sortOption.title}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Results count
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${filteredMeds.size} products found",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
            )

            if (selectedCategory != null || rxOnly != null || searchQuery.isNotBlank()) {
                TextButton(
                    onClick = {
                        viewModel.selectedCategory.value = null
                        viewModel.filterRxOnly.value = null
                        viewModel.searchQuery.value = ""
                    }
                ) {
                    Text("Reset Filters", fontSize = 11.sp, color = MediTeal)
                }
            }
        }

        if (filteredMeds.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = Color(0xFFCBD5E1),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No medicines matched your criteria",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Try clearing search queries or changing the category filter.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B)),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            viewModel.selectedCategory.value = null
                            viewModel.filterRxOnly.value = null
                            viewModel.searchQuery.value = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MediTeal)
                    ) {
                        Text("Show All Products")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredMeds) { med ->
                    MedicineCard(
                        medicine = med,
                        onMedicineClick = { viewModel.navigateTo(Screen.MedicineDetail(med.id)) },
                        onAddToCart = { viewModel.addToCart(med.id) },
                        isWishlisted = wishlist.any { it.id == med.id },
                        onToggleWishlist = { viewModel.toggleWishlist(med.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun MedicineDetailScreen(
    medicineId: String,
    viewModel: MediCareViewModel
) {
    val medicines by viewModel.rawMedicines.collectAsState()
    val wishlist by viewModel.wishlistMedicines.collectAsState()
    val medicine = medicines.find { it.id == medicineId }

    if (medicine == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Medicine not found")
        }
        return
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    var quantity by remember { mutableIntStateOf(1) }
    val isWishlisted = wishlist.any { it.id == medicine.id }

    Column(modifier = Modifier.fillMaxSize().background(MediClinicalBg)) {
        // Top App Bar
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Medicine Details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = { viewModel.toggleWishlist(medicine.id) }) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) MediRxRed else Color(0xFF64748B)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Visual header card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            if (medicine.prescriptionRequired) {
                                RxBadge()
                            } else {
                                Surface(shape = RoundedCornerShape(6.dp), color = MediEmeraldContainer) {
                                    Text(
                                        "Over The Counter (OTC)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MediEmeraldDark,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFF1F5F9)) {
                                Text(
                                    medicine.category,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF475569),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = medicine.name,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Generic: ${medicine.genericName}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MediTealDark, fontWeight = FontWeight.SemiBold)
                        )

                        Text(
                            text = "Manufactured by ${medicine.manufacturer}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B))
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Key Specs Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SpecBox("Strength", medicine.strength, Modifier.weight(1f))
                            SpecBox("Form", medicine.dosageForm, Modifier.weight(1f))
                            SpecBox("Packaging", medicine.packSize, Modifier.weight(1f))
                        }
                    }
                }
            }

            // Rx Warning Alert Banner (if prescription required)
            if (medicine.prescriptionRequired) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MediRxRedLight),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = MediRxRed, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    "Prescription Required (Schedule Drug)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color(0xFF991B1B)
                                )
                                Text(
                                    "A valid prescription from a registered medical practitioner is mandatory at checkout.",
                                    fontSize = 11.sp,
                                    color = Color(0xFF7F1D1D)
                                )
                            }
                        }
                    }
                }
            }

            // Tabs: Overview, Usage & Dosage, Side Effects
            item {
                Spacer(modifier = Modifier.height(16.dp))
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.White,
                    contentColor = MediTeal
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Overview", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Usage & Dosage", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Side Effects", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        when (selectedTab) {
                            0 -> {
                                Text("Therapeutic Description", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(medicine.description, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 18.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Active Pharmaceutical Ingredients (API)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("${medicine.genericName} (${medicine.strength})", fontSize = 12.sp, color = MediTealDark)
                            }
                            1 -> {
                                Text("Administration & Dosage Directions", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(medicine.usageInstructions, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 18.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Storage Guidelines", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Store below 25°C in a dry place protected from direct sunlight. Keep out of reach of children.", fontSize = 12.sp, color = Color(0xFF64748B))
                            }
                            2 -> {
                                Text("Possible Adverse Reactions & Precautions", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MediRxRed)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(medicine.sideEffects, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 18.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Disclaimer", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Consult your prescribing doctor immediately if you experience shortness of breath, severe rash, or swelling.", fontSize = 11.sp, color = Color(0xFF64748B))
                            }
                        }
                    }
                }
            }
        }

        // Bottom purchase bar
        Surface(
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Price & Stepper
                Column {
                    Text(
                        text = "$${String.format("%.2f", medicine.price * quantity)}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = MediTealDark
                        )
                    )
                    Text(
                        text = "$${String.format("%.2f", medicine.price)} each",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF64748B))
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Quantity stepper
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MediBorder),
                        color = Color(0xFFF8FAFC)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = quantity.toString(),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                fontSize = 14.sp
                            )
                            IconButton(
                                onClick = { if (quantity < medicine.stockCount) quantity++ },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            viewModel.addToCart(medicine.id, quantity)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MediTeal),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("detail_add_to_cart_btn")
                    ) {
                        Icon(Icons.Default.Medication, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add to Basket", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun SpecBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF8FAFC),
        border = androidx.compose.foundation.BorderStroke(1.dp, MediBorder),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, fontSize = 9.sp, color = Color(0xFF64748B), fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A), textAlign = TextAlign.Center)
        }
    }
}
