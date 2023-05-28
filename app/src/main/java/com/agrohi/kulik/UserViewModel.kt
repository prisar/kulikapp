package com.agrohi.kulik

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class UserViewModel(
    savedStateHandle: SavedStateHandle,
//    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    private val userId: String = checkNotNull(savedStateHandle["userId"])

    // Fetch the relevant user information from the data layer,
    // ie. userInfoRepository, based on the passed userId argument
//    private val userInfo: Flow<UserInfo> = userInfoRepository.getUserInfo(userId)
}