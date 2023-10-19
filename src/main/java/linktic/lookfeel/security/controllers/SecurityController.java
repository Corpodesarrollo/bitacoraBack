package linktic.lookfeel.security.controllers;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import linktic.lookfeel.security.services.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

// TODO: Auto-generated Javadoc

/**
 * The Class SecurityController.
 */
@RestController
@RequestMapping("/api/security")
public class SecurityController {

    /** The service. */
    @Autowired
    private ISecurityService service;

    /**
     * Gets the permisos.
     *
     * @param username the username
     * @param codInstitucion the cod institucion
     * @param sede the sede
     * @param jornada the jornada
     * @return the permisos
     */

    @RequestMapping(value = "/user/{username}/{codInstitucion}/{sede}/{jornada}", method = RequestMethod.GET)
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ESTUDIANTES LISTAR') or hasRole('ESTUDIANTES LISTAR SELECCION') or hasRole('ESTUDIANTES LISTAR TEXTO')")
    public ResponseEntity<UserDetails> getPermisos(@PathVariable String username, @PathVariable Long codInstitucion,
                                                   @PathVariable Long sede, @PathVariable Long jornada) {
        return ResponseEntity.ok(service.PermisosByUsername(username, codInstitucion, sede, jornada, null));
    }


}
