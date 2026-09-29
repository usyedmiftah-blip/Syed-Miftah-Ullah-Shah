package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.Medicine
import com.example.data.model.PrescriptionStatus

@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey val id: String,
    val name: String,
    val genericName: String,
    val category: String,
    val price: Double,
    val originalPrice: Double,
    val inStock: Boolean,
    val stockCount: Int,
    val prescriptionRequired: Boolean,
    val manufacturer: String,
    val dosageForm: String,
    val strength: String,
    val packSize: String,
    val description: String,
    val usageInstructions: String,
    val sideEffects: String,
    val rating: Double,
    val reviewCount: Int,
    val badge: String? = null
) {
    fun toDomain(): Medicine = Medicine(
        id = id,
        name = name,
        genericName = genericName,
        category = category,
        price = price,
        originalPrice = originalPrice,
        inStock = inStock,
        stockCount = stockCount,
        prescriptionRequired = prescriptionRequired,
        manufacturer = manufacturer,
        dosageForm = dosageForm,
        strength = strength,
        packSize = packSize,
        description = description,
        usageInstructions = usageInstructions,
        sideEffects = sideEffects,
        rating = rating,
        reviewCount = reviewCount,
        badge = badge
    )

    companion object {
        fun fromDomain(m: Medicine): MedicineEntity = MedicineEntity(
            id = m.id,
            name = m.name,
            genericName = m.genericName,
            category = m.category,
            price = m.price,
            originalPrice = m.originalPrice,
            inStock = m.inStock,
            stockCount = m.stockCount,
            prescriptionRequired = m.prescriptionRequired,
            manufacturer = m.manufacturer,
            dosageForm = m.dosageForm,
            strength = m.strength,
            packSize = m.packSize,
            description = m.description,
            usageInstructions = m.usageInstructions,
            sideEffects = m.sideEffects,
            rating = m.rating,
            reviewCount = m.reviewCount,
            badge = m.badge
        )
    }
}

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val medicineId: String,
    val quantity: Int,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "wishlist_items")
data class WishlistItemEntity(
    @PrimaryKey val medicineId: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "prescriptions")
data class PrescriptionEntity(
    @PrimaryKey val id: String,
    val patientName: String,
    val patientAge: Int,
    val doctorName: String,
    val doctorLicense: String,
    val dateIssued: String,
    val diagnosis: String,
    val notes: String,
    val status: String = PrescriptionStatus.PENDING_REVIEW.name,
    val pharmacistNotes: String = "",
    val verifiedBy: String = "",
    val uploadedAt: Long = System.currentTimeMillis(),
    val imageType: String = "DIGITAL_RX"
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
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
    val status: String, // PLACED, PRESCRIPTION_VERIFIED, PACKING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    val prescriptionId: String?,
    val itemsSummary: String, // Formatted text e.g. "Amoxicillin 500mg (x2), Paracetamol 650mg (x1)"
    val itemsData: String, // Semicolon separated list: id|name|strength|qty|price|rxReq
    val placedAt: Long = System.currentTimeMillis(),
    val estimatedDelivery: String,
    val deliveryPersonnelName: String = "David Chen (Express Medical Courier)",
    val deliveryPersonnelPhone: String = "+1 (555) 019-2834"
)

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey val id: String,
    val title: String, // Home, Work, Parents
    val recipientName: String,
    val phone: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val isDefault: Boolean = false
)
