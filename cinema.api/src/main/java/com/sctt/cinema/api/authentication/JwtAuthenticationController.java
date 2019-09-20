package com.sctt.cinema.api.authentication;


import com.sctt.cinema.api.business.entity.DTO.LoginDTO;
import com.sctt.cinema.api.business.entity.DTO.UserDTO;
import com.sctt.cinema.api.business.service.jpa.UserService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Log4j2
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public BaseResponse createAuthenticationToken(@RequestBody JwtRequest req) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username, req.password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(req.username);

            LoginDTO response = new LoginDTO();

            String token = jwtTokenUtil.generateToken(userDetails);
            UserDTO user = new UserDTO();
            user.clone(userService.findById(req.username));

            response.token = token;
            response.user = user;

            return BaseResponse.setResponse(ReturnCodeEnum.SUCCESS, response);

        } catch (BadCredentialsException e){
            return new BaseResponse(ReturnCodeEnum.WRONG_USERNAME_OR_PASSWORD);

        } catch (Exception e){
            log.error(e);
            return new BaseResponse(ReturnCodeEnum.EXCEPTION);
        }
    }
}