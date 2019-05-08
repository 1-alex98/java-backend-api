package org.grouporga.java.back.end.api.data.checks;

import com.yahoo.elide.security.ChangeSpec;
import com.yahoo.elide.security.RequestScope;
import com.yahoo.elide.security.checks.InlineCheck;
import org.grouporga.java.back.end.api.data.domain.GroupMemberShip;
import org.grouporga.java.back.end.api.data.domain.GroupRelatedEntity;
import org.grouporga.java.back.end.api.security.OrgaUserDetails;

import java.util.Optional;

public class IsFounderOfGroup {

  public static final String EXPRESSION = "founder of group";

  public static class Inline extends InlineCheck<GroupRelatedEntity> {

    @Override
    public boolean ok(GroupRelatedEntity entity, RequestScope requestScope, Optional<ChangeSpec> changeSpec) {
      Object opaqueUser = requestScope.getUser().getOpaqueUser();
      if( !(opaqueUser instanceof OrgaUserDetails)
              || entity.getRelatedGroup() == null) return false;
      return entity.getRelatedGroup().getGroupMemberShips().stream()
              .anyMatch(groupMemberShip -> {
                boolean founderMembership = groupMemberShip.getRole() == GroupMemberShip.Role.FOUNDER;
                boolean membershipBelongsToUser = groupMemberShip.getAccount().getId().equals(((OrgaUserDetails) opaqueUser).getId());
                return founderMembership && membershipBelongsToUser;
              });
    }

    @Override
    public boolean ok(com.yahoo.elide.security.User user) {
      return false;
    }
  }
}
