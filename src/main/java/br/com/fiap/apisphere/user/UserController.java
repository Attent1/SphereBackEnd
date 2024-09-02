package br.com.fiap.apisphere.user;

import br.com.fiap.apisphere.user.dto.UserProfileResponse;
import br.com.fiap.apisphere.user.dto.UserRequest;
import br.com.fiap.apisphere.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest, UriComponentsBuilder uriBuilder) {
        var user = service.create(userRequest.toModel());
        var uri = uriBuilder
                 .path("/users/{id}")
                 .buildAndExpand(user.getId())
                 .toUri();

        return ResponseEntity
                .created(uri)
                .body(UserResponse.from(user));
    }

    @GetMapping("profile")
    public UserProfileResponse getProfile(){
        //pegar o user logado
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return service.getProfile(email);
    }

    @PostMapping("avatar")
    public void uploadAvatar(@RequestBody MultipartFile file){
        // receber a foto

        // verificar se o arquivo existe
        if(file.isEmpty()){
            throw new RuntimeException("Archive cannot be empty");
        }

        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Path destinationRoot = Path.of("src/main/resources/static/avatars/");
        Path destinationFile = destinationRoot.resolve(
                Paths.get(System.currentTimeMillis() + email + file.getOriginalFilename())
        )
                .normalize()
                .toAbsolutePath();

        try(InputStream is = file.getInputStream()) {
            Files.copy(is, destinationFile);
            System.out.println("File copied" );

            var avatarURL = "http://localhost:8080/avatars/" + file.getOriginalFilename();
            service.updateAvatar(email, avatarURL);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
