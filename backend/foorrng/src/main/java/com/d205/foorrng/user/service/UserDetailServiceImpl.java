package com.d205.foorrng.user.service;


import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.entity.UserDetailsImpl;
import com.d205.foorrng.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String userUid) throws UsernameNotFoundException {

        User user = userRepository.findByUserUid(Long.parseLong(userUid))
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 존재하지 않습니다.: " + userUid));
        if (user != null) {
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            return userDetails;
        }

        return null;
    }
}

