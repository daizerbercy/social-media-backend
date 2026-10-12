# User Cases

## Overview
This document describes the main use cases of the Social Media Backend project.

The purpose of this document is to identify the interactions between users and the system, 
describe the main features, and define the expected behavior of the application.

## Actors
The system has only the **User** as main actor.
- **User** : A person who interacts with the application to create an account, 
access their account, view profiles, and use social media features.

## Use-Case Diagram

The following diagram represents the main use cases planned.
```mermaid
flowchart LR
    User["👤 User"]

    subgraph System["Social Media Backend"]
        direction TB

        subgraph Account["Account Management"]
            Register(["Register Account"])
            Login(["Log In"])
            ViewProfile(["View User Profile"])
        end

        subgraph Posts["Post Management"]
            CreatePost(["Create Post"])
            GetPosts(["View Posts"])
            GetPost(["View Post Details"])
            DeletePost(["Delete Own Post"])
        end

        subgraph Comments["Comment Management"]
            CreateComment(["Add Comment"])
            GetComments(["View Comments"])
            DeleteComment(["Delete Own Comment"])
        end

        subgraph Interactions["Social Interactions"]
            LikePost(["Like Post"])
            UnlikePost(["Unlike Post"])
            FollowUser(["Follow User"])
            UnfollowUser(["Unfollow User"])
        end
    end

    User ---> Register
    User --> Login
    User --> ViewProfile

    User --> CreatePost
    User --> GetPosts
    User --> GetPost
    User --> DeletePost

    User --> CreateComment
    User --> GetComments
    User --> DeleteComment

    User --> LikePost
    User --> UnlikePost
    User --> FollowUser
    User --> UnfollowUser
```

## Note
This document describes my vision for the project. 