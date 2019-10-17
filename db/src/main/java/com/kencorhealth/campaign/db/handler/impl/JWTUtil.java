package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.CampaignMongoConstants;
import com.kencorhealth.campaign.db.handler.AuthTokenHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTUtil implements CampaignMongoConstants {
    private final static Logger log = LoggerFactory.getLogger(JWTUtil.class);

    static String create(
        String userId,
        String seedToken,
        long ttlSeconds) {
        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;

        long nowMillis = CampaignUtil.utcTime();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes =
            DatatypeConverter.parseBase64Binary(seedToken);
        
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, sa.getJcaName());

        JwtBuilder retVal =
            Jwts.builder()
                .setId(userId)
                .setIssuedAt(now)
                .setSubject(JWT_SUBJECT)
                .setIssuer(JWT_ISSUER)
                .signWith(sa, signingKey);

        // If it has been specified, let's add the expiration
        if (ttlSeconds >= 0) {
            long expMillis = nowMillis + ttlSeconds * 60 * 1000;
            Date exp = new Date(expMillis);
            retVal.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return retVal.compact();
    }
    
    public static AuthToken validate(String jwt) throws NotFoundException {
        AuthToken retVal = null;
        
        try (AuthTokenHandler handler =
             CampaignFactory.get(AuthTokenHandler.class)) {
            retVal = handler.validate(jwt);
            
            Claims claims =
                Jwts.parser()         
                    .setSigningKey(
                        DatatypeConverter.parseBase64Binary(
                            retVal.getId()
                        )
                    )
                    .parseClaimsJws(jwt)
                    .getBody();
            
            if (claims == null) {
                throw new NotFoundException("Invalid token");
            } else {
                log.debug(
                    "JWT Id: " + claims.getId() +
                    ", subject: " + claims.getSubject() +
                    ", issuer: " + claims.getIssuer() +
                    ", token expiration: " + claims.getExpiration()
                );
            }
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                throw (NotFoundException) e;
            } else {
                throw new NotFoundException(e);
            }
        }
        
        return retVal;
    }
}