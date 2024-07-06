package com.example.project_si.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_si.model.Dataset
import com.example.project_si.repository.FirebaseRepository
import com.example.project_si.utils.ui.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DatasetViewModel : ViewModel() {

    private val repository = FirebaseRepository()

    private val _datasets = MutableStateFlow<List<Dataset>>(emptyList())
    val datasets: StateFlow<List<Dataset>> get() = _datasets

    private val _currentDataset = MutableStateFlow(Dataset())
    val currentDataset: StateFlow<Dataset> get() = _currentDataset

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> get() = _currentScreen

    init {
        loadDatasets()
    }

    private fun loadDatasets() {
        viewModelScope.launch {
            repository.getDatasets(
                onSuccess = { datasets ->
                    _datasets.value = datasets.filter { it.nama_project.isNotBlank() } // Filter out empty datasets
                },
                onFailure = { /* Handle error */ }
            )
        }
    }

    fun onDatasetSelected(dataset: Dataset) {
        _currentDataset.value = dataset
        navigateToScreen(Screen.Project) // Update this to the appropriate screen if needed
    }

    fun onCreateDatasetClicked() {
        _currentDataset.value = Dataset()
        navigateToScreen(Screen.Project) // Update this to the appropriate screen if needed
    }

    fun updateCurrentDataset(dataset: Dataset) {
        _currentDataset.value = dataset
    }

    fun updateCurrentDataset(
        id: String? = _currentDataset.value.id,
        nama_project: String = _currentDataset.value.nama_project,
        fileUrl: String = _currentDataset.value.fileUrl
    ) {
        _currentDataset.value = _currentDataset.value.copy(
            id = id,
            nama_project = nama_project,
            fileUrl = fileUrl
        )
    }

    fun navigateToScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    fun createDataset() {
        viewModelScope.launch {
            repository.createDataset(
                dataset = _currentDataset.value,
                onSuccess = { loadDatasets() },
                onFailure = { /* Handle error */ }
            )
        }
    }
}
