package gugunava.danil.usermanagementservice.service;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.model.UserDetailsImpl;
import gugunava.danil.usermanagementservice.repository.RoleRepository;
import gugunava.danil.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with this email: '%s' is not registered.", username)));
        List<SimpleGrantedAuthority> authorities = roleRepository.findAllByUserId(userEntity.getId()).stream()
                .map(role -> new SimpleGrantedAuthority(role.getScope()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}
