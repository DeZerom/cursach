package ru.dezerom.interdiffer.presentation.change_listener.comparison

object ComparisonsChangeListenersHolder {
    private val listeners = mutableListOf<ComparisonsChangeListener>()

    fun registerListener(listener: ComparisonsChangeListener) = listeners.add(listener)

    fun unregisterListener(listener: ComparisonsChangeListener) = listeners.remove(listener)

    fun triggerChange(payload: ComparisonsChangePayload) =
        listeners.forEach {
            it.onChange(payload)
        }
}