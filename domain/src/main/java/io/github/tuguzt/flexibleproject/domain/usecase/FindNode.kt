package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.Node

public interface FindNode {
    public suspend fun find(id: Id<*>): Node?
}
