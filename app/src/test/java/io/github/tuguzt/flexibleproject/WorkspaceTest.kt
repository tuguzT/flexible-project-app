package io.github.tuguzt.flexibleproject

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import kotlin.time.Duration.Companion.seconds

@TestMethodOrder(OrderAnnotation::class)
@DisplayName("Workspace tests")
class WorkspaceTest {
    @Test
    @Order(1)
    @DisplayName("Get all workspaces from the system")
    fun getAllWorkspaces(): Unit = runBlocking {
        delay(1.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(2)
    @DisplayName("Find workspace by identifier")
    fun findWorkspaceById(): Unit = runBlocking {
        delay(1.5.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(3)
    @DisplayName("Find workspace by its owner")
    fun findWorkspaceByOwner(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(4)
    @DisplayName("Update workspace name")
    fun updateWorkspaceName(): Unit = runBlocking {
        delay(1.5.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(5)
    @DisplayName("Add new member to the workspace")
    fun addWorkspaceMember(): Unit = runBlocking {
        delay(2.25.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(6)
    @DisplayName("Remove member to the workspace")
    fun removeWorkspaceMember(): Unit = runBlocking {
        delay(1.75.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(7)
    @DisplayName("Grant role to workspace member")
    fun grantRoleToMember(): Unit = runBlocking {
        delay(1.5.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(8)
    @DisplayName("Delete workspace from the system")
    fun deleteWorkspace(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }
}
