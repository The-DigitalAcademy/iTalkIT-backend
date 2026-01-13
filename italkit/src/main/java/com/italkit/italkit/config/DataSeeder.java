package com.italkit.italkit.config;

import com.italkit.italkit.models.Post;
import com.italkit.italkit.models.User;
import com.italkit.italkit.repositories.PostRepository;
import com.italkit.italkit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists - THIS IS IMPORTANT WITH 'update'
        if (userRepository.count() > 0) {
            System.out.println("Database already contains data. Skipping seeding...");
            return;
        }

        System.out.println("Seeding database...");

        // Create Users
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setEmail("john@example.com");
        user1.setPassword("password123");
        user1.setBio("Software developer and tech enthusiast");
        user1.setProfilePicture("https://i.pravatar.cc/150?img=1");

        User user2 = new User();
        user2.setUsername("jane_smith");
        user2.setEmail("jane@example.com");
        user2.setPassword("password123");
        user2.setBio("Designer & creative thinker");
        user2.setProfilePicture("https://i.pravatar.cc/150?img=5");

        User user3 = new User();
        user3.setUsername("mike_wilson");
        user3.setEmail("mike@example.com");
        user3.setPassword("password123");
        user3.setBio("Marketing professional | Coffee lover ☕");
        user3.setProfilePicture("https://i.pravatar.cc/150?img=12");

        User user4 = new User();
        user4.setUsername("sarah_jones");
        user4.setEmail("sarah@example.com");
        user4.setPassword("password123");
        user4.setBio("Travel blogger | Photography 📸");
        user4.setProfilePicture("https://i.pravatar.cc/150?img=20");

        User user5 = new User();
        user5.setUsername("alex_brown");
        user5.setEmail("alex@example.com");
        user5.setPassword("password123");
        user5.setBio("Fitness coach & nutrition expert");
        user5.setProfilePicture("https://i.pravatar.cc/150?img=33");

        // Save users
        List<User> users = userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5));

        // Set up followers
        user1.getFollowing().add(user2);
        user1.getFollowing().add(user3);
        user1.getFollowing().add(user4);

        user2.getFollowing().add(user1);
        user2.getFollowing().add(user5);

        user3.getFollowing().add(user1);
        user3.getFollowing().add(user2);
        user3.getFollowing().add(user4);
        user3.getFollowing().add(user5);

        user4.getFollowing().add(user1);
        user4.getFollowing().add(user3);

        user5.getFollowing().add(user2);
        user5.getFollowing().add(user3);
        user5.getFollowing().add(user4);

        userRepository.saveAll(users);

        // Create Posts
        Post post1 = new Post();
        post1.setCaption("Just finished an amazing project! 🚀 #coding #developer");
        post1.setUser(user1);
        post1.setTimestamp(LocalDateTime.now().minusHours(2));
        post1.setLikes(15);
        post1.setCommentsCount(3);

        Post post2 = new Post();
        post2.setCaption("Beautiful sunset today! Check out this design inspiration.");
        post2.setImageUrl("https://images.unsplash.com/photo-1495616811223-4d98c6e9c869?w=800");
        post2.setUser(user2);
        post2.setTimestamp(LocalDateTime.now().minusHours(5));
        post2.setLikes(42);
        post2.setCommentsCount(7);

        Post post3 = new Post();
        post3.setCaption("New marketing campaign launching next week! Excited to share what we've been working on.");
        post3.setUser(user3);
        post3.setTimestamp(LocalDateTime.now().minusHours(8));
        post3.setLikes(28);
        post3.setCommentsCount(5);

        Post post4 = new Post();
        post4.setCaption("Exploring the streets of Tokyo 🇯🇵 What a city!");
        post4.setImageUrl("https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800");
        post4.setUser(user4);
        post4.setTimestamp(LocalDateTime.now().minusHours(12));
        post4.setLikes(89);
        post4.setCommentsCount(12);

        Post post5 = new Post();
        post5.setCaption("Remember: consistency beats intensity. Show up every day! 💪");
        post5.setUser(user5);
        post5.setTimestamp(LocalDateTime.now().minusHours(15));
        post5.setLikes(67);
        post5.setCommentsCount(8);

        Post post6 = new Post();
        post6.setCaption("Learning a new framework today. The documentation is surprisingly good!");
        post6.setUser(user1);
        post6.setTimestamp(LocalDateTime.now().minusDays(1));
        post6.setLikes(34);
        post6.setCommentsCount(6);

        Post post7 = new Post();
        post7.setCaption("Color palette inspiration for today ✨");
        post7.setImageUrl("https://images.unsplash.com/photo-1558591710-4b4a1ae0f04d?w=800");
        post7.setUser(user2);
        post7.setTimestamp(LocalDateTime.now().minusDays(1).minusHours(3));
        post7.setLikes(51);
        post7.setCommentsCount(4);

        Post post8 = new Post();
        post8.setCaption("Client meeting went great! Sometimes the simple solutions are the best ones.");
        post8.setUser(user3);
        post8.setTimestamp(LocalDateTime.now().minusDays(2));
        post8.setLikes(22);
        post8.setCommentsCount(2);

        Post post9 = new Post();
        post9.setCaption("Mountain views never get old 🏔️");
        post9.setImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800");
        post9.setUser(user4);
        post9.setTimestamp(LocalDateTime.now().minusDays(2).minusHours(6));
        post9.setLikes(103);
        post9.setCommentsCount(15);

        Post post10 = new Post();
        post10.setCaption("Meal prep Sunday! Nutrition is 80% of the game. 🥗");
        post10.setImageUrl("https://images.unsplash.com/photo-1547592180-85f173990554?w=800");
        post10.setUser(user5);
        post10.setTimestamp(LocalDateTime.now().minusDays(3));
        post10.setLikes(76);
        post10.setCommentsCount(9);

        postRepository.saveAll(Arrays.asList(
                post1, post2, post3, post4, post5,
                post6, post7, post8, post9, post10
        ));

        System.out.println("Database seeded successfully!");
        System.out.println("Created " + userRepository.count() + " users");
        System.out.println("Created " + postRepository.count() + " posts");
    }
}