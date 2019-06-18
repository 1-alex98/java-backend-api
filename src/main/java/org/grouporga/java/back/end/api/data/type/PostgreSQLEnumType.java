package org.grouporga.java.back.end.api.data.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * From https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/.
 */
public class PostgreSQLEnumType extends EnumType {

    public static final String TYPE_NAME = "pgsql_enum";

    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session
    ) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.toString(), Types.OTHER);
        }
    }
}
