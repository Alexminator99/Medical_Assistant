/*
 * Copyright 2022 Medical Assistant
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xeladevmobile.medicalassistant.core.data.di

import com.xeladevmobile.medicalassistant.core.data.repository.AudioRecordsRepository
import com.xeladevmobile.medicalassistant.core.data.repository.OfflineFirstAudioRecordsRepository
import com.xeladevmobile.medicalassistant.core.data.repository.OfflineFirstUserDataRepository
import com.xeladevmobile.medicalassistant.core.data.repository.UserDataRepository
import com.xeladevmobile.medicalassistant.core.data.util.ConnectivityManagerNetworkMonitor
import com.xeladevmobile.medicalassistant.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindAudioRepository(
        audioRepository: OfflineFirstAudioRecordsRepository,
    ): AudioRecordsRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
