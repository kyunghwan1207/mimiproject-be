package com.example.emart.entity;

import com.example.emart.entity.enums.ROLE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter @Getter
@NoArgsConstructor
public class User extends BaseTime{
  @Id
  @GeneratedValue
  private Long id;

  private String username;
  private String email;
  private String password;
  private String address;

  private String phoneNumber;
  private String simplePassword; // e pay 간편결제 비밀번호
  private int epay; // e pay 충전 금액
  private String account; // e pay 충전시 사용할 계좌

  @Enumerated(EnumType.STRING)
  private ROLE role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Cart> carts = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<LikeProduct> likeProducts = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Board> boards = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<BoardComment> boardComments = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Order> orders = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<ProductComment> productComments = new ArrayList<>();

  public User(String username, String email, String password, String address, String phoneNumber, ROLE role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.role = role;
  }

  public void addCart(Cart cart) {
    this.carts.add(cart);
    cart.setUser(this);
    System.out.println("after addCart() / cart.getUser() = " + cart.getUser());
  }
}
