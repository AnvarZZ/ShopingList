package az.anvar.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import az.anvar.shopinglist.data.ShopListRepositoryImpl
import az.anvar.shopinglist.domain.EditShopItemUseCase
import az.anvar.shopinglist.domain.GetShopListUseCase
import az.anvar.shopinglist.domain.RemoveShopItemUseCase
import az.anvar.shopinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        scope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }

    fun changeStateShopItem(shopItem: ShopItem) {
        scope.launch {
            val item = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}