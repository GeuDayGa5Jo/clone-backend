//package com.example.twiter.entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.List;
//@Entity
//@Getter
//@NoArgsConstructor
//public class Follow {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long followId;
//
//    @ManyToOne
//    @JoinColumn
//    private Member following;
//
//    @ManyToOne
//    @JoinColumn
//    private Member followers;
//
//    public Follow(Member following, Member followers){
//        this.following = following;
//        this.followers = followers;
//    }
//
//}
