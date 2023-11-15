package linktic.lookfeel.security.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import linktic.lookfeel.security.services.SecurityService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc

/**
 *
 */
@AllArgsConstructor

/**
 * Instantiates a new user principal.
 */
@NoArgsConstructor

/**
 * Gets the token.
 *
 * @return the token
 */
@Getter

/**
 * Sets the token.
 *
 * @param token the new token
 */
@Setter
public class UserPrincipal implements UserDetails {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The authorities. */
    private Collection<? extends GrantedAuthority> authorities;

    private String password;
    
    /** The token. */
    private String token;

    private String username;



    public UserPrincipal(String token) {
        this.token = token;
    }

    /***
     *
     * @param username
     * @param password
     * @return
     */


    public static UserPrincipal buildNew(String sessionKey, Long timeLImitSession, String username, String password) {

        String token = getJWTTokenNew(sessionKey, timeLImitSession, username, password);

        return new UserPrincipal(token);
    }


    private static String getJWTTokenNew(String sessionKey, long timeLimitSession, String username, String password) {

        String token = Jwts
                .builder()
                .setId("OBS")
                .setSubject(username.toString())
                .claim("authorities",
                        password)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + timeLimitSession))
                .signWith(SignatureAlgorithm.HS512,
                        sessionKey.getBytes()).compact();

        return "Bearer " + token;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return false;
    }


}
