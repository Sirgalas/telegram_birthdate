package ru.sergalas.admin.mapper;

import org.mapstruct.Mapper;
import ru.sergalas.admin.client.data.register.create.CreateRequestData;
import ru.sergalas.admin.client.data.register.create.CreateResponseData;
import ru.sergalas.admin.client.data.register.update.UpdateRequestData;
import ru.sergalas.admin.client.data.register.update.UpdateResponseData;
import ru.sergalas.admin.client.data.user.view.OneUserData;
import ru.sergalas.admin.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromCreateResponse(CreateResponseData createResponseData);
    User fromUpdateResponse(UpdateResponseData createResponseData);
    User fromOneInfo(OneUserData oneUserData);

    CreateRequestData toCreateRequest(User user);
    UpdateRequestData toUpdateRequest(User user);
}
