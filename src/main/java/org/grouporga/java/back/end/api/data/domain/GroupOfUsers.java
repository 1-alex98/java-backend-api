package org.grouporga.java.back.end.api.data.domain;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;
import lombok.Setter;
import lombok.ToString;
import org.grouporga.java.back.end.api.data.checks.Prefab;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;
import java.util.Set;

@Setter
@Entity
@Table(name = "group_of_users")
@Include(rootLevel = true, type = "groupOfUsers")
// Needed to change leader of a clan
@ToString(of = {"name"})
@ReadPermission(expression = Prefab.ALL)
public class GroupOfUsers extends AbstractIntegerIdEntity implements OwnableEntity {
    private String name;
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

    @Transient
    @Override
    public Account getEntityOwner() {
        Optional<GroupMemberShip> first = groupMemberShips.stream()
                .filter(groupMemberShip -> groupMemberShip.getRole() == GroupMemberShip.Role.FOUNDER)
                .findFirst();
        return first.get().getAccount();
    }
}
