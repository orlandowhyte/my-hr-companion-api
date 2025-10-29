package com.hr.companion.api.util;

import java.util.List;

public interface BaseMapper<Entity, Request, Response> {
    Response toResponse(Entity entity);
    Entity toEntity(Request request);
    List<Response> toResponseList(List<Entity> entities);
    List<Entity> toEntityList(List<Request> requests);
}
