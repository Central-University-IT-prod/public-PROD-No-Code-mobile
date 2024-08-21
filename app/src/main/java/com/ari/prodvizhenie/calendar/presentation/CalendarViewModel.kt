package com.ari.prodvizhenie.calendar.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ari.prodvizhenie.auth.presentation.login_screen.PostViewState
import com.ari.prodvizhenie.calendar.domain.model.PostInfo
import com.ari.prodvizhenie.calendar.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _organizationsState = MutableStateFlow(OrganizationsViewState())
    val organizationsState = _organizationsState.asStateFlow()

    private val _postsState = MutableStateFlow(PostViewState())
    val postsState = _postsState.asStateFlow()


    fun getPostsByDate(token: String, date: LocalDate) {
        viewModelScope.launch {
            _postsState.update { it.copy(isLoading = true) }

            val result = ArrayList<PostInfo>()
            postRepository.getOrganizations(token)
                .onRight {
                    it.data.forEach { data ->
                        postRepository.getPostByDateRange(
                            token = "Bearer $token",
                            orgId = data.id,
                            date = localDateToUnixTime(date)
                        )
                            .onRight { resp ->
                                Log.d("TAG", "getPostsByDate: ${resp}")
                                resp.data.forEach { post ->
                                    result.add(post)
                                }
                                result.sortBy { inf ->
                                    inf.upload_date
                                }
                            }
                            .onLeft { err ->
                                Log.d("TAG", "getPostsByDate: $err")
                                //TODO handle error + in whole project
                            }
                    }
                }
                .onLeft { error ->
                    _organizationsState.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    Log.d("TAG", "getPostsByDate: $error")
                }

            _postsState.update { it.copy(posts = result) }
            _postsState.update { it.copy(isLoading = false) }
        }
    }

    private fun localDateToUnixTime(localDate: LocalDate): Long {
        val epochDay = localDate.toEpochDay()
        return epochDay * 86400 // 86400 seconds in a day
    }
}