package com.xeladevmobile.medicalassistant.core.data.test

import com.xeladevmobile.medicalassistant.core.data.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AlwaysOnlineNetworkMonitor @Inject constructor() : NetworkMonitor {
    override val isOnline: Flow<Boolean> = flowOf(true)
}
