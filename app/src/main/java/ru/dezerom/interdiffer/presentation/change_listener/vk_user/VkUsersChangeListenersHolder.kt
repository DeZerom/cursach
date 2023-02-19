package ru.dezerom.interdiffer.presentation.change_listener.vk_user

object VkUsersChangeListenersHolder {

    private val listeners = mutableListOf<VkUsersChangesListener>()

    fun register(listener: VkUsersChangesListener) {
        listeners.add(listener)
    }

    fun unregister(listener: VkUsersChangesListener) {
        listeners.remove(listener)
    }

    fun triggerChange(payload: VkUsersPayload) {
        listeners.forEach { it.onUsersChange(payload) }
    }

}
