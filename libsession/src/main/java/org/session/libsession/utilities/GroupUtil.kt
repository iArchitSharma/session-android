package org.session.libsession.utilities

import org.session.libsignal.service.api.messages.SignalServiceGroup
import org.session.libsignal.utilities.Hex

object GroupUtil {
    const val CLOSED_GROUP_PREFIX = "__textsecure_group__!"
    const val MMS_GROUP_PREFIX = "__signal_mms_group__!"
    const val OPEN_GROUP_PREFIX = "__loki_public_chat_group__!"

    @JvmStatic
    fun getEncodedOpenGroupID(groupID: ByteArray): String {
        return OPEN_GROUP_PREFIX + Hex.toStringCondensed(groupID)
    }

    @JvmStatic
    fun getEncodedClosedGroupID(groupID: ByteArray): String {
        return CLOSED_GROUP_PREFIX + Hex.toStringCondensed(groupID)
    }

    @JvmStatic
    fun getEncodedMMSGroupID(groupID: ByteArray): String {
        return MMS_GROUP_PREFIX + Hex.toStringCondensed(groupID)
    }

    @JvmStatic
    fun getEncodedId(group: SignalServiceGroup): String {
        val groupId = group.groupId
        if (group.groupType == SignalServiceGroup.GroupType.PUBLIC_CHAT) {
            return getEncodedOpenGroupID(groupId)
        }
        return getEncodedClosedGroupID(groupId)
    }

    private fun splitEncodedGroupID(groupID: String): String {
        if (groupID.split("!").count() > 1) {
            return groupID.split("!", limit = 2)[1]
        }
        return groupID
    }

    @JvmStatic
    fun getDecodedGroupID(groupID: String): String {
        return String(getDecodedGroupIDAsData(groupID))
    }

    @JvmStatic
    fun getDecodedGroupIDAsData(groupID: String): ByteArray {
        return Hex.fromStringCondensed(splitEncodedGroupID(groupID))
    }

    fun isEncodedGroup(groupId: String): Boolean {
        return groupId.startsWith(CLOSED_GROUP_PREFIX) || groupId.startsWith(MMS_GROUP_PREFIX) || groupId.startsWith(OPEN_GROUP_PREFIX)
    }

    @JvmStatic
    fun isMmsGroup(groupId: String): Boolean {
        return groupId.startsWith(MMS_GROUP_PREFIX)
    }

    fun isOpenGroup(groupId: String): Boolean {
        return groupId.startsWith(OPEN_GROUP_PREFIX)
    }

    fun isClosedGroup(groupId: String): Boolean {
        return groupId.startsWith(CLOSED_GROUP_PREFIX)
    }
}