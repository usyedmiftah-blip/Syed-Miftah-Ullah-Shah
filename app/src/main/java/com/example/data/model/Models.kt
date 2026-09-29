package com.example.data.model

enum class UserRole(val displayName: String, val badge: String) {
    CUSTOMER("Customer", "Patient"),
    PHARMACIST("Pharmacist", "Licensed RPh"),
    STORE_STAFF("Store Staff", "Inventory & Packing"),
    DELIVERY_STAFF("Delivery Partner", "Active Courier"),
    ADMIN("Administrator", "Pharmacy Director")
}

data class Medicine(
    val id: String,
    val name: String,
    val genericName: String,
    val category: String,
    val price: Double,
    val originalPrice: Double = price,
    val inStock: Boolean = true,
    val stockCount: Int = 50,
    val prescriptionRequired: Boolean = false,
    val manufacturer: String,
    val dosageForm: String, // Tablet, Syrup, Capsule, Inhaler, Gel, Drops, Device
    val strength: String, // e.g. 500mg, 10mg/5ml
    val packSize: String, // e.g. 10 Tablets / Strip
    val description: String,
    val usageInstructions: String,
    val sideEffects: String,
    val rating: Double = 4.8,
    val reviewCount: Int = 124,
    val badge: String? = null // e.g. "Bestseller", "20% OFF", "Essential"
)

data class CartItemWithDetails(
    val medicine: Medicine,
    val quantity: Int
)

data class OrderItem(
    val medicineId: String,
    val medicineName: String,
    val strength: String,
    val quantity: Int,
    val unitPrice: Double,
    val prescriptionRequired: Boolean
)

data class OrderWithItems(
    val id: String,
    val orderNumber: String,
    val customerName: String,
    val customerPhone: String,
    val deliveryAddress: String,
    val paymentMethod: String,
    val paymentStatus: String,
    val subtotal: Double,
    val deliveryFee: Double,
    val discount: Double,
    val total: Double,
    val status: OrderStatus,
    val prescriptionId: String?,
    val items: List<OrderItem>,
    val placedAt: Long,
    val estimatedDelivery: String,
    val deliveryPersonnelName: String,
    val deliveryPersonnelPhone: String
)

enum class OrderStatus(val title: String, val stepIndex: Int) {
    PLACED("Order Placed", 0),
    PRESCRIPTION_VERIFIED("Prescription Verified", 1),
    PACKING("Dispensing & Packed", 2),
    OUT_FOR_DELIVERY("Out for Delivery", 3),
    DELIVERED("Delivered", 4),
    CANCELLED("Cancelled", -1)
}

enum class PrescriptionStatus(val label: String) {
    PENDING_REVIEW("Pending Verification"),
    APPROVED("Verified & Approved"),
    REJECTED("Requires Clarification"),
    DISPENSED("Dispensed")
}

data class CategoryInfo(
    val name: String,
    val description: String,
    val iconName: String,
    val itemCount: Int
)
