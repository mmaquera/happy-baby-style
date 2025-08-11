import android.os.Build
import androidx.annotation.RequiresApi
import com.apollographql.apollo.api.Adapter
import com.apollographql.apollo.api.CustomScalarAdapters
import com.apollographql.apollo.api.json.JsonReader
import com.apollographql.apollo.api.json.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeAdapter : Adapter<LocalDateTime> {

    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    override fun fromJson(
        reader: JsonReader,
        customScalarAdapters: CustomScalarAdapters
    ): LocalDateTime {
        return LocalDateTime.parse(reader.nextString(), formatter)
    }

    override fun toJson(
        writer: JsonWriter,
        customScalarAdapters: CustomScalarAdapters,
        value: LocalDateTime
    ) {
        writer.value(value.format(formatter))
    }
}