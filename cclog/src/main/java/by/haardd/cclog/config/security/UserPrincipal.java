package by.haardd.cclog.config.security;

import by.haardd.cclog.entity.User;
import by.haardd.cclog.entity.enums.UserStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getName().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !UserStatusEnum.LOGIN_EXPIRED.equals(user.getUserStatus().getName());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !UserStatusEnum.BLOCKED.equals(user.getUserStatus().getName());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !UserStatusEnum.PASSWORD_EXPIRED.equals(user.getUserStatus().getName());
    }

    @Override
    public boolean isEnabled() {
        return UserStatusEnum.OK.equals(user.getUserStatus().getName());
    }
}
