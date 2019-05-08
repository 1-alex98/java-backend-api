package org.grouporga.java.back.end.api.data.domain;

import com.yahoo.elide.annotation.*;
import com.yahoo.elide.core.RequestScope;
import lombok.Setter;
import lombok.ToString;
import org.grouporga.java.back.end.api.data.checks.IsEntityOwner;
import org.grouporga.java.back.end.api.data.checks.IsUser;
import org.grouporga.java.back.end.api.data.checks.Prefab;
import org.grouporga.java.back.end.api.security.OrgaUserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;
import java.util.Set;

@Setter
@Entity
@Table(name = "group_of_users")
@Include(rootLevel = true, type = "groupOfUsers")
@ToString(of = {"name"})
@ReadPermission(expression = Prefab.ALL)
@CreatePermission(expression = IsUser.EXPRESSION)
@UpdatePermission(expression = Prefab.UPDATE_ON_CREATE+" or "+IsEntityOwner.EXPRESSION)
public class GroupOfUsers extends AbstractIntegerIdEntity implements OwnableEntity {
    private String name;
    private String password;
    private Set<GroupMemberShip> groupMemberShips;

    @NotEmpty(message = "At least one member must be in a group")
    @OneToMany(mappedBy = "groupOfUsers", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<GroupMemberShip> getGroupMemberShips() {
        return groupMemberShips;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "password")
    @ReadPermission(expression = IsEntityOwner.EXPRESSION)
    public String getPassword() {
        return password;
    }

    @Transient
    @Override
    public Account getEntityOwner() {
        Optional<GroupMemberShip> first = groupMemberShips.stream()
                .filter(groupMemberShip -> groupMemberShip.getRole() == GroupMemberShip.Role.FOUNDER)
                .findFirst();
        return first.get().getAccount();
    }

    @OnCreatePreSecurity
    public void onCreate(RequestScope scope){
        final Object caller = scope.getUser().getOpaqueUser();
        final OrgaUserDetails user = (OrgaUserDetails) caller;
        GroupMemberShip groupMemberShip = new GroupMemberShip();
        Account account = new Account();
        account.setId(user.getId());
        groupMemberShip.setAccount(account);
        groupMemberShip.setGroupOfUsers(this);
        groupMemberShip.setRole(GroupMemberShip.Role.FOUNDER);
        getGroupMemberShips().add(groupMemberShip);
    }
}
