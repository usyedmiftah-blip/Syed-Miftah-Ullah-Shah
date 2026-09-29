package com.example.data.repository

import com.example.data.local.AddressEntity
import com.example.data.local.CartItemEntity
import com.example.data.local.MediCareDao
import com.example.data.local.MedicineEntity
import com.example.data.local.OrderEntity
import com.example.data.local.PrescriptionEntity
import com.example.data.local.WishlistItemEntity
import com.example.data.model.CartItemWithDetails
import com.example.data.model.Medicine
import com.example.data.model.OrderItem
import com.example.data.model.OrderStatus
import com.example.data.model.OrderWithItems
import com.example.data.model.PrescriptionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID

class MediCareRepository(private val dao: MediCareDao) {

    init {
        // Ensure initial clinical mock data is seeded
        CoroutineScope(Dispatchers.IO).launch {
            val count = dao.getMedicineCount()
            if (count == 0) {
                dao.insertMedicines(MockData.initialMedicines)
                dao.insertAddresses(MockData.initialAddresses)
                MockData.initialPrescriptions.forEach { dao.insertPrescription(it) }
                MockData.initialOrders.forEach { dao.insertOrder(it) }
            }
        }
    }

    val medicines: Flow<List<Medicine>> = dao.getAllMedicines().map { list ->
        list.map { it.toDomain() }
    }

    val cartWithDetails: Flow<List<CartItemWithDetails>> = combine(
        dao.getCartItems(),
        dao.getAllMedicines()
    ) { cartItems, medicines ->
        val medMap = medicines.associateBy { it.id }
        cartItems.mapNotNull { cartItem ->
            val med = medMap[cartItem.medicineId]
            if (med != null) {
                CartItemWithDetails(medicine = med.toDomain(), quantity = cartItem.quantity)
            } else null
        }
    }

    val wishlistMedicines: Flow<List<Medicine>> = combine(
        dao.getWishlistItems(),
        dao.getAllMedicines()
    ) { wishlistItems, medicines ->
        val medMap = medicines.associateBy { it.id }
        wishlistItems.mapNotNull { medMap[it.medicineId]?.toDomain() }
    }

    val prescriptions: Flow<List<PrescriptionEntity>> = dao.getAllPrescriptions()

    val orders: Flow<List<OrderWithItems>> = dao.getAllOrders().map { list ->
        list.map { entity -> parseOrder(entity) }
    }

    val addresses: Flow<List<AddressEntity>> = dao.getAllAddresses()

    fun getMedicineById(id: String): Flow<Medicine?> {
        return dao.getMedicineById(id).map { it?.toDomain() }
    }

    suspend fun addToCart(medicineId: String, quantity: Int = 1) {
        dao.insertCartItem(CartItemEntity(medicineId = medicineId, quantity = quantity))
    }

    suspend fun updateCartQuantity(medicineId: String, quantity: Int) {
        if (quantity <= 0) {
            dao.deleteCartItem(medicineId)
        } else {
            dao.updateCartQuantity(medicineId, quantity)
        }
    }

    suspend fun removeFromCart(medicineId: String) {
        dao.deleteCartItem(medicineId)
    }

    suspend fun clearCart() {
        dao.clearCart()
    }

    suspend fun toggleWishlist(medicineId: String, isCurrentlyWishlisted: Boolean) {
        if (isCurrentlyWishlisted) {
            dao.deleteWishlistItem(medicineId)
        } else {
            dao.insertWishlistItem(WishlistItemEntity(medicineId = medicineId))
        }
    }

    suspend fun uploadPrescription(
        patientName: String,
        patientAge: Int,
        doctorName: String,
        doctorLicense: String,
        diagnosis: String,
        notes: String
    ): String {
        val id = "rx_" + System.currentTimeMillis().toString().takeLast(6)
        val entity = PrescriptionEntity(
            id = id,
            patientName = patientName,
            patientAge = patientAge,
            doctorName = doctorName,
            doctorLicense = doctorLicense,
            dateIssued = "2026-09-29",
            diagnosis = diagnosis,
            notes = notes,
            status = PrescriptionStatus.PENDING_REVIEW.name,
            pharmacistNotes = "",
            verifiedBy = "",
            uploadedAt = System.currentTimeMillis()
        )
        dao.insertPrescription(entity)
        return id
    }

    suspend fun updatePrescriptionStatus(id: String, status: PrescriptionStatus, notes: String, verifiedBy: String) {
        dao.updatePrescriptionStatus(id, status.name, notes, verifiedBy)
    }

