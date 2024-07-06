package com.example.project_si.repository

import android.net.Uri
import com.example.project_si.model.Dataset
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.UUID

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference

    fun getDatasets(
        onSuccess: (List<Dataset>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("datasets")
            .get()
            .addOnSuccessListener { result ->
                val datasets = result.map { it.toObject(Dataset::class.java) }
                onSuccess(datasets)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun createDataset(
        dataset: Dataset,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("datasets")
            .add(dataset)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun uploadFile(
        fileUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val fileName = UUID.randomUUID().toString()
        val fileRef = storageRef.child("uploads/$fileName")

        fileRef.putFile(fileUri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }.addOnFailureListener { exception ->
                    onFailure(exception)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun createDatasetWithFile(
        dataset: Dataset,
        fileUri: Uri,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        uploadFile(fileUri,
            onSuccess = { fileUrl ->
                val newDataset = dataset.copy(fileUrl = fileUrl)
                createDataset(newDataset, onSuccess, onFailure)
            },
            onFailure = onFailure
        )
    }

    fun downloadFile(
        dataset: Dataset,
        onSuccess: (Uri) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val fileRef = storage.getReferenceFromUrl(dataset.fileUrl)
        val localFile = File.createTempFile("tempFile", ".pkl")

        fileRef.getFile(localFile)
            .addOnSuccessListener {
                onSuccess(Uri.fromFile(localFile))
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
