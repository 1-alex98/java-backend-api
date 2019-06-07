package org.grouporga.java.back.end.api.data.domain;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;
import com.yahoo.elide.annotation.UpdatePermission;
import lombok.Setter;
import org.grouporga.java.back.end.api.data.checks.IsFounderOfGroup;
import org.grouporga.java.back.end.api.data.checks.IsPartOfGroup;

import javax.persistence.*;

@Setter
@Entity
@Table(name = "group_membership")
@Include( type = "groupMembership")
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

    @Enumerated(EnumType.STRING)
    @UpdatePermission(expression = IsFounderOfGroup.EXPRESSION)
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
