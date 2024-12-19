package com.example.mypratice_databinding_3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel: ViewModel() {
    // 雙向綁定的數據
    val quantity = MutableLiveData("1")
    val couponCode = MutableLiveData("")

    // 選擇的商品資訊
    private val _selectedProduct = MutableLiveData<String>()
    val selectedProduct: LiveData<String>
        get(){ return _selectedProduct }

    // 訂單資訊
    private val _orderInfo = MutableLiveData(OrderInfo("", 0, 0.0, 0.0, 0.0))
    val orderInfo: LiveData<OrderInfo>
        get(){return _orderInfo}

    // 商品價格對照表
    private val productPrices = mapOf(
        "商品A" to 100.0,
        "商品B" to 200.0,
        "商品C" to 300.0
    )

    // 處理商品選擇
    fun onProductSelected(productName: String, position: Int) {
        _selectedProduct.value = productName
    }

    // 計算價格（處理多個參數）
    fun calculatePrice(productId: String, quantity: Int, couponCode: String) {
        val basePrice = productPrices[productId] ?: 0.0
        val totalPrice = basePrice * quantity

        // 根據優惠碼計算折扣
        val discount = when (couponCode.uppercase()) {
            "SAVE10" -> 0.1
            "SAVE20" -> 0.2
            "VIP" -> 0.3
            else -> 0.0
        }

        val discountAmount = totalPrice * discount
        val finalPrice = totalPrice - discountAmount

        // 更新訂單資訊
        _orderInfo.value = OrderInfo(
            productId = productId,
            quantity = quantity,
            price = totalPrice,
            discount = discountAmount,
            finalPrice = finalPrice
        )
    }
}