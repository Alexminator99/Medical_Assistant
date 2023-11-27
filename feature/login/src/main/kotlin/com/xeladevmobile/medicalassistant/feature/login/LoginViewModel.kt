/*
 * Copyright © 2023 Aptimyz Retail LTD. All Rights Reserved.
 *
 * This software is the proprietary information of Aptimyz Retail LTD.
 * Use, reproduction, disclosure, or distribution of this software and related
 * documentation without an express written agreement from Aptimyz Retail LTD is strictly prohibited.
 *
 * This software may incorporate open-source software, the use of which is
 * governed by their respective licenses as designated in the included
 * OSS-licenses documentation generated by the oss-licenses-plugin.
 *
 * This software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
 * except as required by applicable law.
 *
 * The software is available on Clover Market, soon to be available on Apple App Store and Google Play.
 */

package com.xeladevmobile.medicalassistant.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xeladevmobile.medicalassistant.core.data.model.asUserData
import com.xeladevmobile.medicalassistant.core.data.repository.UserDataRepository
import com.xeladevmobile.medicalassistant.core.domain.InsertPatientUseCase
import com.xeladevmobile.medicalassistant.core.network.model.networkUserForTestWithDoctor
import com.xeladevmobile.medicalassistant.core.network.model.networkUserForTestWithPatient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val insertPatientUseCase: InsertPatientUseCase,
) : ViewModel() {
    private val shouldShowOnboarding: Flow<Boolean> =
        userDataRepository.userData.map { !it.shouldHideOnboarding }

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun doLogin(username: String, password: String) {
        // TODO: For later development
        viewModelScope.launch {
            _uiState.emit(LoginUiState.Loading)
            delay(2000)
            if (username == "alexminator@gmail.com" && password == "12345678") {
                userDataRepository.setUserFromNetwork(networkUserForTestWithPatient.asUserData())
                insertPatientUseCase(networkUserForTestWithPatient.asUserData())
                _uiState.emit(LoginUiState.Success(networkUserData = networkUserForTestWithPatient))
            } else if (username == "josefeliciano" && password == "12345678") {
                userDataRepository.setUserFromNetwork(networkUserForTestWithDoctor.asUserData())
                insertPatientUseCase(networkUserForTestWithDoctor.asUserData())
                _uiState.emit(LoginUiState.Success(networkUserData = networkUserForTestWithDoctor))
            } else {
                _uiState.emit(LoginUiState.Error("Usuario o contraseña incorrectos"))
            }
        }
    }

    fun errorDismissed() {
        viewModelScope.launch {
            _uiState.emit(LoginUiState.Initial)
        }
    }
}
