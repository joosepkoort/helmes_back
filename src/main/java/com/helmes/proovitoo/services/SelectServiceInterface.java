package com.helmes.proovitoo.services;

import com.helmes.proovitoo.model.Data;
import java.security.Principal;

public interface SelectServiceInterface {
    String loadTree();
    String loadDataForUser(Principal principal);
    Data updateUserData(Data inputData, Principal principal);
}
