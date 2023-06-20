package com.young.youngsecurity.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MyUserDetails implements UserDetails {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Set<String> authoritySet;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(this.authorities) && !CollectionUtils.isEmpty(this.authoritySet)) {
            this.authorities = this.authoritySet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
