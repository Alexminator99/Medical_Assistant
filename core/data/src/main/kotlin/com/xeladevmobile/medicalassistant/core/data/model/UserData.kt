package com.xeladevmobile.medicalassistant.core.data.model

import com.xeladevmobile.medicalassistant.core.model.data.DarkThemeConfig
import com.xeladevmobile.medicalassistant.core.model.data.ThemeBrand
import com.xeladevmobile.medicalassistant.core.model.data.UserData
import com.xeladevmobile.medicalassistant.core.network.model.NetworkUser

fun NetworkUser.asUserData() = UserData(
    themeBrand = ThemeBrand.DEFAULT,
    darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    name = this.name,
    sex = this.sex,
    bornDate = this.bornDate,
    address = this.address,
    userType = this.userType,
    problemDescription = this.problemDescription,
    treatmentDate = this.treatmentDate,
    specialty = this.specialty,
    graduationDate = this.graduationDate,
    experience = this.experience,
    occupation = this.occupation,
    useDynamicColor = false,
    shouldHideOnboarding = false,
    patientId = this.patientId,
    doctorId = this.doctorId,
)