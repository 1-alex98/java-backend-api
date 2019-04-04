package org.grouporga.java.back.end.api.security;

import org.grouporga.java.back.end.api.data.domain.Account;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

/**
 * Converts a {@link Account} from and to an {@link Authentication} for use in a JWT token.
 */
@Component
public class OrgaUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

  public static final String USER_ID_KEY = "user_id";
  public static final String NON_LOCKED_KEY = "non_locked";

  @Override
  public Map<String, ?> convertUserAuthentication(Authentication authentication) {
    OrgaUserDetails orgaUserDetails = (OrgaUserDetails) authentication.getPrincipal();

    @SuppressWarnings("unchecked")
    Map<String, Object> response = (Map<String, Object>) super.convertUserAuthentication(authentication);
    response.put(USER_ID_KEY, orgaUserDetails.getId());
    response.put(NON_LOCKED_KEY, orgaUserDetails.isAccountNonLocked());

    return response;
  }

  @Override
  public Authentication extractAuthentication(Map<String, ?> map) {
    if (!map.containsKey(USER_ID_KEY)) {
      return null;
    }

    Integer id = (Integer) map.get(USER_ID_KEY);
    String username = (String) map.get(USERNAME);

    boolean accountNonLocked = Optional.ofNullable((Boolean) map.get(NON_LOCKED_KEY)).orElse(true);
    Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
    UserDetails userDetails = new OrgaUserDetails(id, username, "N/A", accountNonLocked, authorities);

    return new UsernamePasswordAuthenticationToken(userDetails, "N/A", authorities);
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
    if (!map.containsKey(AUTHORITIES)) {
      return Collections.emptySet();
    }
    Object authorities = map.get(AUTHORITIES);
    if (authorities instanceof String) {
      return commaSeparatedStringToAuthorityList((String) authorities);
    }
    if (authorities instanceof Collection) {
      return commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
    }
    throw new IllegalArgumentException("Authorities must be either a String or a Collection");
  }
}
