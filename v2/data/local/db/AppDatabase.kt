package tamhoang.ldpro4.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tamhoang.ldpro4.v2.data.local.db.dao.ChatDao
import tamhoang.ldpro4.v2.data.local.db.dao.ChayTrangAccDao
import tamhoang.ldpro4.v2.data.local.db.dao.ChayTrangAccWinDao
import tamhoang.ldpro4.v2.data.local.db.dao.ChayTrangTicketDao
import tamhoang.ldpro4.v2.data.local.db.dao.ChuyenThangDao
import tamhoang.ldpro4.v2.data.local.db.dao.KetQuaDao
import tamhoang.ldpro4.v2.data.local.db.dao.KhachHangNewDao
import tamhoang.ldpro4.v2.data.local.db.dao.SettingDao
import tamhoang.ldpro4.v2.data.local.db.dao.SoCTSDao
import tamhoang.ldpro4.v2.data.local.db.dao.SoOmDao
import tamhoang.ldpro4.v2.data.local.db.dao.ThayThePhuDao
import tamhoang.ldpro4.v2.data.local.db.dao.TinNhanDao
import tamhoang.ldpro4.v2.data.local.db.entity.ChatEntity
import tamhoang.ldpro4.v2.data.local.db.entity.ChayTrangAccEntity
import tamhoang.ldpro4.v2.data.local.db.entity.ChayTrangAccWinEntity
import tamhoang.ldpro4.v2.data.local.db.entity.ChayTrangTicketEntity
import tamhoang.ldpro4.v2.data.local.db.entity.ChuyenThangEntity
import tamhoang.ldpro4.v2.data.local.db.entity.KetQuaEntity
import tamhoang.ldpro4.v2.data.local.db.entity.KhachHangNewEntity
import tamhoang.ldpro4.v2.data.local.db.entity.SettingEntity
import tamhoang.ldpro4.v2.data.local.db.entity.SoCTSEntity
import tamhoang.ldpro4.v2.data.local.db.entity.SoOmEntity
import tamhoang.ldpro4.v2.data.local.db.entity.ThayThePhuEntity
import tamhoang.ldpro4.v2.data.local.db.entity.TinNhanEntity

@Database(
    entities = [
        ChatEntity::class,
        ChayTrangAccEntity::class,
        ChayTrangAccWinEntity::class,
        ChayTrangTicketEntity::class,
        ChuyenThangEntity::class,
        KetQuaEntity::class,
        KhachHangNewEntity::class,
        SettingEntity::class,
        SoCTSEntity::class,
        SoOmEntity::class,
        ThayThePhuEntity::class,
        TinNhanEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun chayTrangAccDao(): ChayTrangAccDao
    abstract fun chayTrangAccWinDao(): ChayTrangAccWinDao
    abstract fun chayTrangTicketDao(): ChayTrangTicketDao
    abstract fun chuyenThangDao(): ChuyenThangDao
    abstract fun ketQuaDao(): KetQuaDao
    abstract fun khachHangNewDao(): KhachHangNewDao
    abstract fun settingDao(): SettingDao
    abstract fun soCTSDao(): SoCTSDao
    abstract fun soOmDao(): SoOmDao
    abstract fun thayThePhuDao(): ThayThePhuDao
    abstract fun tinNhanDao(): TinNhanDao

    companion object {
        private const val DATABASE_NAME = "win6868.db"
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}