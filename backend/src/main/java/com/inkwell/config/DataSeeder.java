package com.inkwell.config;

import com.inkwell.entity.Post;
import com.inkwell.entity.Role;
import com.inkwell.entity.User;
import com.inkwell.repository.PostRepository;
import com.inkwell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User author = userRepository.save(User.builder()
                    .username("writer")
                    .email("writer@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .displayName("Ada Writer")
                    .bio("Building stories and thoughtful ideas.")
                    .avatarUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=120&q=80")
                    .role(Role.CREATOR)
                    .build());

            postRepository.save(Post.builder()
                    .title("Welcome to Inkwell")
                    .subtitle("A polished starter for your blog")
                    .content("This is a seeded post to demonstrate the platform. You can edit it, publish it, or replace it with your own content.")
                    .published(true)
                    .author(author)
                    .build());
        }
    }
}
