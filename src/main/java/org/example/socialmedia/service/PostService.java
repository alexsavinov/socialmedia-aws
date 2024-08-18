package org.example.socialmedia.service;

import org.example.socialmedia.entity.Post;
import org.example.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private S3Service s3Service;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String s3Url = s3Service.uploadFile("your-bucket-name", "post-images/" + post.getId(), image);
            post.setImageUrl(s3Url);
        }
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}