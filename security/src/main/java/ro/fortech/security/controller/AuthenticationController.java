package ro.fortech.security.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.fortech.security.model.AuthenticationModel;
import ro.fortech.security.model.TokenModel;
import ro.fortech.security.service.JwtService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/token")
    //@PreFilter("filterObject.username == 'Paul'")
    public ResponseEntity<TokenModel> getTokens(@RequestBody AuthenticationModel model){
      try {
          TokenModel tokenModel = jwtService.getTokens(model);
          return ResponseEntity.ok().body(tokenModel);
      } catch (BadCredentialsException | UsernameNotFoundException e){
          System.err.println(e.getClass() + " " + e.getMessage());
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    }

    @PostMapping("/refresh")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TokenModel> getAccessToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        TokenModel tokenModel = jwtService.getAccessToken(token.substring(7));
        return ResponseEntity.ok().body(tokenModel);
    }
}
