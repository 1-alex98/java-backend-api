package org.grouporga.java.back.end.api.data.checks;

import com.yahoo.elide.security.ChangeSpec;
import com.yahoo.elide.security.RequestScope;
import com.yahoo.elide.security.checks.InlineCheck;
import org.grouporga.java.back.end.api.security.OrgaUserDetails;
import org.grouporga.java.back.end.api.data.domain.OwnableEntity;

import java.util.Optional;

public class IsEntityOwner {

  public static final String EXPRESSION = "is entity owner";

  public static class Inline extends InlineCheck<OwnableEntity> {

    @Override
    public boolean ok(OwnableEntity entity, RequestScope requestScope, Optional<ChangeSpec> changeSpec) {
      Object opaqueUser = requestScope.getUser().getOpaqueUser();
      return opaqueUser instanceof OrgaUserDetails
        && entity.getEntityOwner() != null
        && entity.getEntityOwner().getId().equals(((OrgaUserDetails) opaqueUser).getId());
    }

    @Override
    public boolean ok(com.yahoo.elide.security.User user) {
      return false;
    }
  }
}
