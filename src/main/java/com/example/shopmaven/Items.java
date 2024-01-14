package com.example.shopmaven;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;



@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", length = 200)
    private String title;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "categories", length = 200)
    private int categories;

    @Lob
    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

}
