package com.nerv.tactic.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "oauth_access_token")
public class AccessToken {

    @Id
    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "token_id")
    private String tokenId;

    @Lob
    @Column(name = "token")
    private byte[] token;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    @Lob
    @Column(name = "authentication")
    private byte[] authentication;

    @Column(name = "refresh_token")
    private String refreshToken;
}
