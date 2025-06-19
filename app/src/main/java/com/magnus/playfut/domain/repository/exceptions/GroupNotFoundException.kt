package com.magnus.playfut.domain.repository.exceptions

class GroupNotFoundException : Exception() {
    override val message: String?
        get() = "Group not found"
}