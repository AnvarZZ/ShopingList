package az.anvar.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopItemList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_items WHERE id = :shopItemDbModelId")
    fun deleteShopItem(shopItemDbModelId: Int)

    @Query("SELECT * FROM shop_items WHERE id = :shopItemDbModelId LIMIT 1")
    fun getShopItem(shopItemDbModelId: Int): ShopItemDbModel
}