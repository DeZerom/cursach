package ru.dezerom.interdiffer

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.dezerom.interdiffer.presentation.utils.toNearestInt

class UtilsTest {
    @Test
    fun toNearestIntTest() {
        assertEquals(1.51.toNearestInt(), 2)
        assert(0.49.toNearestInt() == 0)
    }
}