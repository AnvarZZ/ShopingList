package az.anvar.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import az.anvar.shopinglist.data.ShopListRepositoryImpl
import az.anvar.shopinglist.domain.AddShopItemUseCase
import az.anvar.shopinglist.domain.EditShopItemUseCase
import az.anvar.shopinglist.domain.GetShopItemUseCase
import az.anvar.shopinglist.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldFinish = MutableLiveData<Unit>()
    val shouldFinish: LiveData<Unit>
        get() = _shouldFinish

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.value = item
        }
    }

    fun addShopItem(name: String?, count: String?) {
        val inputName = parseName(name)
        val inputCount = parseCount(count)
        val areValid = validateInput(inputName, inputCount)
        if (areValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(inputName, inputCount, true)
                addShopItemUseCase.addShopItem(shopItem)
                finish()
            }
        }
    }


    fun editShopItem(name: String?, count: String?) {
        val inputName = parseName(name)
        val inputCount = parseCount(count)
        val areValid = validateInput(inputName, inputCount)
        if (areValid) {
            viewModelScope.launch {
                _shopItem.value?.let {
                    val item = it.copy(name = inputName, count = inputCount)
                    editShopItemUseCase.editShopItem(item)
                    finish()
                }
            }
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (exception: NumberFormatException) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            return false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finish() {
        _shouldFinish.value = Unit
    }
}