package com.masudbappy.helperforensics.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.masudbappy.helperforensics.controller.TokenNotFoundException;
import com.masudbappy.helperforensics.models.User;
import com.masudbappy.helperforensics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private User user;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(String token) throws Exception {
        try {
            System.out.println("Received Token");

            JacksonFactory jsonFactory = new JacksonFactory();
            final String CLIENT_ID = "605727931426-48h2lfc7avt702rn5k94hccema0hmrrp.apps.googleusercontent.com";


            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jsonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            // (Receive idTokenString by HTTPS POST)

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                // Use or store profile information
                // ...
                String tokenValue = generateToken();
                user = new User();
                user.setToken(tokenValue);
                user.setName(name);
                user.setEmail(email);
                user.setPictureUrl(pictureUrl);
                return userRepository.save(user);

            } else {
                System.out.println("Invalid ID token.");
            }

        } catch (Exception ex) {
            throw new TokenNotFoundException(("Token not found"), ex);
        }
        return user;
    }

    private String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }
}
