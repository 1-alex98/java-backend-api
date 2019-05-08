package org.grouporga.java.back.end.api.data.checks;

import com.yahoo.elide.security.ChangeSpec;
import com.yahoo.elide.security.RequestScope;
import com.yahoo.elide.security.checks.InlineCheck;
import org.grouporga.java.back.end.api.data.domain.OwnableEntity;
import org.grouporga.java.back.end.api.security.OrgaUserDetails;

import java.util.Optional;

public class IsUser {

  public static final String EXPRESSION = "is user";

  public static class Inline extends InlineCheck<OwnableEntity> {

    @Override
    public boolean ok(OwnableEntity entity, RequestScope requestScope, Optional<ChangeSpec> changeSpec) {
      Object opaqueUser = requestScope.getUser().getOpaqueUser();
      return opaqueUser instanceof OrgaUserDetails;
    }

    @Override
    public boolean ok(com.yahoo.elide.security.User user) {
      return false;
    }
  }
}
