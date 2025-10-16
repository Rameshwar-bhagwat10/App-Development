package com.example.agrokrishiseva.data

import com.example.agrokrishiseva.models.User
import com.example.agrokrishiseva.models.UserStats
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserRepository {
    
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val usersCollection = firestore.collection("users")
    
    suspend fun getCurrentUser(): User? {
        return try {
            val currentUser = auth.currentUser ?: return null
            val document = usersCollection.document(currentUser.uid).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun updateUser(user: User): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            usersCollection.document(currentUser.uid).set(user, SetOptions.merge()).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun createUserProfile(user: User): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            val userWithUid = user.copy(
                uid = currentUser.uid,
                email = currentUser.email ?: user.email,
                isEmailVerified = currentUser.isEmailVerified
            )
            usersCollection.document(currentUser.uid).set(userWithUid).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun updateLastLoginDate(): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            usersCollection.document(currentUser.uid)
                .update("lastLoginDate", System.currentTimeMillis()).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun incrementTipsViewed(): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            val userDoc = usersCollection.document(currentUser.uid)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userDoc)
                val currentCount = snapshot.getLong("totalTipsViewed") ?: 0
                transaction.update(userDoc, "totalTipsViewed", currentCount + 1)
            }.await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun incrementProductsViewed(): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            val userDoc = usersCollection.document(currentUser.uid)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userDoc)
                val currentCount = snapshot.getLong("totalProductsViewed") ?: 0
                transaction.update(userDoc, "totalProductsViewed", currentCount + 1)
            }.await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun addBookmarkedTip(tipId: String): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            val userDoc = usersCollection.document(currentUser.uid)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userDoc)
                val currentBookmarks = snapshot.get("bookmarkedTips") as? List<String> ?: emptyList()
                val updatedBookmarks = currentBookmarks.toMutableList()
                if (!updatedBookmarks.contains(tipId)) {
                    updatedBookmarks.add(tipId)
                }
                transaction.update(userDoc, "bookmarkedTips", updatedBookmarks)
            }.await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun removeBookmarkedTip(tipId: String): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            val userDoc = usersCollection.document(currentUser.uid)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userDoc)
                val currentBookmarks = snapshot.get("bookmarkedTips") as? List<String> ?: emptyList()
                val updatedBookmarks = currentBookmarks.toMutableList()
                updatedBookmarks.remove(tipId)
                transaction.update(userDoc, "bookmarkedTips", updatedBookmarks)
            }.await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun getUserStats(): UserStats {
        return try {
            val user = getCurrentUser()
            if (user != null) {
                val joinDate = user.joinDate
                val currentTime = System.currentTimeMillis()
                val daysActive = ((currentTime - joinDate) / (1000 * 60 * 60 * 24)).toInt()
                
                UserStats(
                    tipsViewed = user.totalTipsViewed,
                    productsViewed = user.totalProductsViewed,
                    bookmarkedTips = user.bookmarkedTips.size,
                    favoriteProducts = user.favoriteProducts.size,
                    daysActive = maxOf(1, daysActive),
                    achievementsUnlocked = user.achievements.size
                )
            } else {
                UserStats()
            }
        } catch (e: Exception) {
            UserStats()
        }
    }
    
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
    
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
    
    fun signOut() {
        auth.signOut()
    }
    
    suspend fun uploadProfileImage(imageData: ByteArray): String? {
        return try {
            val currentUser = auth.currentUser ?: return null
            val imageRef = storage.reference
                .child("profile_images")
                .child("${currentUser.uid}.jpg")
            
            val uploadTask = imageRef.putBytes(imageData).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await()
            
            // Update user profile with new image URL
            usersCollection.document(currentUser.uid)
                .update("profileImageUrl", downloadUrl.toString()).await()
            
            downloadUrl.toString()
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun deleteProfileImage(): Boolean {
        return try {
            val currentUser = auth.currentUser ?: return false
            val imageRef = storage.reference
                .child("profile_images")
                .child("${currentUser.uid}.jpg")
            
            // Delete from storage
            imageRef.delete().await()
            
            // Update user profile to remove image URL
            usersCollection.document(currentUser.uid)
                .update("profileImageUrl", "").await()
            
            true
        } catch (e: Exception) {
            false
        }
    }
}