package az.anvar.shopinglist.presentation

import androidx.lifecycle.ViewModel
import az.anvar.shopinglist.data.ShopListRepositoryImpl
import az.anvar.shopinglist.domain.AddShopItemUseCase
import az.anvar.shopinglist.domain.EditShopItemUseCase
import az.anvar.shopinglist.domain.GetShopItemUseCase
import az.anvar.shopinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(name: String?, count: String?) {
        val inputName = parseName(name)
        val inputCount = parseCount(count)
        val areValid = validateInput(inputName, inputCount)
        if (areValid) {
            val shopItem = ShopItem(inputName, inputCount, true)
            addShopItemUseCase.addShopItem(shopItem)
        }

    }


    fun editShopItem(name: String?, count: String?) {
        val inputName = parseName(name)
        val inputCount = parseCount(count)
        val areValid = validateInput(inputName, inputCount)
        if (areValid) {
            val shopItem = ShopItem(inputName, inputCount, true)
            editShopItemUseCase.editShopItem(shopItem)
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
            // TODO: show error input name
            result = false
        }
        if (count <= 0) {
            // TODO: show error input count
            return false
        }
        return result
    }
}