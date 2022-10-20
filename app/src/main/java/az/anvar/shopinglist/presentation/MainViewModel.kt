package az.anvar.shopinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList() {
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
        getShopList()
    }

    fun editShopItem(shopItem: ShopItem) {
        val item = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(item)
        getShopList()
    }
}