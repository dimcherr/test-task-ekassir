package pics.phocus.testtaskekassir.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_order")
data class VehicleOrder(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @Embedded(prefix = "start_address_")
    val startAddress: Address,

    @Embedded(prefix = "end_address_")
    val endAddress: Address,

    @Embedded(prefix = "price_")
    val price: Price,

    val orderTime: String,

    @Embedded(prefix = "vehicle_")
    val vehicle: Vehicle
)