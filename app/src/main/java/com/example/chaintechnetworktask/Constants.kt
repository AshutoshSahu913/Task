package com.example.chaintechnetworktask

import android.annotation.SuppressLint
import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

const val DATABASE_NAME="AppDatabase"
const val TABLE_NAME="SavedPassword"


// Function to generate AES key using SHA-256
fun generateAESKey(accountName: String): SecretKeySpec {
    val digest = MessageDigest.getInstance("SHA-256")
    val keyBytes = digest.digest(accountName.toByteArray(Charsets.UTF_8))
    return SecretKeySpec(keyBytes, "AES")
}

fun encrypt(string: String, key: SecretKeySpec): String {
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptBytes = cipher.doFinal(string.toByteArray(Charsets.UTF_8))
    return Base64.encodeToString(encryptBytes, Base64.DEFAULT)
}

@SuppressLint("GetInstance")
fun decrypt(string: String, key: SecretKeySpec): String? {
    try {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedBytes = cipher.doFinal(Base64.decode(string, Base64.DEFAULT))
        return String(decryptedBytes, Charsets.UTF_8)
    } catch (e: Exception) {
        e.printStackTrace()
        return e.localizedMessage
    }
}