    suspend fun placeOrder(
        customerName: String,
        customerPhone: String,
        deliveryAddress: String,
        paymentMethod: String,
        prescriptionId: String?,
        cartItems: List<CartItemWithDetails>,
        discount: Double = 0.0
    ): String {
        val orderId = "ord_" + System.currentTimeMillis().toString().takeLast(6)
        val orderNum = "MC-2026-" + (1000..9999).random()

        val subtotal = cartItems.sumOf { it.medicine.price * it.quantity }
        val deliveryFee = if (subtotal > 35.0) 0.0 else 3.99
        val total = (subtotal + deliveryFee - discount).coerceAtLeast(0.0)

        val itemsSummary = cartItems.joinToString(", ") { "${it.medicine.name} (x${it.quantity})" }
        val itemsData = cartItems.joinToString(";") {
            "${it.medicine.id}|${it.medicine.name}|${it.medicine.strength}|${it.quantity}|${it.medicine.price}|${it.medicine.prescriptionRequired}"
        }

        val initialStatus = if (cartItems.any { it.medicine.prescriptionRequired } && prescriptionId == null) {
            OrderStatus.PLACED.name
        } else if (cartItems.any { it.medicine.prescriptionRequired }) {
            OrderStatus.PLACED.name
        } else {
            OrderStatus.PACKING.name
        }

        val order = OrderEntity(
            id = orderId,
            orderNumber = orderNum,
            customerName = customerName,
            customerPhone = customerPhone,
            deliveryAddress = deliveryAddress,
            paymentMethod = paymentMethod,
            paymentStatus = if (paymentMethod.contains("Cash")) "CASH_ON_DELIVERY" else "PAID",
            subtotal = subtotal,
            deliveryFee = deliveryFee,
            discount = discount,
            total = total,
            status = initialStatus,
            prescriptionId = prescriptionId,
            itemsSummary = itemsSummary,
            itemsData = itemsData,
            placedAt = System.currentTimeMillis(),
            estimatedDelivery = "Tomorrow by 2:00 PM",
            deliveryPersonnelName = "David Chen (Express Courier)",
            deliveryPersonnelPhone = "+1 (555) 019-2834"
        )

        dao.insertOrder(order)
        dao.clearCart()
        return orderId
    }

    suspend fun updateOrderStatus(orderId: String, status: OrderStatus) {
        dao.updateOrderStatus(orderId, status.name)
    }

    suspend fun updateMedicineStock(id: String, stock: Int) {
        dao.updateStock(id, stock)
    }

    suspend fun saveMedicine(medicine: Medicine) {
        dao.insertMedicine(MedicineEntity.fromDomain(medicine))
    }

    suspend fun deleteMedicine(id: String) {
        dao.deleteMedicine(id)
    }

    suspend fun saveAddress(address: AddressEntity) {
        dao.insertAddress(address)
    }

    suspend fun setDefaultAddress(id: String) {
        dao.setDefaultAddress(id)
    }

    suspend fun deleteAddress(id: String) {
        dao.deleteAddress(id)
    }

    private fun parseOrder(entity: OrderEntity): OrderWithItems {
        val itemsList = if (entity.itemsData.isNotBlank()) {
            entity.itemsData.split(";").mapNotNull { itemStr ->
                val parts = itemStr.split("|")
                if (parts.size >= 6) {
                    OrderItem(
                        medicineId = parts[0],
                        medicineName = parts[1],
                        strength = parts[2],
                        quantity = parts[3].toIntOrNull() ?: 1,
                        unitPrice = parts[4].toDoubleOrNull() ?: 0.0,
                        prescriptionRequired = parts[5].toBooleanStrictOrNull() ?: false
                    )
                } else null
            }
        } else emptyList()

        val statusEnum = try {
            OrderStatus.valueOf(entity.status)
        } catch (e: Exception) {
            OrderStatus.PLACED
        }

        return OrderWithItems(
            id = entity.id,
            orderNumber = entity.orderNumber,
            customerName = entity.customerName,
            customerPhone = entity.customerPhone,
            deliveryAddress = entity.deliveryAddress,
            paymentMethod = entity.paymentMethod,
            paymentStatus = entity.paymentStatus,
            subtotal = entity.subtotal,
            deliveryFee = entity.deliveryFee,
            discount = entity.discount,
            total = entity.total,
            status = statusEnum,
            prescriptionId = entity.prescriptionId,
            items = itemsList,
            placedAt = entity.placedAt,
            estimatedDelivery = entity.estimatedDelivery,
            deliveryPersonnelName = entity.deliveryPersonnelName,
            deliveryPersonnelPhone = entity.deliveryPersonnelPhone
        )
    }
}
