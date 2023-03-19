package ru.dezerom.interdiffer.data.network.type_adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import ru.dezerom.interdiffer.data.models.VkSocietyDataModel

class SocietyTypeAdapter: TypeAdapter<VkSocietyDataModel?>() {

    override fun write(out: JsonWriter?, value: VkSocietyDataModel?) {
        //no need to write it
    }

    override fun read(`in`: JsonReader?): VkSocietyDataModel? {
        val reader = `in` ?: return null

        var society = VkSocietyDataModel()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.peek()) {
                JsonToken.NAME -> {
                    when (reader.nextName()) {
                        ID -> society = society.copy(id = reader.nextInt())
                        NAME -> society = society.copy(name = reader.nextString())
                        IS_CLOSED -> society = society.copy(isClosed = reader.nextInt())
                        DEACTIVATED -> society = society.copy(deactivated = reader.nextString())
                        PHOTO_100 -> society = society.copy(photo100 = reader.nextString())
                        PHOTO_200 -> society = society.copy(photo200 = reader.nextString())
                        ACTIVITY -> society = society.copy(activity = reader.nextString())
                        AGE_LIMITS -> society = society.copy(ageLimits = reader.nextInt())
                        DESCRIPTION -> society = society.copy(description = reader.nextString())

                        TYPE -> {
                            val value = reader.nextString()

                            if (value == "profile") {
                                skipToTheEnd(reader)
                                return null
                            } else {
                                society = society.copy(type = value)
                            }
                        }

                        else -> reader.skipValue()
                    }
                }

                JsonToken.BEGIN_OBJECT -> reader.beginObject()
                JsonToken.END_OBJECT -> reader.endObject()

                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return society
    }

    private fun skipToTheEnd(reader: JsonReader) {
        while (reader.peek() != JsonToken.END_OBJECT)
            reader.skipValue()

        reader.endObject()
    }

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val IS_CLOSED = "is_closed"
        const val DEACTIVATED = "deactivated"
        const val TYPE = "type"
        const val PHOTO_100 = "photo_100"
        const val PHOTO_200 = "photo_200"
        const val ACTIVITY = "activity"
        const val AGE_LIMITS = "age_limits"
        const val DESCRIPTION = "description"
    }
}
