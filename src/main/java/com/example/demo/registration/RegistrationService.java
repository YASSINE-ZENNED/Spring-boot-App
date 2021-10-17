package com.example.demo.registration;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRole;
import com.example.demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValitadtor emailValitadtor;
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
    boolean   isValidEmail= emailValitadtor.test(request.getEmail());
    if(!isValidEmail){
        throw new IllegalStateException("email not valid");
    }
        return  appUserService.singUpUser( new AppUser

                        (
                            request.getFirstname(),
                            request.getLastname(),
                            request.getEmail(),
                            request.getPassword(),
                                AppUserRole.USER
                        )
        );
    }
}
