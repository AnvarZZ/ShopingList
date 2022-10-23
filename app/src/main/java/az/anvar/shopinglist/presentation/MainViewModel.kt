package az.anvar.shopinglist.presentation

import androidx.lifecycle.ViewModel
import az.anvar.shopinglist.data.ShopListRepositoryImpl
import az.anvar.shopinglist.domain.EditShopItemUseCase
import az.anvar.shopinglist.domain.GetShopListUseCase
import az.anvar.shopinglist.domain.RemoveShopItemUseCase
import az.anvar.shopinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun changeStateShopItem(shopItem: ShopItem) {
        val item = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(item)
    }
}