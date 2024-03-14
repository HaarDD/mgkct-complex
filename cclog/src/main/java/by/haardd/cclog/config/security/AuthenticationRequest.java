package by.haardd.cclog.config.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    private String login;
    private String password;

}
