package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.UserRole
import com.example.ui.MediCareViewModel
import com.example.ui.Screen
import com.example.ui.components.CustomerBottomNavBar
import com.example.ui.components.MediCareTopBar
import com.example.ui.components.RoleSwitcherDialog
import com.example.ui.screens.AboutUsScreen
import com.example.ui.screens.AddressesScreen
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CheckoutScreen
import com.example.ui.screens.ContactUsScreen
import com.example.ui.screens.CustomerHomeScreen
import com.example.ui.screens.CustomerProfileScreen
import com.example.ui.screens.DeliveryDashboardScreen
import com.example.ui.screens.MedicineDetailScreen
import com.example.ui.screens.MedicineListScreen
import com.example.ui.screens.OrderDetailScreen
import com.example.ui.screens.OrdersScreen
import com.example.ui.screens.PharmacistDashboardScreen
import com.example.ui.screens.PrescriptionListScreen
import com.example.ui.screens.PrescriptionUploadScreen
import com.example.ui.screens.StaffDashboardScreen
import com.example.ui.screens.WishlistScreen
import com.example.ui.theme.MediClinicalBg
import com.example.ui.theme.MediTealDark
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: MediCareViewModel = viewModel()
                MediCareApp(viewModel)
            }
        }
    }
}

@Composable
fun MediCareApp(viewModel: MediCareViewModel) {
    val currentRole by viewModel.currentRole.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val notificationMessage by viewModel.notificationMessage.collectAsState()

    var showRoleModal by remember { mutableStateOf(false) }

    // Intercept back button for deep screens
    val isRootScreen = currentScreen is Screen.Home ||
                       currentScreen is Screen.PharmacistDashboard ||
                       currentScreen is Screen.StaffInventory ||
                       currentScreen is Screen.DeliveryRuns ||
                       currentScreen is Screen.AdminDashboard

    BackHandler(enabled = !isRootScreen) {
        viewModel.navigateBack()
    }

    val totalCartCount = cartItems.sumOf { it.quantity }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(MediClinicalBg),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            if (currentScreen !is Screen.Home) {
                MediCareTopBar(
                    currentRole = currentRole,
                    cartCount = totalCartCount,
                    onRoleClick = { showRoleModal = true },
                    onCartClick = { viewModel.navigateTo(Screen.Cart) },
                    onSearchClick = { viewModel.navigateTo(Screen.Medicines) }
                )
            }
        },
        bottomBar = {
            if (currentRole == UserRole.CUSTOMER) {
                CustomerBottomNavBar(
                    currentScreen = currentScreen,
                    onNavigate = { viewModel.navigateTo(it) },
                    cartCount = totalCartCount
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Screen Routing
            when (val screen = currentScreen) {
                is Screen.Home -> CustomerHomeScreen(viewModel = viewModel)
                is Screen.Medicines -> MedicineListScreen(viewModel = viewModel)
                is Screen.MedicineDetail -> MedicineDetailScreen(medicineId = screen.medicineId, viewModel = viewModel)
                is Screen.Categories -> CategoriesScreen(viewModel = viewModel)
                is Screen.Cart -> CartScreen(viewModel = viewModel)
                is Screen.Checkout -> CheckoutScreen(viewModel = viewModel)
                is Screen.Orders -> OrdersScreen(viewModel = viewModel)
                is Screen.OrderDetail -> OrderDetailScreen(orderId = screen.orderId, viewModel = viewModel)
                is Screen.Prescriptions,
                is Screen.PrescriptionDetail -> PrescriptionListScreen(viewModel = viewModel)
                is Screen.UploadPrescription -> PrescriptionUploadScreen(viewModel = viewModel)
                is Screen.Wishlist -> WishlistScreen(viewModel = viewModel)
                is Screen.Addresses -> AddressesScreen(viewModel = viewModel)
                is Screen.Profile -> CustomerProfileScreen(viewModel = viewModel)
                is Screen.AboutUs -> AboutUsScreen(viewModel = viewModel)
                is Screen.ContactUs -> ContactUsScreen(viewModel = viewModel)

                // Dedicated Professional Dashboards
                is Screen.PharmacistDashboard,
                is Screen.PharmacistReviewRx -> PharmacistDashboardScreen(viewModel = viewModel)

                is Screen.StaffInventory -> StaffDashboardScreen(viewModel = viewModel)
                is Screen.DeliveryRuns -> DeliveryDashboardScreen(viewModel = viewModel)

                is Screen.AdminDashboard,
                is Screen.AdminProducts,
                is Screen.AdminInventory,
                is Screen.AdminOrders,
                is Screen.AdminPrescriptions,
                is Screen.AdminReports,
                is Screen.AdminSettings -> AdminDashboardScreen(viewModel = viewModel)
            }

            // Notification / Feedback Banner
            AnimatedVisibility(
                visible = notificationMessage != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                if (notificationMessage != null) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF0F172A),
                        shadowElevation = 6.dp,
                        modifier = Modifier.fillMaxWidth().testTag("app_notification_banner")
                    ) {
                        androidx.compose.foundation.layout.Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = notificationMessage ?: "",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(onClick = { viewModel.dismissMessage() }) {
                                Text("OK", color = Color(0xFF5EEAD4), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
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
