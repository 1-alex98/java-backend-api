package org.grouporga.java.back.end.api.data.domain;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;
import lombok.Setter;
import lombok.ToString;
import org.grouporga.java.back.end.api.data.checks.IsPartOfGroup;

import javax.persistence.*;

@Setter
@Entity
@Table(name = "group_membership")
@Include( type = "groupMembership")
// Needed to change leader of a clan
@ToString(of = {"name"})
@ReadPermission(expression = IsPartOfGroup.EXPRESSION)
public class GroupMemberShip extends AbstractIntegerIdEntity implements OwnableEntity, GroupRelatedEntity{
    private Account account;
    private GroupOfUsers groupOfUsers;
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    public Account getAccount() {
        return account;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",nullable = false)
    public GroupOfUsers getGroupOfUsers() {
        return groupOfUsers;
    }

    public Role getRole() {
        return role;
    }

    @Override
    @Transient
    public Account getEntityOwner() {
        return null;
    }

    @Transient
    @Override
    public GroupOfUsers getRelatedGroup() {
        return groupOfUsers;
    }

    public enum Role{
        USER,
        ADMIN,
        OBSERVER,
        FOUNDER;
    }
}
