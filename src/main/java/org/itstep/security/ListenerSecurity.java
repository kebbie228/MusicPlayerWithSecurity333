package org.itstep.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ListenerSecurity {

    public boolean checkUserId(Authentication authentication, int id) {
        // Получите текущего аутентифицированного слушателя
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();

        // Проверьте, что идентификатор в URL соответствует идентификатору аутентифицированного слушателя
        return listenerDetails.getListener().getId() == id;
    }
}