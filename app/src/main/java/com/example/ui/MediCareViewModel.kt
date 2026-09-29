package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AddressEntity
import com.example.data.local.MediCareDatabase
import com.example.data.local.PrescriptionEntity
import com.example.data.model.CartItemWithDetails
import com.example.data.model.Medicine
import com.example.data.model.OrderStatus
import com.example.data.model.OrderWithItems
import com.example.data.model.PrescriptionStatus
import com.example.data.model.UserRole
import com.example.data.repository.MediCareRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    // Customer Screens
    data object Home : Screen()
    data object Medicines : Screen()
    data class MedicineDetail(val medicineId: String) : Screen()
    data object Categories : Screen()
    data object Cart : Screen()
    data object Checkout : Screen()
    data object Orders : Screen()
    data class OrderDetail(val orderId: String) : Screen()
    data object Prescriptions : Screen()
    data class PrescriptionDetail(val prescriptionId: String) : Screen()
    data object UploadPrescription : Screen()
    data object Wishlist : Screen()
    data object Addresses : Screen()
    data object Profile : Screen()
    data object AboutUs : Screen()
    data object ContactUs : Screen()

    // Pharmacist Screens
    data object PharmacistDashboard : Screen()
    data class PharmacistReviewRx(val prescriptionId: String) : Screen()

    // Store Staff Screens
    data object StaffInventory : Screen()

    // Delivery Staff Screens
    data object DeliveryRuns : Screen()

    // Admin Screens
    data object AdminDashboard : Screen()
    data object AdminProducts : Screen()
    data object AdminInventory : Screen()
    data object AdminOrders : Screen()
    data object AdminPrescriptions : Screen()
    data object AdminReports : Screen()
    data object AdminSettings : Screen()
}

enum class SortOption(val title: String) {
    POPULARITY("Most Popular"),
    PRICE_LOW_HIGH("Price: Low to High"),
    PRICE_HIGH_LOW("Price: High to Low"),
    RATING("Highest Rated")
}

class MediCareViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MediCareRepository =
        MediCareRepository(MediCareDatabase.getInstance(application).dao())

    // Role state
    private val _currentRole = MutableStateFlow(UserRole.CUSTOMER)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    // Navigation Stack
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<Screen>(Screen.Home)

    // Search & Filters
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow<String?>(null)
    val filterRxOnly = MutableStateFlow<Boolean?>(null)
    val sortOption = MutableStateFlow(SortOption.POPULARITY)

    // Data streams from repository
    val rawMedicines: StateFlow<List<Medicine>> = repository.medicines.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val cartItems: StateFlow<List<CartItemWithDetails>> = repository.cartWithDetails.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val wishlistMedicines: StateFlow<List<Medicine>> = repository.wishlistMedicines.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val prescriptions: StateFlow<List<PrescriptionEntity>> = repository.prescriptions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val orders: StateFlow<List<OrderWithItems>> = repository.orders.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val addresses: StateFlow<List<AddressEntity>> = repository.addresses.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Filtered medicines
    val filteredMedicines: StateFlow<List<Medicine>> = combine(
        rawMedicines,
        searchQuery,
        selectedCategory,
        filterRxOnly,
        sortOption
    ) { meds, query, cat, rxOnly, sort ->
        var list = meds

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(q) ||
                it.genericName.lowercase().contains(q) ||
                it.category.lowercase().contains(q) ||
                it.manufacturer.lowercase().contains(q) ||
                it.description.lowercase().contains(q)
            }
        }

        if (cat != null) {
            list = list.filter { it.category.equals(cat, ignoreCase = true) }
        }

        if (rxOnly != null) {
            list = list.filter { it.prescriptionRequired == rxOnly }
        }

        when (sort) {
            SortOption.POPULARITY -> list.sortedByDescending { it.reviewCount }
            SortOption.PRICE_LOW_HIGH -> list.sortedBy { it.price }
            SortOption.PRICE_HIGH_LOW -> list.sortedByDescending { it.price }
            SortOption.RATING -> list.sortedByDescending { it.rating }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Checkout selection states
    val selectedAddressId = MutableStateFlow<String?>("addr_1")
    val selectedPaymentMethod = MutableStateFlow("Health Insurance / HSA Card")
    val attachedPrescriptionId = MutableStateFlow<String?>("rx_101")
    val promoCode = MutableStateFlow("")
    val promoDiscount = MutableStateFlow(0.0)

    // Newsletter & Interactive Home States
    val newsletterEmail = MutableStateFlow("")
    val isSubscribedNewsletter = MutableStateFlow(false)
    val isSimulatedLoading = MutableStateFlow(false)
    val simulateError = MutableStateFlow<String?>(null)

    fun subscribeNewsletter(email: String): Boolean {
        val trimmed = email.trim()
        val isValid = trimmed.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(trimmed).matches()
        if (isValid) {
            isSubscribedNewsletter.value = true
            newsletterEmail.value = ""
            showMessage("Thank you! You are subscribed to MediCare Health updates.")
            return true
        } else {
            showMessage("Please enter a valid email address (e.g. name@example.com)")
            return false
        }
    }

    fun triggerRefresh() {
        viewModelScope.launch {
            isSimulatedLoading.value = true
            simulateError.value = null
            kotlinx.coroutines.delay(600)
            isSimulatedLoading.value = false
            showMessage("Catalog updated with latest clinical stocks")
        }
    }

    // Feedback message (snackbar/banner)
    private val _notificationMessage = MutableStateFlow<String?>(null)
    val notificationMessage: StateFlow<String?> = _notificationMessage.asStateFlow()

    fun showMessage(msg: String) {
        _notificationMessage.value = msg
    }

    fun dismissMessage() {
        _notificationMessage.value = null
    }

    // Role switcher
    fun switchRole(role: UserRole) {
        _currentRole.value = role
        screenStack.clear()
        val defaultScreen = when (role) {
            UserRole.CUSTOMER -> Screen.Home
            UserRole.PHARMACIST -> Screen.PharmacistDashboard
            UserRole.STORE_STAFF -> Screen.StaffInventory
            UserRole.DELIVERY_STAFF -> Screen.DeliveryRuns
            UserRole.ADMIN -> Screen.AdminDashboard
        }
        screenStack.add(defaultScreen)
        _currentScreen.value = defaultScreen
        showMessage("Switched workspace to: ${role.displayName}")
    }

    // Navigation methods
    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = screenStack.last()
            return true
        }
        return false
    }

    // Cart operations
    fun addToCart(medicineId: String, quantity: Int = 1) {
        viewModelScope.launch {
            val existing = cartItems.value.find { it.medicine.id == medicineId }
            val newQty = (existing?.quantity ?: 0) + quantity
            repository.updateCartQuantity(medicineId, newQty)
            showMessage("Added to prescription basket")
        }
    }

    fun updateCartQuantity(medicineId: String, quantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(medicineId, quantity)
        }
    }

    fun removeFromCart(medicineId: String) {
        viewModelScope.launch {
            repository.removeFromCart(medicineId)
            showMessage("Item removed from cart")
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun toggleWishlist(medicineId: String) {
        viewModelScope.launch {
            val isWishlisted = wishlistMedicines.value.any { it.id == medicineId }
            repository.toggleWishlist(medicineId, isWishlisted)
            showMessage(if (isWishlisted) "Removed from wishlist" else "Added to wishlist")
        }
    }

    // Prescription submission
    fun uploadPrescription(
        patientName: String,
        patientAge: Int,
        doctorName: String,
        doctorLicense: String,
        diagnosis: String,
        notes: String
    ) {
        viewModelScope.launch {
            val rxId = repository.uploadPrescription(
                patientName, patientAge, doctorName, doctorLicense, diagnosis, notes
            )
            attachedPrescriptionId.value = rxId
            showMessage("Prescription submitted for pharmacist review")
            navigateTo(Screen.Prescriptions)
        }
    }

    // Pharmacist actions
    fun reviewPrescription(id: String, status: PrescriptionStatus, notes: String, pharmacistName: String = "Dr. Michael Chang, PharmD") {
        viewModelScope.launch {
            repository.updatePrescriptionStatus(id, status, notes, pharmacistName)
            showMessage("Prescription status updated to: ${status.label}")
        }
    }

    // Checkout & Order Placement
    fun applyPromo(code: String) {
        if (code.equals("MEDICARE10", ignoreCase = true)) {
            promoDiscount.value = 5.0
            showMessage("Coupon MEDICARE10 applied! $5.00 discount")
        } else if (code.equals("HEALTHFIRST", ignoreCase = true)) {
            promoDiscount.value = 8.0
            showMessage("Coupon HEALTHFIRST applied! $8.00 discount")
        } else {
            promoDiscount.value = 0.0
            showMessage("Invalid promo coupon code")
        }
    }

    fun placeCurrentOrder() {
        val currentCart = cartItems.value
        if (currentCart.isEmpty()) {
            showMessage("Your cart is empty")
            return
        }

        val addrList = addresses.value
        val selectedAddr = addrList.find { it.id == selectedAddressId.value } ?: addrList.firstOrNull()
        val addrString = selectedAddr?.let { "${it.street}, ${it.city}, ${it.state} ${it.zipCode}" } ?: "742 Evergreen Terrace, Springfield, OR"

        viewModelScope.launch {
            val orderId = repository.placeOrder(
                customerName = "Sarah Jenkins",
                customerPhone = "+1 (555) 349-8821",
                deliveryAddress = addrString,
                paymentMethod = selectedPaymentMethod.value,
                prescriptionId = attachedPrescriptionId.value,
                cartItems = currentCart,
                discount = promoDiscount.value
            )
            promoDiscount.value = 0.0
            promoCode.value = ""
            showMessage("Order placed successfully! Tracking active.")
            navigateTo(Screen.OrderDetail(orderId))
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
            showMessage("Order status updated to: ${newStatus.title}")
        }
    }

    // Inventory management (Store Staff & Admin)
    fun updateStock(medicineId: String, newStock: Int) {
        viewModelScope.launch {
            repository.updateMedicineStock(medicineId, newStock)
            showMessage("Inventory updated for $medicineId: $newStock in stock")
        }
    }

    fun addCustomMedicine(medicine: Medicine) {
        viewModelScope.launch {
            repository.saveMedicine(medicine)
            showMessage("New medicine added to pharmacy inventory: ${medicine.name}")
        }
    }

    fun saveAddress(address: AddressEntity) {
        viewModelScope.launch {
            repository.saveAddress(address)
            showMessage("Address saved: ${address.title}")
        }
    }

    fun setDefaultAddress(id: String) {
        viewModelScope.launch {
            repository.setDefaultAddress(id)
            selectedAddressId.value = id
            showMessage("Default delivery address updated")
        }
    }
}
