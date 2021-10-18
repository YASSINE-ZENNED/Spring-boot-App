package com.example.demo.appuser;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG="user with email %s  not found ";
    private final AppUserRespository appUserRespository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRespository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String singUpUser(AppUser appUser){
      boolean userExists = appUserRespository.findByEmail(appUser.getEmail()).isPresent();
        if(userExists){

            throw new IllegalStateException("email already taken");
        }


        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRespository.save(appUser);

        String token =UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
             token,
             LocalDateTime.now(),
             LocalDateTime.now().plusMinutes(15),
             appUser


        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO:SEND EMAIL
        return token;
    }
}
