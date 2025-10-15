package vn.hoidanit.laptopshop.service.validator;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // logic
        vn.hoidanit.laptopshop.domain.User user = this.userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        // tính đa hình của java
        // chỉ cần trả ra User vì nó cũng là 1 loại của UserDetails, nó sẽ tự ép kiểu dữ
        // liệu
        // chỉ cần truyền vào username, password, quyền hạn
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        // tạo 1 list, truyền vào list duy nhất 1 phần tử, gán role
    }

}
