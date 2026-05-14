package com.example.oceanxgroceryapp

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

// 1. THE ENTITY: Represents a table in our local database
@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Int,
    val imageRes: Int,
    var quantity: Int
)

// 2. THE DAO (Data Access Object): How we interact with the data
@Dao
interface CartDao {
    // Flow automatically updates the UI whenever the database changes!
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartEntity)

    @Query("UPDATE cart_items SET quantity = quantity + 1 WHERE id = :itemId")
    suspend fun increaseQuantity(itemId: Int)

    @Query("UPDATE cart_items SET quantity = quantity - 1 WHERE id = :itemId")
    suspend fun decreaseQuantity(itemId: Int)

    @Query("DELETE FROM cart_items WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int)

    @Query("SELECT * FROM cart_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): CartEntity?
}

// 3. THE DATABASE: The actual Room database instance
@Database(entities = [CartEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "grocery_cart_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
