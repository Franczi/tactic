package com.nerv.tactic.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "oauth_refresh_token")
public class RefreshToken {

    @Id
    @Column(name = "token_id")
    private String tokenId;

    @Lob
    @Column(name = "token")
    private byte[] token;

    @Lob
    @Column(name = "authentication")
    private byte[] authentication;
}
