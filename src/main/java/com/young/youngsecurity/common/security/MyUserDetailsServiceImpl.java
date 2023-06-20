package com.young.youngsecurity.common.security;

import com.young.youngsecurity.entity.User;
import com.young.youngsecurity.service.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:
 * @author: Yang JianXiong
 * @since: 2023/6/18
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.userService.getUserByName(username))
                .map(this::buildMyUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Authenticate failed!"));
    }

    private MyUserDetails buildMyUserDetails(User user) {
        return new MyUserDetails()
                .setId(user.getId())
                .setUsername(user.getName())
                .setPassword(user.getPassword())
                .setEnabled(Boolean.TRUE)
                .setAccountNonExpired(Boolean.TRUE)
                .setAccountNonLocked(Boolean.TRUE)
                .setCredentialsNonExpired(Boolean.TRUE);
    }

}
