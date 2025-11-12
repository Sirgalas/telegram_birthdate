package ru.sergalas.admin.client;

import org.springframework.web.bind.annotation.PathVariable;
import ru.sergalas.admin.client.data.user.view.ListUserData;
import ru.sergalas.admin.client.data.user.view.OneUserData;

public interface UserClient {
    public ListUserData getIndex(String userName,Integer first,Integer count);
    public OneUserData getOneUser(String id);
}
