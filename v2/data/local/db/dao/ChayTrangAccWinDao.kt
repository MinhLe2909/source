package tamhoang.ldpro4.v2.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tamhoang.ldpro4.v2.data.local.db.entity.ChatEntity

@Dao
interface ChayTrangAccWinDao {

    @Query("select * from tb_chat")
    fun getChatList(): List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetChat(chatEntity: ChatEntity)

}