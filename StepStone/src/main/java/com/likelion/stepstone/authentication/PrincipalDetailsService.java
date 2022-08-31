package com.likelion.stepstone.authentication;

import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  PrincipalDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("PrincipalDetailsService의 loadUserByUsername()");

    UserEntity userEntity = userRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다."));

    return new PrincipalDetails(userEntity);
  }
}
