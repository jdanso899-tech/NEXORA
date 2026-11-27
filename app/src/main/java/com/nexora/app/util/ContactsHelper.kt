package com.nexora.app.util

import android.content.Context
import android.provider.ContactsContract
import com.nexora.app.data.model.Friend

/**
 * Reads phone contacts and returns a simple list of Friend objects.
 * In a full app you would normalize numbers and match against registered NEXORA users.
 */
object ContactsHelper {

    fun loadContacts(context: Context): List<Friend> {
        val result = mutableListOf<Friend>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        try {
            context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )?.use { cursor ->
                val nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                while (cursor.moveToNext()) {
                    val name = cursor.getString(nameIdx) ?: continue
                    val number = cursor.getString(numberIdx)?.filter { it.isDigit() || it == '+' } ?: continue
                    if (number.length < 7) continue
                    result.add(
                        Friend(
                            phoneNumber = number,
                            displayName = name,
                            isNexoraUser = false // real matching would set this
                        )
                    )
                }
            }
        } catch (e: SecurityException) {
            // Permission not granted
        }
        return result.distinctBy { it.phoneNumber }.take(50)
    }
}
